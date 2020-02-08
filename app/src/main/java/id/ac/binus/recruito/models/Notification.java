package id.ac.binus.recruito.models;

import java.util.ArrayList;

public class Notification {


    /*
    Modified by Stephen
    Date : Feb 06, 2020
    Purpose :
        Ganti UserID jdi int, karna di database int(Gak tau sih boleh jdi int gk),
        Tambahin atribut ThreadID supaya pas klik notifnya bisa redirect ke detail thread sesuai threadID
     */
    private int UserID, ThreadID;
    private String jobTitle, Date, Time, username;
    private boolean read;
    private ArrayList<NotificationDetail> notificationDetails;


    public Notification(int threadID, String jobTitle, int userID, String username, String date, String time, boolean read) {
        ThreadID = threadID;
        this.jobTitle = jobTitle;
        UserID = userID;
        this.username = username;
        Date = date;
        Time = time;
        this.read = read;
    }

    public ArrayList<NotificationDetail> getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(ArrayList<NotificationDetail> notificationDetails) {
        this.notificationDetails = notificationDetails;
    }

    public Notification() {
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getThreadID() {
        return ThreadID;
    }

    public void setThreadID(int threadID) {
        ThreadID = threadID;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
