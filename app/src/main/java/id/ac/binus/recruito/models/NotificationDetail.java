package id.ac.binus.recruito.models;

public class NotificationDetail {

    String message;

    public NotificationDetail(String message) {
        this.message = message;
    }

    public NotificationDetail() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
