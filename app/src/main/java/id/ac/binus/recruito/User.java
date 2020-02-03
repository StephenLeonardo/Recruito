package id.ac.binus.recruito;

public class User {

    int UserID;
    int ImageID;
    String UserName;
    String DOB;
    String Gender;
    String PhoneNumber;
    String UserStatus;
    String Email;
    String UserPassword;

    public User() {
    }


    public User(int userID, int imageID, String userName, String DOB, String gender, String phoneNumber, String userStatus, String email, String userPassword) {
        UserID = userID;
        ImageID = imageID;
        UserName = userName;
        this.DOB = DOB;
        Gender = gender;
        PhoneNumber = phoneNumber;
        UserStatus = userStatus;
        Email = email;
        UserPassword = userPassword;
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
