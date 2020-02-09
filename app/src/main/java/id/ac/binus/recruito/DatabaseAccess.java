package id.ac.binus.recruito;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import id.ac.binus.recruito.models.CategoryItem;
import id.ac.binus.recruito.models.Comment;
import id.ac.binus.recruito.models.JobThread;
import id.ac.binus.recruito.models.Notification;
import id.ac.binus.recruito.models.NotificationDetail;
import id.ac.binus.recruito.models.User;

public class DatabaseAccess extends AppCompatActivity {
    private static final String TAG = "DatabaseAccess";
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    Cursor cursor = null;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }

        return instance;
    }


    // to open the database
    public void openDatabase() {
        this.database = openHelper.getWritableDatabase();
    }

    // to close the database
    public void closeDatabase() {
        if (database != null) {
            this.database.close();
        }
    }


    public boolean insertUser(int ImageID, String Username, String DOB, String Gender, String PhoneNumber, String UserStatus, String Email, String Password) {
        try {
            String query = "INSERT INTO msUser(ImageID, UserName, DOB, Gender, PhoneNumber, UserStatus, Email, UserPassword) " +
                    "Values ('" +
                    ImageID + "', '" +
                    Username + "', '" +
                    DOB + "', '" +
                    Gender + "', '" +
                    PhoneNumber + "', '" +
                    UserStatus + "', '" +
                    Email + "', '" +
                    Password + "')";
            database.execSQL(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Cursor login(String email) {
        try {
            Cursor cursor = database.rawQuery("select *, (strftime('%Y', 'now') - strftime('%Y', DOB)) - (strftime('%m-%d', 'now') < strftime('%m-%d', DOB)) AS 'Age' from msUser mU JOIN msImage mI ON mU.ImageID = mI.ImageID where Email = ? ", new String[]{email});
            return cursor;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Cursor getAllUsers() {
        try {
            return database.query("msUser", null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertThread(String categoryName, String title, String time, String date, String address, String description, int totalPeople, String creator){
        try {
            String query = "INSERT INTO msThread(CategoryID, JobTitle, JobTime, JobDate, JobAddress, JobDescription, TotalPeople, Creator) Values (" +
                    "(SELECT CategoryID FROM msCategory WHERE CategoryName = '"+categoryName+"'), '" +
                    title + "', '" +
                    time + "', '" +
                    date + "', '" +
                    address + "', '" +
                    description + "', '" +
                    totalPeople + "', '" +
                    creator + "')";
            database.execSQL(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProfile(int userID, String name, String gender, String DOB, String PhoneNumber, String status) {
        try {
            String query = "UPDATE msUser " +
                    "SET UserName = '" + name + "'," +
                    "Gender = '" + gender + "'," +
                    "DOB = '" + DOB + "'," +
                    "PhoneNumber = '" + PhoneNumber + "'," +
                    "UserStatus = '"+status+"' " +
                    "WHERE UserID = " + userID ;
            database.execSQL(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList getAllCategory() {
        ArrayList<CategoryItem> data = new ArrayList<>();
        Cursor cursor = database.query("msCategory", null, null, null, null, null, null);
        String fieldToAdd;
        while (cursor.moveToNext()) {
            fieldToAdd = cursor.getString(cursor.getColumnIndex("CategoryName"));
            data.add(new CategoryItem(fieldToAdd));
        }
        cursor.close();  // dont forget to close the cursor after operation done
        return data;
    }

    public ArrayList getFilteredList(String location, String startTime, String endTime, String date, int categoryID, int peopleRange) {

        String query = "SELECT\n" +
                "mT.ThreadID, JobTitle, JobAddress, JobDate, UserName," +
                "Joined = Count(tD.UserID), TotalPeople " +
                "FROM msThread mT, trDetail tD, msUser mU " +
                "WHERE mT.ThreadID = tD.ThreadID AND" +
                "td.UserID = mU.UserID AND " +
                "TotalPeople = " + peopleRange + " AND " +
                "CategoryID = " + categoryID + " AND " +
                "JobTime BETWEEN " + startTime + " AND " + endTime + " AND " +
                "JobDate = " + date + " AND " +
                "JobAddress LIKE '%' +" + location + " +'% '" +
                "GROUP BY td.ThreadID,JobTitle,JobAddress,JobDate,UserName";

        ArrayList<JobThread> jobThreadArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        JobThread jobThread;
        while (cursor.moveToNext()) {
            int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
            String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
            String jobAddress = cursor.getString(cursor.getColumnIndex("JobAddress"));
            String jobDate = cursor.getString(cursor.getColumnIndex("jobDate"));
            String username = cursor.getString(cursor.getColumnIndex("UserName"));
            int totalJoined = cursor.getInt(cursor.getColumnIndex("Joined"));
            int totalPeople = cursor.getInt(cursor.getColumnIndex("TotalPeople"));
            jobThread = new JobThread(threadID, username, null, null, jobTitle, null, jobDate, jobAddress, null, totalPeople, totalJoined);

            jobThreadArrayList.add(jobThread);
        }
        cursor.close();
        return jobThreadArrayList;
    }

    public ArrayList getAllThread(){
        String query = "SELECT * " +
                "FROM msThread mT";
        ArrayList<JobThread> threadArrayList = new ArrayList<>();


        openDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            JobThread jobThread;
            do {
                String query2 = "SELECT COUNT(*) as a " +
                        "FROM msThread mT JOIN trDetail tD ON mT.ThreadID = tD.threadID WHERE mT.ThreadID = " + cursor.getInt(cursor.getColumnIndex("ThreadID")) +
                        " AND HsJoin = 1";
                Cursor cursor1 = database.rawQuery(query2, null);
                int joinedPeople = 0;
                if (cursor1 != null && cursor1.moveToFirst() && cursor1.isFirst())
                    joinedPeople = cursor1.getInt(cursor1.getColumnIndex("a"));

                int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
                String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
                String jobAddress = cursor.getString(cursor.getColumnIndex("JobAddress"));
                String jobDate = cursor.getString(cursor.getColumnIndex("JobDate"));
                String username = cursor.getString(cursor.getColumnIndex("Creator"));
                int totalPeople = cursor.getInt(cursor.getColumnIndex("TotalPeople"));
                jobThread = new JobThread(threadID, username, null, null, jobTitle, null, jobDate, jobAddress, null, totalPeople, joinedPeople);

                threadArrayList.add(jobThread);
            } while (cursor.moveToNext());
            cursor.close();
        }
        closeDatabase();
        Log.d(TAG, "getAllThread: " + threadArrayList);
        return threadArrayList;
    }



    public ArrayList getHistoryList(int userID, String date) {
        String query = "SELECT " +
                "mT.ThreadID, JobTitle, JobAddress, JobDate, UserName, " +
                "Joined = Count(tD.UserID), TotalPeople " +
                "FROM msThread mT, trDetail tD, msUser mU " +
                "WHERE mT.ThreadID = tD.ThreadID AND " +
                "td.UserID = mU.UserID AND " +
                "tD.UserID = " + userID + " AND " +
                "JobDate BETWEEN " + date + " AND GETDATE()" +
                "GROUP BY td.ThreadID,JobTitle,JobAddress,JobDate,UserName";

        ArrayList<JobThread> jobThreadArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        JobThread jobThread;
        while (cursor.moveToNext()) {
            int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
            String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
            String jobAddress = cursor.getString(cursor.getColumnIndex("JobAddress"));
            String jobDate = cursor.getString(cursor.getColumnIndex("jobDate"));
            String username = cursor.getString(cursor.getColumnIndex("UserName"));
            int totalJoined = cursor.getInt(cursor.getColumnIndex("Joined"));
            int totalPeople = cursor.getInt(cursor.getColumnIndex("TotalPeople"));
            jobThread = new JobThread(threadID, username, null, null, jobTitle, null, jobDate, jobAddress, null, totalPeople, totalJoined);

            jobThreadArrayList.add(jobThread);
        }
        cursor.close();
        return jobThreadArrayList;
    }


    public ArrayList getNotifList(int userID, String currenUser) {

        String query = "SELECT\n" +
                "\t\tDISTINCT *\n" +
                "\tFROM\n" +
                "\t\tmsThread mT JOIN trDetail tD ON mT.ThreadID = tD.ThreadID\n" +
                "\t\tJOIN msUser mU ON tD.UserID = mU.UserID\n" +
                "\tWHERE\n" +
                "\t\ttD.UserID = " + userID + " OR\n" +
                "\t\tCreator = '" + currenUser +"'\n";

        ArrayList<Notification> notificationArrayList = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Notification notification;
        while (cursor.moveToNext()) {
            int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
            String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
            String jobDate = cursor.getString(cursor.getColumnIndex("JobDate"));
            String username = cursor.getString(cursor.getColumnIndex("UserName"));
            String jobTime = cursor.getString(cursor.getColumnIndex("JobTime"));
//            jobThread = new JobThread(threadID, username, null, jobTitle, null, jobDate, null, null, 0, 0);
            notification = new Notification(threadID, jobTitle, userID, username, jobDate, jobTime, false);

            notificationArrayList.add(notification);
        }
        cursor.close();
        closeDatabase();
        return notificationArrayList;
    }

    public JobThread getSpecificPageData(int threadID) {
        String query = "SELECT " +
                "JobDescription, " +
                "JobTime, " +
                "JobDate, " +
                "JobAddress, " +
                "PhoneNumber, " +
                "TotalPeople," +
                "Creator " +
                "FROM " +
                "msThread mT " +
                "JOIN msCategory mC ON mT.CategoryID = mC.CategoryID " +
                "JOIN msUser mU ON mt.Creator = mU.UserName " +
                "GROUP BY " +
                "JobDescription," +
                "JobTime, " +
                "JobDate, " +
                "JobAddress, " +
                "PhoneNumber, " +
                "TotalPeople, " +
                "Creator ;";

        String query2 = "SELECT DISTINCT COUNT(UserID) as a FROM trDetail WHERE ThreadID = " + threadID +
                " AND HsJoin = 1";

        openDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Cursor cursor1 = database.rawQuery(query2, null);

        int joinedPeople = 0;

        if (cursor1 != null && cursor1.moveToFirst() && cursor1.isFirst()){
            joinedPeople = cursor1.getInt(cursor1.getColumnIndex("a"));
        }

        if (cursor != null && cursor.moveToFirst() && cursor.isFirst()){
            String jobDescription = cursor.getString(cursor.getColumnIndex("JobDescription"));
            String jobTime = cursor.getString(cursor.getColumnIndex("JobTime"));
            String jobDate = cursor.getString(cursor.getColumnIndex("JobDate"));
            String jobAddress = cursor.getString(cursor.getColumnIndex("JobAddress"));
            String phoneNumber = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
            int totalPeople = cursor.getInt(cursor.getColumnIndex("TotalPeople"));
            String creator = cursor.getString(cursor.getColumnIndex("Creator"));

            JobThread jobThread =
                    new JobThread(threadID, creator, phoneNumber, null, null, jobTime,
                            jobDate, jobAddress, jobDescription, totalPeople, joinedPeople);

            return jobThread;
        }



        cursor.close();
        closeDatabase();

        return new JobThread();
    }

    public ArrayList getAllNotifDetail(int threadID, String currentUser){
        String query = "SELECT\n" +
                "\t\t DISTINCT *\n" +
                "\tFROM\n" +
                "\t\ttrDetail tD JOIN msUser mU ON tD.UserID = mU.UserID\n" +
                "\tWHERE\n" +
                "\t\tThreadID = " + threadID + "\n" +
                "GROUP BY ThreadID" +
                "\tORDER BY CommentID DESC " +
                "LIMIT 2";

        openDatabase();
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<NotificationDetail> notificationDetailArrayList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst() && cursor.isFirst()){
            do{
                int hasJoined = cursor.getInt(cursor.getColumnIndex("HsJoin"));
                int hasComment = cursor.getInt(cursor.getColumnIndex("HsComment"));
                int hasLeft = cursor.getInt(cursor.getColumnIndex("HsLeave"));
                int isKicked = cursor.getInt(cursor.getColumnIndex("IsKick"));
                String username  = cursor.getString(cursor.getColumnIndex("UserName"));

                NotificationDetail notificationDetail = new NotificationDetail();

                if (hasJoined == 1)
                    notificationDetail.setMessage(username + " has joined");
                else if (hasComment == 1)
                    notificationDetail.setMessage(username + " has commented on this forum");
                else if (hasLeft == 1)
                    notificationDetail.setMessage(username + " has left this forum");
                else if (isKicked == 1 && username.equalsIgnoreCase(currentUser))
                    notificationDetail.setMessage("You have been kicked out. We are sorry");

                notificationDetailArrayList.add(notificationDetail);

            } while (cursor.moveToNext());
        }
        cursor.close();
        closeDatabase();

        return notificationDetailArrayList;
    }

    public ArrayList getAllComments(int threadID) {
        String query =
                "SELECT\n" +
                        "\t\t*\n" +
                        "\tFROM\n" +
                        "\t\ttrDetail tD JOIN msUser mU ON tD.UserID = mU.UserID\n" +
                        "JOIN msImage mI ON mU.ImageID = mI.ImageID " +
                        "\tWHERE\n" +
                        "\t\tThreadID = " + threadID + " AND\n" +
                        "\t\tHsComment = 1";

        ArrayList<Comment> commentArrayList = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.rawQuery(query, null);

        Comment comment;
        if (cursor != null && cursor.moveToFirst() && cursor.isFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex("UserName"));
                int hasJoined = cursor.getInt(cursor.getColumnIndex("HsJoin"));
                int hasComment = cursor.getInt(cursor.getColumnIndex("HsComment"));
                int hasLeft = cursor.getInt(cursor.getColumnIndex("HsLeave"));
                int isKicked = cursor.getInt(cursor.getColumnIndex("IsKick"));
                String commentText = cursor.getString(cursor.getColumnIndex("Comment"));
                int userID = cursor.getInt(cursor.getColumnIndex("UserID"));
                String imageName = cursor.getString(cursor.getColumnIndex("ImageName"));

                comment = new Comment( threadID, userID, name, imageName, commentText, hasJoined, hasComment, hasLeft, isKicked);

                commentArrayList.add(comment);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return commentArrayList;
    }

    /*
    Commented by Stephen
    Date : Tuesday Feb, 04 2020
    Purpose : query is invalid, need to tell JSA
     */
    public ArrayList getHomePageData() {
        String query = "SELECT " +
                "mT.ThreadID, JobTitle, JobAddress, JobDate, Creator, " +
                "Joined = Count(tD.UserID), TotalPeople " +
                "FROM msThread mT, trDetail tD, msUser mU " +
                "WHERE mT.ThreadID = tD.ThreadID AND " +
                "td.UserID = mU.UserID AND " +
                "JobDate BETWEEN date('now') AND date('now', '+30 days') " +
                "GROUP BY td.ThreadID,JobTitle,JobAddress,JobDate, Creator, TotalPeople ";

        ArrayList<JobThread> jobThreadArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        JobThread jobThread;
        while (cursor.moveToNext()) {
            int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
            String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
            String jobAddress = cursor.getString(cursor.getColumnIndex("JobAddress"));
            String jobDate = cursor.getString(cursor.getColumnIndex("jobDate"));
            String creator = cursor.getString(cursor.getColumnIndex("Creator"));
            int totalJoined = cursor.getInt(cursor.getColumnIndex("Joined"));
            int totalPeople = cursor.getInt(cursor.getColumnIndex("TotalPeople"));
            jobThread = new JobThread(threadID, creator, null, null, jobTitle, null, jobDate, jobAddress, null, totalPeople, totalJoined);

            jobThreadArrayList.add(jobThread);
        }
        cursor.close();
        return jobThreadArrayList;
    }

    ArrayList getAllJoinedPeople(int threadID){
        String query = "SELECT " +
                " UserName, tD.UserID " +
                "FROM " +
                "msUser mU JOIN trDetail tD ON mU.UserID = tD.UserID " +
                "WHERE " +
                "ThreadID = " + threadID +" AND " +
                "HsJoin = 1 ";
        ArrayList<User> userArrayList = new ArrayList<>();

        openDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst() && cursor.isFirst()){
            do {
                userArrayList.add(new User(cursor.getString(cursor.getColumnIndex("UserName")), cursor.getInt(cursor.getColumnIndex("UserID"))));

            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDatabase();

        Log.d(TAG, "getAllJoinedPeople: Joined User = " + userArrayList);

        return userArrayList;
    }


    public User getProfileData(int userID) {
        String query = "SELECT " +
                "* " +
                "FROM msUser mU, msImage mI " +
                "WHERE mU.ImageID= mI.ImageID AND " +
                "UserID = " + userID;
        Cursor cursor = database.rawQuery(query, null);
        int imageID = cursor.getInt(cursor.getColumnIndex("ImageID"));
        String imageName = cursor.getString(cursor.getColumnIndex("ImageName"));
        String username = cursor.getString(cursor.getColumnIndex("UserName"));
        int age = cursor.getInt(cursor.getColumnIndex("Age"));
        String gender = cursor.getString(cursor.getColumnIndex("Gender"));
        String phoneNumber = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
        String email = cursor.getString(cursor.getColumnIndex("Email"));
        String status = cursor.getString(cursor.getColumnIndex("UserStatus"));

        User user = new User(userID, imageID, username, null, age, gender, phoneNumber, status, email, null, imageName);

        return user;
    }

    public boolean updatePassword(int UserID, String newPassword) {
        try {
            String query = "UPDATE msUser " +
                    "SET UserPassword = '" + newPassword + "' " +
                    "WHERE UserID = '" + UserID + "'";
            database.execSQL(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean joinThread(int userID, int threadID) {

        int hasJoined = 1;
        int hasComment = 0;
        int hasLeft = 0;
        int isKicked = 0;
        String comment = "";

        String query =
                "INSERT INTO trDetail(ThreadID, UserID, Comment, HsJoin, HsComment, HsLeave, IsKick) " +
                "VALUES( " +
                threadID + ", " + userID + ", '" + comment + "', " + hasJoined + ", " +
                hasComment + ", " + hasLeft + ", " + isKicked +
                ") ";

        openDatabase();
        database.execSQL(query);
        closeDatabase();
        Log.d(TAG, "joinThread: BISA JOIN!");
        return true;
    }

    public boolean insertComment(int userID, int threadID, String comment){
        int hasJoined = 0;
        int hasComment = 1;
        int hasLeft = 0;
        int isKicked = 0;

        String query =
                "INSERT INTO trDetail(ThreadID,UserID,Comment,HsJoin,HsComment,HsLeave,IsKick) " +
                "VALUES(" +
                threadID + "," + userID + ",'" + comment + "'," + hasJoined + "," +
                hasComment + "," + hasLeft + "," + isKicked +
                ")";
        database.execSQL(query);
        return true;
    }

    public boolean leaveThread(int userID, int threadID){
        int hasJoined = 0;
        int hasComment = 0;
        int hasLeft = 1;
        int isKicked = 0;
        String comment = "";

        try{
            String query =
                    "INSERT INTO trDetail(ThreadID,UserID,Comment,HsJoin,HsComment,HsLeave,IsKick) " +
                            "VALUES(" +
                            threadID + "," + userID + "," + comment + "," + hasJoined + "," +
                            hasComment + "," + hasLeft + "," + isKicked +
                            ");" +
                    "DELETE FROM trDetail " +
                            "WHERE ThreadID = " + threadID + " AND " +
                            "UserID = " + userID + " AND " +
                            "HsLeave != 1";
            database.execSQL(query);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean kickPeople(int userIDToBeKicked, int threadID){
        int hasJoined = 0;
        int hasComment = 0;
        int hasLeft = 0;
        int isKicked = 1;
        String comment = "";

        String query =
                "INSERT INTO trDetail(ThreadID,UserID,Comment,HsJoin,HsComment,HsLeave,IsKick) " +
                        "VALUES(" +
                        threadID + "," + userIDToBeKicked + ",'" + comment + "'," + hasJoined + "," +
                        hasComment + "," + hasLeft + "," + isKicked +
                        ")";
        database.execSQL(query);

        String query2 =
                "DELETE FROM trDetail " +
                        "WHERE ThreadID = "+ threadID + " AND " +
                        "UserID =  "+ userIDToBeKicked +"  AND " +
                        "IsKick != 1";
        database.execSQL(query2);

        return true;
    }

}
