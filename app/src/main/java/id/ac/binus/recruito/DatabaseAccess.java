package id.ac.binus.recruito;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    Cursor cursor = null;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }

        return instance;
    }


    // to open the database
    public void openDatabase(){
        this.database = openHelper.getWritableDatabase();
    }

    // to close the database
    public void closeDatabase(){
        if(database != null){
            this.database.close();
        }
    }


    public void insertUser(int ImageID, String Username, String DOB, String PhoneNumber, String UserStatus, String Email, String Password){
        String query = "INSERT INTO msUser(ImageID, UserName, DOB, PhoneNumber, UserStatus, Email, UserPassword) Values ('"+
                ImageID +"', '"+
                Username +"', '"+
                DOB+"', '"+
                PhoneNumber+"', '"+
                UserStatus+"', '"+
                Email+"', '"+
                Password+"')";
        database.execSQL(query);
    }

}
