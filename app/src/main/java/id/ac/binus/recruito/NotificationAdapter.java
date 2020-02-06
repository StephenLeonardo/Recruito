package id.ac.binus.recruito;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.databinding.ListNotificationItemBinding;
import id.ac.binus.recruito.models.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    /*
    Modified by Stephen
    Date : Feb 06, 2020
    Purpose : Added global variable context
     */

    ArrayList<Notification> listNotif;
    Context mContext;

    public NotificationAdapter(Context mContext, ArrayList<Notification> listNotif) {

        this.mContext = mContext;
        this.listNotif = new ArrayList<>();
        this.listNotif = listNotif;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListNotificationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_notification_item, parent, false);
        binding.setNotif(new Notification());
        NotificationViewHolder vh = new NotificationViewHolder(binding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notif =listNotif.get(position);
        holder.binding.setNotif(notif);
        holder.bind(notif);
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

        /*
        Modified by Stephen
        Date : Feb 06, 2020
        Purpose : Added intent to go to Thread Activity
         */
        public void bind(final Notification item){
//            binding.
            Intent intent = new Intent(mContext.getApplicationContext(), ThreadDetailActivity.class);
            intent.putExtra("ThreadID", item.getThreadID());
            mContext.startActivity(intent);
        }

    }
}
