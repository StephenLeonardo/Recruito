package id.ac.binus.recruito;

public class Thread {

    int ThreadID;
    String Username;
    String Category;
    String JobTitle;
    String JobTime;
    String JobDate;
    String JobAddress;
    String JobDescription;
    int TotalPeople;

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

    public Thread(int threadID, String username, String category, String jobTitle, String jobTime, String jobDate, String jobAddress, String jobDescription, int totalPeople) {
        ThreadID = threadID;
        Username = username;
        Category = category;
        JobTitle = jobTitle;
        JobTime = jobTime;
        JobDate = jobDate;
        JobAddress = jobAddress;
        JobDescription = jobDescription;
        TotalPeople = totalPeople;
    }
}
