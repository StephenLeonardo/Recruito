package id.ac.binus.recruito.models;

public class Comment {

    private int CommentID;
    private int ThreadID;
    private int UserID;
    private String Name;
    private String ImageName;
    private String Comment;
    private int HsJoin;
    private int HsComment;
    private int HsLeave;
    private int IsKick;

    public int getCommentID() {
        return CommentID;
    }

    public void setCommentID(int commentID) {
        CommentID = commentID;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getHsJoin() {
        return HsJoin;
    }

    public void setHsJoin(int hsJoin) {
        HsJoin = hsJoin;
    }

    public int getHsComment() {
        return HsComment;
    }

    public void setHsComment(int hsComment) {
        HsComment = hsComment;
    }

    public int getHsLeave() {
        return HsLeave;
    }

    public void setHsLeave(int hsLeave) {
        HsLeave = hsLeave;
    }

    public int getIsKick() {
        return IsKick;
    }

    public void setIsKick(int isKick) {
        IsKick = isKick;
    }

    public Comment(int commentID, int threadID, int userID, String name, String imageName, String comment, int hsJoin, int hsComment, int hsLeave, int isKick) {
        CommentID = commentID;
        ThreadID = threadID;
        UserID = userID;
        Name = name;
        ImageName = imageName;
        Comment = comment;
        HsJoin = hsJoin;
        HsComment = hsComment;
        HsLeave = hsLeave;
        IsKick = isKick;
    }

    public Comment( String name, String comment) {
        Name = name;
        Comment = comment;
    }

    public Comment(int threadID, int userID, String name, String imageName, String comment, int hsJoin, int hsComment, int hsLeave, int isKick) {
        ThreadID = threadID;
        UserID = userID;
        Name = name;
        ImageName = imageName;
        Comment = comment;
        HsJoin = hsJoin;
        HsComment = hsComment;
        HsLeave = hsLeave;
        IsKick = isKick;
    }
}
