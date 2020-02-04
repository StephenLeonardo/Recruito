package id.ac.binus.recruito.models;

public class User {

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

    public User() {

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
}
