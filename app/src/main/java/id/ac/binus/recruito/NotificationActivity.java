package id.ac.binus.recruito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;

import id.ac.binus.recruito.databinding.ActivityNotificationBinding;
import id.ac.binus.recruito.models.Notification;

public class NotificationActivity extends AppCompatActivity {

    ArrayList<Notification> listNotif = new ArrayList<>();
    NotificationAdapter adapter;
    ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        binding.rvNotif.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotificationAdapter(listNotif);
        binding.rvNotif.setAdapter(adapter);

        getNotifList();
    }

    //Function untuk mendapatkan list notification
    private void getNotifList(){

        //Ini Contoh list notifikasi
        listNotif.add(new Notification( "Ini adalah notification 1", "1", "01-02-2020", "10:00", false));
        listNotif.add(new Notification( "Ini adalah notification 2", "1", "02-02-2020", "11:00", false));
        listNotif.add(new Notification( "Ini adalah notification 3", "1", "03-02-2020", "12:00", false));
        listNotif.add(new Notification( "Ini adalah notification 4", "1", "04-02-2020", "13:00", false));

        adapter.notifyDataSetChanged();
    }
}
