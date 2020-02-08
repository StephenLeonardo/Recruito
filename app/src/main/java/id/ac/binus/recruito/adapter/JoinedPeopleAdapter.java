package id.ac.binus.recruito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.R;
import id.ac.binus.recruito.databinding.ListJoinedPeopleItemBinding;
import id.ac.binus.recruito.databinding.ListNotificationItemBinding;
import id.ac.binus.recruito.models.Comment;
import id.ac.binus.recruito.models.User;

public class JoinedPeopleAdapter extends RecyclerView.Adapter<JoinedPeopleAdapter.MyViewHolder>{

    private static final String TAG = "JoinedPeopleAdapter";

    ArrayList<User> userArrayList = new ArrayList<>();
    Context mContext;

    public JoinedPeopleAdapter( Context mContext, ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
        this.mContext = mContext;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userArrayList.get(position);

        holder.binding.setUser(user);
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
