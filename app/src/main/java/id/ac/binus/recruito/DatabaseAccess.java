package id.ac.binus.recruito;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import id.ac.binus.recruito.models.CategoryItem;
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
                "Joined = Count(tD.UserID)" +
                "FROM msThread mT, trDetail tD, msUser mU" +
                "WHERE mT.ThreadID = tD.ThreadID AND" +
                "td.UserID = mU.UserID AND" +
                "TotalPeople = " + peopleRange + " AND" +
                "CategoryID = " + categoryID + " AND" +
                "JobTime BETWEEN " + startTime + " AND " + endTime + " AND" +
                "JobDate = " + date + " AND" +
                "JobAddress LIKE '%' +" + location + " +'%'" +
                "GROUP BY td.ThreadID,JobTitle,JobAddress,JobDate,UserName";

        ArrayList<Thread> threadArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        Thread thread;
        while (cursor.moveToNext()) {
            int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
            String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
            String jobAddress = cursor.getString(cursor.getColumnIndex("JobAddress"));
            String jobDate = cursor.getString(cursor.getColumnIndex("jobDate"));
            String username = cursor.getString(cursor.getColumnIndex("UserName"));
            int totalJoined = cursor.getInt(cursor.getColumnIndex("Joined"));
            thread = new Thread(threadID, username, null, jobTitle, null, jobDate, jobAddress, null, totalJoined);

            threadArrayList.add(thread);
        }
        cursor.close();
        return threadArrayList;
    }


    public ArrayList getHistoryList(int userID, String date) {
        String query = "SELECT " +
                "mT.ThreadID, JobTitle, JobAddress, JobDate, UserName," +
                "Joined = Count(tD.UserID)" +
                "FROM msThread mT, trDetail tD, msUser mU" +
                "WHERE mT.ThreadID = tD.ThreadID AND" +
                "td.UserID = mU.UserID AND " +
                "tD.UserID = " + userID + " AND" +
                "JobDate BETWEEN " + date + " AND GETDATE()" +
                "GROUP BY td.ThreadID,JobTitle,JobAddress,JobDate,UserName";

        ArrayList<Thread> threadArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        Thread thread;
        while (cursor.moveToNext()) {
            int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
            String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
            String jobAddress = cursor.getString(cursor.getColumnIndex("JobAddress"));
            String jobDate = cursor.getString(cursor.getColumnIndex("jobDate"));
            String username = cursor.getString(cursor.getColumnIndex("UserName"));
            int totalJoined = cursor.getInt(cursor.getColumnIndex("Joined"));
            thread = new Thread(threadID, username, null, jobTitle, null, jobDate, jobAddress, null, totalJoined);

            threadArrayList.add(thread);
        }
        cursor.close();
        return threadArrayList;
    }


    public ArrayList getNotifList(int userID) {
        String query = "SELECT " +
                "mT.ThreadID, JobTitle, JobDate,UserName" +
                "FROM msThread mT, trDetail tD, msUser mU" +
                "WHERE mT.ThreadID = tD.ThreadID AND" +
                "td.UserID = mU.UserID AND" +
                "td.UserID=" + userID;

        ArrayList<Thread> threadArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        Thread thread;
        while (cursor.moveToNext()) {
            int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
            String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
            String jobDate = cursor.getString(cursor.getColumnIndex("jobDate"));
            String username = cursor.getString(cursor.getColumnIndex("UserName"));
            thread = new Thread(threadID, username, null, jobTitle, null, jobDate, null, null, 0);

            threadArrayList.add(thread);
        }
        cursor.close();
        return threadArrayList;
    }


    /*
    Commented by Stephen
    Date : Tuesday Feb, 04 2020
    Purpose : query is invalid, need to tell JSA
     */
    public Thread getSpecificPageData(int threadID) {
        String query = "SELECT " +
                "JobTime, JobDate, JobAddress, PhoneNumber, UserName, Comment, ImageName\n" +
                "FROM msThread mT, trDetail tD, msUser mU, msImage mI" +
                "WHERE mT.ThreadID = tD.ThreadID AND" +
                "td.UserID = mU.UserID AND" +
                "mU.ImageID = mI.ImageID AND" +
                "td.ThreadID = " + threadID;
        return null; // delete when fixed
    }

    /*
    Commented by Stephen
    Date : Tuesday Feb, 04 2020
    Purpose : query is invalid, need to tell JSA
     */
    public ArrayList getHomePageData() {
        String query = "SELECT " +
                "mT.ThreadID, JobTitle, JobAddress, JobDate, UserName, " +
                "Joined = Count(tD.UserID) " +
                "FROM msThread mT, trDetail tD, msUser mU " +
                "WHERE mT.ThreadID = tD.ThreadID AND " +
                "td.UserID = mU.UserID AND " +
                "Comment = '<!!<THR34D M4K3R>!!>' AND " +
                "JobDate BETWEEN GETDATE() AND GETDATE()+30 " +
                "GROUP BY td.ThreadID,JobTitle,JobAddress,JobDate,UserName ";

        ArrayList<Thread> threadArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(query, null);
        Thread thread;
        while (cursor.moveToNext()) {
            int threadID = cursor.getInt(cursor.getColumnIndex("ThreadID"));
            String jobTitle = cursor.getString(cursor.getColumnIndex("JobTitle"));
            String jobAddress = cursor.getString(cursor.getColumnIndex("JobAddress"));
            String jobDate = cursor.getString(cursor.getColumnIndex("jobDate"));
            String username = cursor.getString(cursor.getColumnIndex("UserName"));
            int totalJoined = cursor.getInt(cursor.getColumnIndex("Joined"));
            thread = new Thread(threadID, username, null, jobTitle, null, jobDate, jobAddress, null, totalJoined);

            threadArrayList.add(thread);
        }
        cursor.close();
        return threadArrayList;
    }

    /*
    Commented by Stephen
    Date : Tuesday Feb, 04 2020
    Purpose : need ImageID, not ImageName
     */
    public User getProfileData(int userID) {
        String query = "SELECT " +
                "mU.ImageID, ImageName, " +
                "UserName,Age = DATEDIFF(YEAR,DOB,GETDATE()), " +
                "Gender," +
                "PhoneNumber, Email,UserStatus " +
                "FROM msUser mU, msImage mI " +
                "WHERE mU.ImageID= mI.ImageID AND " +
                "UserID = @UserId";
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

    /*
    Commented by Stephen
    Date : Tuesday Feb, 04 2020
    Purpose : query is invalid, need to tell JSA
     */
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


}
