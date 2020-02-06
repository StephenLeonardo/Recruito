package id.ac.binus.recruito.models;

public class Notification {


    /*
    Modified by Stephen
    Date : Feb 06, 2020
    Purpose :
        Ganti UserID jdi int, karna di database int(Gak tau sih boleh jdi int gk),
        Tambahin atribut ThreadID supaya pas klik notifnya bisa redirect ke detail thread sesuai threadID
     */
    private int UserID, ThreadID;
    private String message, Date, Time;
    private boolean read;

    public Notification(int threadID, String message, int userID, String date, String time, boolean read) {
        ThreadID = threadID;
        this.message = message;
        UserID = userID;
        Date = date;
        Time = time;
        this.read = read;
    }

    public Notification() {
    }

    public int getThreadID() {
        return ThreadID;
    }

    public void setThreadID(int threadID) {
        ThreadID = threadID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
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
