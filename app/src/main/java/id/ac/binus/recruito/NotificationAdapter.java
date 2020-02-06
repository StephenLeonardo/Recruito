package id.ac.binus.recruito;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.databinding.ListNotificationItemBinding;
import id.ac.binus.recruito.models.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    ArrayList<Notification> listNotif;

    public NotificationAdapter(ArrayList<Notification> listNotif) {
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

        public void bind(final Notification item){
//            binding.
        }

    }
}
