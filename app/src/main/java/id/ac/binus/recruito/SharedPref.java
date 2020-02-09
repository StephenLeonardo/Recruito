package id.ac.binus.recruito;

import android.content.Context;
import android.content.SharedPreferences;

import id.ac.binus.recruito.models.User;


public class SharedPref {

    // What's the function of this class :
    // take care of saving and loading data from shared preferences xml.
    // remember, the data is saved in the form of xml.

    private SharedPreferences sharedPreferences;

    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);

    }

    public SharedPref() {

    }

    public void save(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("UserID", user.getUserID());
        editor.putInt("ImageID", user.getImageID());
        editor.putString("Username", user.getUserName());
        editor.putString("DOB", user.getDOB());
        editor.putInt("Age", user.getAge());
        editor.putString("Gender", user.getGender());
        editor.putString("PhoneNumber", user.getPhoneNumber());
        editor.putString("UserStatus", user.getUserStatus());
        editor.putString("Email", user.getEmail());
        editor.putString("Password", user.getUserPassword());
        editor.putString("ImageName", user.getImageName());
        // you can use apply() or commit() to saving data to shared preferences xml
        editor.apply();
    }

    public User load() {
        User user = new User();
        user.setUserID(sharedPreferences.getInt("UserID", 0));
        user.setImageID(sharedPreferences.getInt("ImageID", 1));
        user.setUserName(sharedPreferences.getString("Username", ""));
        user.setDOB(sharedPreferences.getString("DOB", ""));
        user.setAge(sharedPreferences.getInt("Age", 0));
        user.setGender(sharedPreferences.getString("Gender", ""));
        user.setPhoneNumber(sharedPreferences.getString("PhoneNumber", ""));
        user.setUserStatus(sharedPreferences.getString("UserStatus", ""));
        user.setEmail(sharedPreferences.getString("Email", ""));
        user.setUserPassword(sharedPreferences.getString("Password", ""));
        user.setImageName(sharedPreferences.getString("ImageName", ""));
        return user;
    }

    public void clearAll(Context context) {
        sharedPreferences.edit().clear().commit();
    }
}
