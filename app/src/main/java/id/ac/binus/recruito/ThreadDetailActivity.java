package id.ac.binus.recruito;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
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
    private JoinedPeopleAdapter joinedPeopleAdapter;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> commentList;
    private ArrayList<User> joinedPeopleList;
    private int countPeople = 0;
    JobThread jobThread;
    User user;

    public ThreadDetailActivity(int threadID) {
        this.threadID = threadID;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_thread_detail, container, false);
        View view = binding.getRoot();

        binding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countPeople++;
                binding.tvCountJoin.setText(Integer.toString(countPeople));
            }
        });

        getThreadInfo();

        setClickHandler();

        getJoinedPeople();
        setJoinedPeopleAdapter();

        getComments();
        setCommentAdapter();

        checkIfOwner();



        return view;
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
        joinedPeopleAdapter = new JoinedPeopleAdapter(getActivity(), joinedPeopleList);
        binding.recyclerViewPeople.setAdapter(joinedPeopleAdapter);
        binding.recyclerViewPeople.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getThreadInfo() {
        jobThread = new JobThread(1, "Budi", "0823834353", "Art", "Butuh Dokter", "19:00", "2020-02-16", "Jalan Syahdan",
                "Description 1", 10,3);
        binding.setThread(jobThread);
    }

    public void getJoinedPeople(){
        joinedPeopleList = new ArrayList<>();
        joinedPeopleList.add(new User("Budi"));
        joinedPeopleList.add(new User("Andi"));
        joinedPeopleList.add(new User("Siti"));
    }

    public void getComments(){
        commentList = new ArrayList<>();
        commentList.add(new Comment("Budi", "Comment si Budi"));
        commentList.add(new Comment("Siti", "Comment si Siti"));
    }


    public class ClickHandler {
        private static final String TAG = "ClickHandler";
        Context mContext;

        public ClickHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void joinThread(View view){

            Log.d(TAG, "joinThread: ");

            joinToDatabase(jobThread.getThreadID(), user.getUserID());
            Intent intent = new Intent(getActivity(), NavigationBarActivity.class);
            intent.putExtra("goToWhichFragment", "detail");
            intent.putExtra("ThreadID", jobThread.getThreadID());
            startActivity(intent);
            getActivity().finish();

        }

        // Belom dibikin
        private void joinToDatabase(int threadID, int userID){

        }

    }



}
