package id.ac.binus.recruito;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import id.ac.binus.recruito.models.CategoryItem;
import id.ac.binus.recruito.models.Comment;
import id.ac.binus.recruito.models.JobThread;
import id.ac.binus.recruito.models.User;

public class DatabaseAccess extends AppCompatActivity {

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


    public Cursor login(String email, String password) {
        try {
            Cursor cursor = database.rawQuery("select *, (strftime('%Y', 'now') - strftime('%Y', DOB)) - (strftime('%m-%d', 'now') < strftime('%m-%d', DOB)) AS 'Age' from msUser mU JOIN msImage mI ON mU.ImageID = mI.ImageID where Email = ? and UserPassword = ? ", new String[]{email, password});
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

    public boolean insertThread(int categoryID, String title, String time, String date, String address, String description, int totalPeople){
        try {
            String query = "INSERT INTO msThread(CategoryID, JobTitle, JobTime, JobDate, JobAddress, JobDescription, TotalPeople) Values ('" +
                    categoryID + "', '" +
                    title + "', '" +
                    time + "', '" +
                    date + "', '" +
                    address + "', '" +
                    description + "', '" +
                    totalPeople + "')";
            database.execSQL(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProfile(int userID, String name, String gender, String DOB, String PhoneNumber, String status) {
        try {
            String query = "UPDATE msUser" +
                    "SET UserName = " + name + "," +
                    "Gender = " + gender + "," +
                    "DOB = " + DOB + "," +
                    "PhoneNumber = " + PhoneNumber + "," +
                    "UserStatus = "+status+"" +
                    "WHERE UserID = " + userID;
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
            jobThread = new JobThread(threadID, username, null, jobTitle, null, jobDate, jobAddress, null, totalPeople, totalJoined);

            jobThreadArrayList.add(jobThread);
        }
        cursor.close();
        return jobThreadArrayList;
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
            jobThread = new JobThread(threadID, username, null, jobTitle, null, jobDate, jobAddress, null, totalPeople, totalJoined);

            jobThreadArrayList.add(jobThread);
        }
        cursor.close();
        return jobThreadArrayList;
    }


    public ArrayList getNotifList(int userID) {
        String query = "SELECT " +
                "mT.ThreadID, JobTitle, JobDate,UserName " +
                "FROM msThread mT, trDetail tD, msUser mU " +
                "WHERE mT.ThreadID = tD.ThreadID AND " +
                "td.UserID = mU.UserID AND " +
                "td.UserID=" + userID;

        ArrayList<JobThread> jobThreadArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        JobThread jobThread;
        while (cursor.moveToNext()) {
            int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
            String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
            String jobDate = cursor.getString(cursor.getColumnIndex("jobDate"));
            String username = cursor.getString(cursor.getColumnIndex("UserName"));
            jobThread = new JobThread(threadID, username, null, jobTitle, null, jobDate, null, null, 0, 0);

            jobThreadArrayList.add(jobThread);
        }
        cursor.close();
        return jobThreadArrayList;
    }

    public JobThread getSpecificPageData(int threadID) {
        String query = "SELECT\n" +
                "JobDescription,\n" +
                "JobTime,\n" +
                "JobDate,\n" +
                "JobAddress,\n" +
                "PhoneNumber,\n" +
                "COUNT(CommentID) AS 'JoinedPeople',\n" +
                "TotalPeople,\n" +
                "Creator\n" +
                "FROM\n" +
                "msThread mT JOIN trDetail tD on mT.ThreadID = tD.ThreadID\n" +
                "JOIN msCategory mC ON mT.CategoryID = mC.CategoryID \n" +
                "JOIN msUser mU ON mT.Creator = mU.UserName\n" +
                "WHERE\n" +
                "HsJoin = 1 AND\n" +
                "mT.ThreadID =" + threadID +
                "GROUP BY\n" +
                "JobDescription,\n" +
                "JobTime,\n" +
                "JobDate,\n" +
                "JobAddress,\n" +
                "PhoneNumber,\n" +
                "TotalPeople,\n" +
                "Creator";

        Cursor cursor = database.rawQuery(query, null);

        String jobDescription = cursor.getString(cursor.getColumnIndex("JobDescription"));
        String jobTime = cursor.getString(cursor.getColumnIndex("JobTime"));
        String jobDate = cursor.getString(cursor.getColumnIndex("JobDate"));
        String jobAddress = cursor.getString(cursor.getColumnIndex("JobAddress"));
        String phoneNumber = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
        int joinedPeople = cursor.getInt(cursor.getColumnIndex("JoinedPeople"));
        int totalPeople = cursor.getInt(cursor.getColumnIndex("TotalPeople"));
        String creator = cursor.getString(cursor.getColumnIndex("Creator"));

        JobThread jobThread =
                new JobThread(threadID, creator, phoneNumber, null, null, jobTime,
                        jobDate, jobAddress, jobDescription, totalPeople, joinedPeople);

        return jobThread;

    }

    public ArrayList getAllCommentsAndJoinedPeopleInThread(int threadID) {
        String query =
                "SELECT\n" +
                        "*\n" +
                        "FROM\n" +
                        "msThread mT JOIN trDetail tD ON mT.ThreadID = tD.ThreadID\n" +
                        "JOIN msUser mU ON mT.Creator = mU.UserName\n" +
                        "WHERE\n" +
                        "mT.ThreadID = @threadID";

        ArrayList<Comment> commentArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);

        Comment comment;

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("UserName"));
            int hasJoined = cursor.getInt(cursor.getColumnIndex("HsJoin"));
            int hasComment = cursor.getInt(cursor.getColumnIndex("HsComment"));
            int hasLeft = cursor.getInt(cursor.getColumnIndex("HsLeave"));
            int isKicked = cursor.getInt(cursor.getColumnIndex("IsKick"));
            String commentText = cursor.getString(cursor.getColumnIndex("Comment"));
            int commentID = cursor.getInt(cursor.getColumnIndex("CommentID"));
            int userID = cursor.getInt(cursor.getColumnIndex("UserID"));
            String imageName = cursor.getString(cursor.getColumnIndex("ImageName"));

            comment = new Comment(commentID, threadID, userID, name, imageName, commentText, hasJoined, hasComment, hasLeft, isKicked);

            commentArrayList.add(comment);
        }
        cursor.close();

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
            jobThread = new JobThread(threadID, creator, null, jobTitle, null, jobDate, jobAddress, null, totalPeople, totalJoined);

            jobThreadArrayList.add(jobThread);
        }
        cursor.close();
        return jobThreadArrayList;
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
                    "SET UserPassword = " + newPassword +
                    "WHERE UserID = " + UserID;
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

        try {
            String query =
                    "INSERT INTO trDetail(ThreadID,UserID,Comment,HsJoin,HsComment,HsLeave,IsKick) " +
                    "VALUES(" +
                    threadID + "," + userID + "," + comment + "," + hasJoined + "," +
                    hasComment + "," + hasLeft + "," + isKicked +
                    ")";

            database.execSQL(query);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertComment(int userID, int threadID, String comment){
        int hasJoined = 0;
        int hasComment = 1;
        int hasLeft = 0;
        int isKicked = 0;

        try{
            String query =
                    "INSERT INTO trDetail(ThreadID,UserID,Comment,HsJoin,HsComment,HsLeave,IsKick) " +
                    "VALUES(" +
                    threadID + "," + userID + "," + comment + "," + hasJoined + "," +
                    hasComment + "," + hasLeft + "," + isKicked +
                    ")";
            database.execSQL(query);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
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

    public boolean kickThread(int userIDToBeKicked, int threadID){
        int hasJoined = 0;
        int hasComment = 0;
        int hasLeft = 0;
        int isKicked = 1;
        String comment = "";

        try{
            String query =
                    "INSERT INTO trDetail(ThreadID,UserID,Comment,HsJoin,HsComment,HsLeave,IsKick) " +
                            "VALUES(" +
                            threadID + "," + userIDToBeKicked + "," + comment + "," + hasJoined + "," +
                            hasComment + "," + hasLeft + "," + isKicked +
                            ");" +
                            "DELETE FROM trDetail " +
                            "WHERE ThreadID = " + threadID + " AND " +
                            "UserID = " + userIDToBeKicked + " AND " +
                            "IsKick != 1";
            database.execSQL(query);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
