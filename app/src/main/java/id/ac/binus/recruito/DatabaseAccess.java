package id.ac.binus.recruito;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

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


    public boolean insertUser(int ImageID, String Username, String DOB, String PhoneNumber, String UserStatus, String Email, String Password) {
        try {
            String query = "INSERT INTO msUser(ImageID, UserName, DOB, PhoneNumber, UserStatus, Email, UserPassword) " +
                    "Values ('" +
                    ImageID + "', '" +
                    Username + "', '" +
                    DOB + "', '" +
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
            Cursor cursor = database.rawQuery("select * from msUser where Email = ? and UserPassword = ? ", new String[]{email, password});
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
}
