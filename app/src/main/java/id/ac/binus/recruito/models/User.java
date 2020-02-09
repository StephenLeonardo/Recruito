package id.ac.binus.recruito.models;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.databinding.BindingAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class User extends Application {

    private int UserID;
    private int ImageID;
    private String UserName;
    private String DOB;
    private int Age;
    private String Gender;
    private String PhoneNumber;
    private String UserStatus;
    private String Email;
    private String UserPassword;
    private String ImageName;
    private static Context mContext;

    public User() {

    }

    public User(Context context){
        mContext = context;
    }

    public User(String userName) {
        this.UserName = userName;
    }

    public User(String userName, int userID) {
        this.UserName = userName;
        this.UserID = userID;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }


    public User(int userID, int imageID, String userName, String DOB, int age, String gender, String phoneNumber, String userStatus, String email, String userPassword, String imageFilePath) {
        UserID = userID;
        ImageID = imageID;
        UserName = userName;
        this.DOB = DOB;
        Age = age;
        Gender = gender;
        PhoneNumber = phoneNumber;
        UserStatus = userStatus;
        Email = email;
        UserPassword = userPassword;
        ImageName = imageFilePath;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(String userStatus) {
        UserStatus = userStatus;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    @BindingAdapter("android:imageFilePath")
    public static void loadImage(View view, String imageName){
        CircleImageView circleImageView = (CircleImageView) view;
        circleImageView.setImageResource( mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName()));
    }


}
