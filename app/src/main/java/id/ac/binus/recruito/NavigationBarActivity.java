package id.ac.binus.recruito;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


/*
Modified by Stephen
Date : Feb 06, 2020
Purpose : ganti dari extends Fragment, jdi extends AppCompatActivity
 */
public class NavigationBarActivity extends AppCompatActivity {

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        BottomNavigationView bottomNav = findViewById(R.id.nav_btn_Navigation_Bottom);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Intent intent = getIntent();
        String goToWhichFragment = intent.getStringExtra("goToWhichFragment");

        if (goToWhichFragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(false)).commit();
        }
        else if (goToWhichFragment.equalsIgnoreCase("profile")) {
            // Go to profile fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileActivity()).commit();
        }
        else if (goToWhichFragment.equalsIgnoreCase("history")) {
            // Go to home fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(true)).commit();
        }
        else if (goToWhichFragment.equalsIgnoreCase("notification")){
            // Go to Notification fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationActivity()).commit();
        }
        else if (goToWhichFragment.equalsIgnoreCase("filter")){
            // Go to Filter fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FilterActivity()).commit();
        }
        else if (goToWhichFragment.equalsIgnoreCase("detail")){
            int threadID = intent.getIntExtra("ThreadID", 0);
            if(threadID != 0){
                // Go to Detail fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThreadDetailActivity(threadID)).commit();
            }
        }
    }


    /*
    Modified by Stephen
    Date : Feb 06, 2020
    Purpose : Tambah functionality untuk pindah pindah fragment
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            //Home Fragment here
                            selectedFragment = new HomeFragment(false);
                            break;
                        case R.id.nav_add:
                            selectedFragment = new AddJobListActivity();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileActivity();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

}
