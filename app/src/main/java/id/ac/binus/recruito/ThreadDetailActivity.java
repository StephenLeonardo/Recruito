package id.ac.binus.recruito;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import id.ac.binus.recruito.adapter.CommentAdapter;
import id.ac.binus.recruito.adapter.JoinedPeopleAdapter;
import id.ac.binus.recruito.databinding.ActivityThreadDetailBinding;
import id.ac.binus.recruito.models.Comment;
import id.ac.binus.recruito.models.JobThread;
import id.ac.binus.recruito.models.User;

public class ThreadDetailActivity extends Fragment {
    private static final String TAG = "ThreadDetailActivity";


    ActivityThreadDetailBinding binding;

    private int threadID;
    private String title;
    private boolean isHistory;
    private JoinedPeopleAdapter joinedPeopleAdapter;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> commentList;
    private ArrayList<User> joinedPeopleList;
    JobThread jobThread;
    User user;

    public ThreadDetailActivity(int threadID) {
        this.threadID = threadID;
        title = null;
    }

    public ThreadDetailActivity(int threadID, String detailTitle) {
        this.threadID = threadID;
        title = detailTitle;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_thread_detail, container, false);
        View view = binding.getRoot();

        getThreadInfo();

        setTitle();

        setClickHandler();

        getJoinedPeople();
        setJoinedPeopleAdapter();

        getComments();
        setCommentAdapter();

        checkIfOwner();



        return view;
    }

    private void setTitle() {

        if (title == null)
            binding.textViewDetailTitle.setVisibility(View.GONE);
        else{
            binding.textViewDetailTitle.setText(title);

            if (title.equalsIgnoreCase("History")){
                binding.btnJoin.setVisibility(View.GONE);
                isHistory = true;
            }
        }

        SharedPref sharedPref = new SharedPref(getActivity());
        User user = sharedPref.load();

        if (user.getUserName().equalsIgnoreCase(jobThread.getUsername())){
            binding.btnJoin.setVisibility(View.GONE);
        }

    }

    private void setClickHandler() {
        ClickHandler handler =  new ClickHandler(getActivity());
        binding.setClickHandler(handler);
    }

    private void checkIfOwner() {
        SharedPref sharedPref = new SharedPref(getActivity());
        user = sharedPref.load();

        if(user.getUserName().equals(jobThread.getUsername())){
           binding.btnJoin.setVisibility(View.GONE);
        }


    }

    private void setCommentAdapter() {
        commentAdapter = new CommentAdapter(getActivity(), commentList);
        binding.recyclerViewComment.setAdapter(commentAdapter);
        binding.recyclerViewComment.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setJoinedPeopleAdapter() {
        if (title == null)
            joinedPeopleAdapter = new JoinedPeopleAdapter(getActivity(), joinedPeopleList, isHistory, jobThread.getThreadID(), false, jobThread.getUsername());
        else
            joinedPeopleAdapter = new JoinedPeopleAdapter(getActivity(), joinedPeopleList, isHistory, jobThread.getThreadID(), true,  jobThread.getUsername());
        binding.recyclerViewPeople.setAdapter(joinedPeopleAdapter);
        binding.recyclerViewPeople.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getThreadInfo() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        databaseAccess.openDatabase();
        jobThread = databaseAccess.getSpecificPageData(threadID);
        databaseAccess.closeDatabase();
        Log.d(TAG, "getThreadInfo: Joined People : " + jobThread.getJoinedPeople());

        binding.setThread(jobThread);
    }

    public void getJoinedPeople(){
        joinedPeopleList = new ArrayList<>();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        joinedPeopleList.addAll(databaseAccess.getAllJoinedPeople(threadID));

    }

    public void getComments(){
        commentList = new ArrayList<>();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        commentList.addAll(databaseAccess.getAllComments(threadID));
    }


    public class ClickHandler {
        private static final String TAG = "ClickHandler";
        Context mContext;

        public ClickHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void joinThread(View view){

            Log.d(TAG, "joinThread: ");

            SharedPref sharedPref = new SharedPref(getActivity());
            User user1 = sharedPref.load();

            joinToDatabase(jobThread.getThreadID(), user1.getUserID());
            Intent intent = new Intent(getActivity(), NavigationBarActivity.class);
            intent.putExtra("goToWhichFragment", "detail");
            intent.putExtra("ThreadID", jobThread.getThreadID());
            startActivity(intent);
            getActivity().finish();

        }

        public void addComment(View view) {
            String comment = binding.editTextComment.getText().toString();
            if (comment == null) return;

            addComentToDatabase(threadID, user.getUserID(), comment);

            Intent intent = new Intent(getActivity(), NavigationBarActivity.class);
            intent.putExtra("goToWhichFragment", "detail");
            intent.putExtra("ThreadID", jobThread.getThreadID());
            startActivity(intent);
            getActivity().finish();

        }

        private void addComentToDatabase(int threadID, int userID, String comment) {

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());

            databaseAccess.openDatabase();
            boolean isSuccsessComment = databaseAccess.insertComment(userID, threadID, comment);
            databaseAccess.closeDatabase();

        }

        // Belom dibikin
        private void joinToDatabase(int threadID, int userID){

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
            databaseAccess.openDatabase();
            boolean isSuccessJoin = databaseAccess.joinThread(userID, threadID);
            databaseAccess.closeDatabase();

        }

    }



}
