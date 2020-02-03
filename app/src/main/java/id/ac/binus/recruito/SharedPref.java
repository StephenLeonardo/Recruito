package id.ac.binus.recruito;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPref {

    // What's the function of this class :
    // take care of saving and loading data from shared preferences xml.
    // remember, the data is saved in the form of xml.

    private SharedPreferences sharedPreferences;

    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);

    }

    public void save(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("UserID", user.getUserID());
        editor.putInt("ImageID", user.getImageID());
        editor.putString("username", user.getUserName());
        editor.putString("DOB", user.getDOB());
        editor.putString("PhoneNumber", user.getPhoneNumber());
        editor.putString("UserStatus", user.getUserStatus());
        editor.putString("Email", user.getEmail());
        editor.putString("password", user.getUserPassword());
        // you can use apply() or commit() to saving data to shared preferences xml
        editor.apply();
    }

    public User load() {
        User user = new User();
        user.setUserID(sharedPreferences.getInt("UserID", 0));
        user.setImageID(sharedPreferences.getInt("ImageID", 1));
        user.setUserName(sharedPreferences.getString("username", ""));
        user.setDOB(sharedPreferences.getString("DOB", ""));
        user.setPhoneNumber(sharedPreferences.getString("PhoneNumber", ""));
        user.setUserStatus(sharedPreferences.getString("UserStatus", ""));
        user.setEmail(sharedPreferences.getString("Email", ""));
        user.setUserPassword(sharedPreferences.getString("password", ""));
        return user;
    }

    public void clearAll(Context context) {
        sharedPreferences.edit().clear().commit();
    }
}
