package id.ac.binus.recruito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.R;
import id.ac.binus.recruito.databinding.ListCommentItemBinding;
import id.ac.binus.recruito.databinding.ListJoinedPeopleItemBinding;
import id.ac.binus.recruito.models.Comment;
import id.ac.binus.recruito.models.User;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{

    private static final String TAG = "CommentAdapter";

    ArrayList<Comment> commentArrayList = new ArrayList<>();
    Context mContext;

    public CommentAdapter( Context mContext, ArrayList<Comment> commentArrayList) {
        this.commentArrayList = commentArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListCommentItemBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_comment_item, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comment comment = commentArrayList.get(position);

        holder.binding.setComment(comment);
    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ListCommentItemBinding binding;

        public MyViewHolder(@NonNull ListCommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
