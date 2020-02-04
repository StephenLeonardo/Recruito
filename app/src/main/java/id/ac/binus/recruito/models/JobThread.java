package id.ac.binus.recruito.models;

public class JobThread {

    int ThreadID;
    String Username;
    String PhoneNumber;
    String Category;
    String JobTitle;
    String JobTime;
    String JobDate;
    String JobAddress;
    String JobDescription;
    int TotalPeople;
    int JoinedPeople;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public JobThread(int threadID, String username, String phoneNumber, String category, String jobTitle, String jobTime, String jobDate, String jobAddress, String jobDescription, int totalPeople, int joinedPeople) {
        ThreadID = threadID;
        Username = username;
        PhoneNumber = phoneNumber;
        Category = category;
        JobTitle = jobTitle;
        JobTime = jobTime;
        JobDate = jobDate;
        JobAddress = jobAddress;
        JobDescription = jobDescription;
        TotalPeople = totalPeople;
        JoinedPeople = joinedPeople;
    }

    public int getJoinedPeople() {
        return JoinedPeople;
    }

    public void setJoinedPeople(int joinedPeople) {
        JoinedPeople = joinedPeople;
    }

    public int getThreadID() {
        return ThreadID;
    }

    public void setThreadID(int threadID) {
        ThreadID = threadID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getJobTime() {
        return JobTime;
    }

    public void setJobTime(String jobTime) {
        JobTime = jobTime;
    }

    public String getJobDate() {
        return JobDate;
    }

    public void setJobDate(String jobDate) {
        JobDate = jobDate;
    }

    public String getJobAddress() {
        return JobAddress;
    }

    public void setJobAddress(String jobAddress) {
        JobAddress = jobAddress;
    }

    public String getJobDescription() {
        return JobDescription;
    }

    public void setJobDescription(String jobDescription) {
        JobDescription = jobDescription;
    }

    public int getTotalPeople() {
        return TotalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        TotalPeople = totalPeople;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public JobThread(int threadID, String username, String category, String jobTitle, String jobTime, String jobDate, String jobAddress, String jobDescription, int totalPeople, int joinedPeople) {
        ThreadID = threadID;
        Username = username;
        Category = category;
        JobTitle = jobTitle;
        JobTime = jobTime;
        JobDate = jobDate;
        JobAddress = jobAddress;
        JobDescription = jobDescription;
        TotalPeople = totalPeople;
        JoinedPeople = joinedPeople;
    }
}
