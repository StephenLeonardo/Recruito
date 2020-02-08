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
import id.ac.binus.recruito.databinding.ThreadJobListItemBinding;
import id.ac.binus.recruito.models.JobThread;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ViewHolder>{

    private static final String TAG = "ThreadAdapter";

    private Context mContext;
    private ArrayList<JobThread> jobThreadArrayList = new ArrayList<>();

    public ThreadAdapter(Context mContext, ArrayList<JobThread> jobThreadArrayList) {
        this.mContext = mContext;
        this.jobThreadArrayList = jobThreadArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ThreadJobListItemBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()), R.layout.thread_job_list_item, viewGroup, false
        );

        ViewHolder viewHolder = new ViewHolder(itemBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final JobThread jobThread = jobThreadArrayList.get(position);
        viewHolder.itemLayoutBinding.setThread(jobThread);

        viewHolder.itemLayoutBinding.relativeLayoutThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NavigationBarActivity.class);
                intent.putExtra("goToWhichFragment", "detail");
                intent.putExtra("ThreadID", jobThread.getThreadID());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return jobThreadArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ThreadJobListItemBinding itemLayoutBinding;

        public ViewHolder(@NonNull ThreadJobListItemBinding itemView) {
            super(itemView.getRoot());
            itemLayoutBinding = itemView;
        }
    }

}
