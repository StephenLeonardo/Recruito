package id.ac.binus.recruito;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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


        binding = DataBindingUtil.inflate(inflater, R.layout.activity_notification, container, false);
        View view = binding.getRoot();
        RecyclerView recyclerview= view.findViewById(R.id.rv_notif);

        ClickHandler clickHandler = new ClickHandler(getActivity());
        binding.setClickHandler(clickHandler);

        getNotifList();

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NotificationAdapter(getActivity(), listNotif);
        adapter.notifyDataSetChanged();
        recyclerview.setAdapter(adapter);


        return view;
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


    public class ClickHandler{
        private Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void goToHistory(View view){
            SharedPref sharedPref = new SharedPref(getActivity());
            user = sharedPref.load();
            Intent intent = new Intent(getActivity(), NavigationBarActivity.class);
            intent.putExtra("goToHistoryFragment", true);
            startActivity(intent);
            getActivity().finish();
        }
    }


}
