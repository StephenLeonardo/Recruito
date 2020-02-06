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
        BottomNavigationView topNav = findViewById(R.id.nav_btn_Navigation_Top);

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        topNav.setOnNavigationItemSelectedListener(navListener);

        Intent intent = getIntent();
        boolean goToProfileFragment =  intent.getBooleanExtra("goToProfileFragment", false);

        if(goToProfileFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileActivity()).commit();
        }

        else {
            // Go to home fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileActivity()).commit();
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
                            selectedFragment = new ProfileActivity();
                            break;
                        case R.id.nav_add:
                            selectedFragment = new AddJobListActivity();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileActivity();
                            break;
                        case R.id.nav_notifications:
                            selectedFragment = new NotificationActivity();
                            break;
                        case R.id.nav_filters:
                            selectedFragment = new FilterActivity();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}
