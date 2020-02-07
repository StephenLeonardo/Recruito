package id.ac.binus.recruito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.R;
import id.ac.binus.recruito.databinding.ListNotificationDetailItemBinding;
import id.ac.binus.recruito.models.NotificationDetail;

public class NotificationDetailAdapter extends RecyclerView.Adapter {



    Context mContext;
    ArrayList<NotificationDetail> detailArrayList;


    public NotificationDetailAdapter(Context mContext, ArrayList<NotificationDetail> detailArrayList) {
        this.detailArrayList = new ArrayList<>();
        this.mContext = mContext;
        this.detailArrayList = detailArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListNotificationDetailItemBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_notification_detail_item, parent, false
        );

        NotificationDetailAdapter.NotificationDetailViewHolder viewHolder = new NotificationDetailAdapter.NotificationDetailViewHolder(itemBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NotificationDetailViewHolder extends RecyclerView.ViewHolder{

        ListNotificationDetailItemBinding binding;

        public NotificationDetailViewHolder(@NonNull ListNotificationDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
