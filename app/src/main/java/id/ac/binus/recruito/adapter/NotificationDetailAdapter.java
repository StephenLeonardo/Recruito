package id.ac.binus.recruito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.R;
import id.ac.binus.recruito.databinding.ListNotificationDetailItemBinding;
import id.ac.binus.recruito.models.NotificationDetail;

public class NotificationDetailAdapter extends RecyclerView.Adapter<NotificationDetailAdapter.NotificationDetailViewHolder> {

    private Context context;
    private ArrayList<NotificationDetail> detailArrayList;

    public NotificationDetailAdapter(Context mContext, ArrayList<NotificationDetail> detailArrayList) {
        context = mContext;
        this.detailArrayList = detailArrayList;
    }

    @NonNull
    @Override
    public NotificationDetailAdapter.NotificationDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_notification_detail_item, parent, false);
        return new NotificationDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationDetailAdapter.NotificationDetailViewHolder holder, int position) {
        NotificationDetail notificationDetail = detailArrayList.get(position);
        holder.text_view_detail.setText(notificationDetail.getMessage());
    }

    @Override
    public int getItemCount() {
        return detailArrayList.size();
    }

    public class NotificationDetailViewHolder extends RecyclerView.ViewHolder{


        TextView text_view_detail;

        public NotificationDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            text_view_detail = itemView.findViewById(R.id.text_view_notif_detail);
        }
    }

}
