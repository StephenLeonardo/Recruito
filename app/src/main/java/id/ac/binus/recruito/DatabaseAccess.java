package id.ac.binus.recruito;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

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
            String query = "INSERT INTO msUser(ImageID, UserName, DOB, PhoneNumber, UserStatus, Email, UserPassword) Values ('" +
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


    public User login(String email, String password) {
        try {
            Cursor cursor = database.rawQuery("select * from msUser where Email = ? and UserPassword = ? ", new String[]{email, password});
            if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {

                int UserID = cursor.getInt(cursor.getColumnIndex("UserID"));
                int ImageID = cursor.getInt(cursor.getColumnIndex("ImageID"));
                String UserName = cursor.getString(cursor.getColumnIndex("UserName"));
                String DOB = cursor.getString(cursor.getColumnIndex("DOB"));
                String PhoneNumber = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
                String UserStatus = cursor.getString(cursor.getColumnIndex("UserStatus"));
                String Email = cursor.getString(cursor.getColumnIndex("Email"));
                String UserPassword = cursor.getString(cursor.getColumnIndex("UserPassword"));


                // if cursor has value then in user database there is user associated with this given email
                User currentUser = new User(UserID, ImageID, UserName, DOB, PhoneNumber, UserStatus, Email, UserPassword);

                // Match both passwords check they are same or not
                if (password.equals(currentUser.UserPassword) && email.equalsIgnoreCase(currentUser.Email)) {
                    return currentUser;
                }
            }
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
}
