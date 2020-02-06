package id.ac.binus.recruito;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import id.ac.binus.recruito.databinding.ActivityNotificationBinding;
import id.ac.binus.recruito.models.Notification;
import id.ac.binus.recruito.models.User;

public class NotificationActivity extends Fragment {

    ArrayList<Notification> listNotif = new ArrayList<>();
    NotificationAdapter adapter;
    ActivityNotificationBinding binding;
    DatabaseAccess databaseAccess;
    User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_notification, container, false);

        binding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_notification);
        binding.rvNotif.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new NotificationAdapter(getActivity(), listNotif);
        binding.rvNotif.setAdapter(adapter);

        getNotifList();

        return rootView;
    }

    //Function untuk mendapatkan list notification
    private void getNotifList(){

        /*
        Commented by Stephen
        Date : Feb 06, 2020
        Purpose : Contoh cara add listNotif
         */
        //Ini Contoh list notifikasi
//        listNotif.add(new Notification( 1, "Ini adalah notification 1", 1, "01-02-2020", "10:00", false));
//        listNotif.add(new Notification( 1, "Ini adalah notification 2", 1, "02-02-2020", "11:00", false));
//        listNotif.add(new Notification( 1, "Ini adalah notification 3", 1, "03-02-2020", "12:00", false));
//        listNotif.add(new Notification( 1, "Ini adalah notification 4", 1, "04-02-2020", "13:00", false));

        // get user data from shared pref
        SharedPref sharedPref = new SharedPref(getActivity());
        user = sharedPref.load();

        /*
        Modified by Stephen
        Date : Feb 06, 2020
        Purpose : Get notification data from database
         */
        // get all notification list from database
        databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());

        databaseAccess.openDatabase();
        listNotif = databaseAccess.getNotifList(user.getUserID());
        databaseAccess.closeDatabase();

        adapter.notifyDataSetChanged();
    }
}
