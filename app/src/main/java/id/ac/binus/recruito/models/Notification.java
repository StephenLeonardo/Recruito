package id.ac.binus.recruito.models;

public class Notification {

    private String message, UserID, Date, Time;
    private boolean read;

    public Notification(String message, String userID, String date, String time, boolean read) {
        this.message = message;
        UserID = userID;
        Date = date;
        Time = time;
        this.read = read;
    }

    public Notification() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
