package id.ac.binus.recruito;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.binus.recruito.adapter.NotificationAdapter;
import id.ac.binus.recruito.databinding.ActivityNotificationBinding;
import id.ac.binus.recruito.models.Notification;
import id.ac.binus.recruito.models.User;

public class NotificationActivity extends Fragment{

    ArrayList<Notification> listNotif = new ArrayList<>();
    NotificationAdapter adapter;
    ActivityNotificationBinding binding;
    DatabaseAccess databaseAccess;
    User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.activity_notification,container,false);
        RecyclerView recyclerview= rootView.findViewById(R.id.rv_notif);


        getNotifList();

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NotificationAdapter(getActivity(), listNotif);
        adapter.notifyDataSetChanged();
        recyclerview.setAdapter(adapter);


        return rootView;
    }

    //Function untuk mendapatkan list notification
    private void getNotifList(){

        //Ini Contoh list notifikasi
        listNotif.add(new Notification( 1, "Ini adalah notification 1", 1, "Budi", "01-02-2020", "10:00", false));
        listNotif.add(new Notification( 2, "Ini adalah notification 2", 2, "Andi", "02-02-2020", "11:00", false));
        listNotif.add(new Notification( 3, "Ini adalah notification 3", 3, "Ani", "03-02-2020", "12:00", false));
        listNotif.add(new Notification( 4, "Ini adalah notification 4", 4, "Lisa", "04-02-2020", "13:00", false));



        // get user data from shared pref
        SharedPref sharedPref = new SharedPref(getActivity());
        user = sharedPref.load();

        /*
        Modified by Stephen
        Date : Feb 06, 2020
        Purpose : Get notification data from database
         */
        // get all notification list from database
//        databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
//
//        databaseAccess.openDatabase();
//        listNotif = databaseAccess.getNotifList(user.getUserID());
//        databaseAccess.closeDatabase();

    }

}
