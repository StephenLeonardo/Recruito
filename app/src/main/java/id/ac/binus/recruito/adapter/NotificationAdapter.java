package id.ac.binus.recruito.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.NavigationBarActivity;
import id.ac.binus.recruito.NotificationActivity;
import id.ac.binus.recruito.R;
import id.ac.binus.recruito.ThreadDetailActivity;
import id.ac.binus.recruito.databinding.ListNotificationItemBinding;
import id.ac.binus.recruito.models.Notification;
import id.ac.binus.recruito.models.NotificationDetail;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    private static final String TAG = "NotificationAdapter";

    ArrayList<Notification> listNotif;
    Context mContext;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    ArrayList<NotificationDetail> detailArrayList;
    NotificationDetailAdapter adapter;

    public NotificationAdapter(Context mContext, ArrayList<Notification> listNotif) {

        this.mContext = mContext;
        this.listNotif = new ArrayList<>();
        this.listNotif = listNotif;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListNotificationItemBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_notification_item, parent, false
        );

        NotificationViewHolder viewHolder = new NotificationViewHolder(itemBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder holder, int position) {
        final Notification notif =listNotif.get(position);

        holder.binding.setNotif(notif);

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.binding.rvNotifMessage.getContext(), LinearLayoutManager.VERTICAL, false);
        fillDetailArrayList(position);
        notif.setNotificationDetails(detailArrayList);

        layoutManager.setInitialPrefetchItemCount(notif.getNotificationDetails().size());

        adapter = new NotificationDetailAdapter(mContext, detailArrayList);


        holder.binding.rvNotifMessage.setLayoutManager(layoutManager);
        holder.binding.rvNotifMessage.setAdapter(adapter);
        holder.binding.rvNotifMessage.setRecycledViewPool(viewPool);

        holder.binding.relativeLayoutNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NavigationBarActivity.class);
                intent.putExtra("goToWhichFragment", "detail");
                intent.putExtra("ThreadID", notif.getThreadID());
                mContext.startActivity(intent);
            }
        });

    }

    private void fillDetailArrayList(int position) {
        detailArrayList = new ArrayList<>();

        detailArrayList.add(new NotificationDetail("Detail " + position));
        detailArrayList.add(new NotificationDetail("Detail " + ++position));
a
    }

    @Override
    public int getItemCount() {
        return listNotif.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        ListNotificationItemBinding binding;

        public NotificationViewHolder(@NonNull ListNotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(final Notification item){
//            binding.
            Intent intent = new Intent(mContext.getApplicationContext(), ThreadDetailActivity.class);
            intent.putExtra("ThreadID", item.getThreadID());
            mContext.startActivity(intent);
        }

    }


}
