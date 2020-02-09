package id.ac.binus.recruito.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.NavigationBarActivity;
import id.ac.binus.recruito.R;
import id.ac.binus.recruito.SharedPref;
import id.ac.binus.recruito.databinding.ListJoinedPeopleItemBinding;
import id.ac.binus.recruito.models.User;

public class JoinedPeopleAdapter extends RecyclerView.Adapter<JoinedPeopleAdapter.MyViewHolder>{

    private static final String TAG = "JoinedPeopleAdapter";

    ArrayList<User> userArrayList;
    Context mContext;
    boolean isHistory;
    boolean isNotif;
    int threadID;
    String username;

    public JoinedPeopleAdapter(Context mContext, ArrayList<User> userArrayList, boolean isHistory, int threadID, boolean isBotif, String username) {
        this.userArrayList = userArrayList;
        this.mContext = mContext;
        this.isHistory = isHistory;
        this.threadID = threadID;
        this.isNotif = isBotif;
        this.username = username;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListJoinedPeopleItemBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_joined_people_item, parent, false
        );

        MyViewHolder viewHolder = new MyViewHolder(itemBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final User user = userArrayList.get(position);
        holder.binding.setUser(user);
        if (isHistory)
            holder.binding.buttonKick.setVisibility(View.GONE);

        SharedPref sharedPref = new SharedPref(mContext);
        User user1 = sharedPref.load();

        if (user1.getUserName().equalsIgnoreCase(username)){
            holder.binding.buttonKick.setVisibility(View.GONE);
        }

        holder.binding.buttonKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kickPeople(threadID, user.getUserID());
                Intent intent = new Intent(mContext.getApplicationContext(), NavigationBarActivity.class);
                intent.putExtra("goToWhichFragment", "detail");
                intent.putExtra("ThreadID", threadID);
                intent.putExtra("DetailTitle", isNotif ? "Notification" : null);
                mContext.startActivity(intent);
                ((NavigationBarActivity)mContext).finish();
            }

            private void kickPeople(int threadID, int userID) {
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ListJoinedPeopleItemBinding binding;

        public MyViewHolder(@NonNull ListJoinedPeopleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
