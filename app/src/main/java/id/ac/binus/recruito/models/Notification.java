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
    private ArrayList<String> message;
    private boolean read;

    /*
    Modified by Stephen
    Date : Feb 07, 06 2020
    Purpose : Add functionality to get notifications
     */

    public Notification(int threadID, String jobTitle, int userID, String username, String date, String time, boolean read) {
        ThreadID = threadID;
        this.jobTitle = jobTitle;
        UserID = userID;
        this.username = username;
        Date = date;
        Time = time;
        this.read = read;


        message = new ArrayList<>();

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

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMessage(int hasJoined, int hasComment, int hasLeft, int isKicked){
        if(hasJoined == 1){
            message.add(" has joined");
        }
        else if(hasComment == 1){
            message.add(" has commented on this forum");
        }
        else if(hasLeft == 1){
            message.add(" has left this forum");
        }
        else if(isKicked == 1){
            setUsername("You");
            message.add(" have been kicked out. We are sorry");
        }
    }

}
