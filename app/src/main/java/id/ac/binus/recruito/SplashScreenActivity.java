package id.ac.binus.recruito;

import androidx.appcompat.app.AppCompatActivity;

import android.app.slice.Slice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import id.ac.binus.recruito.models.User;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(isAlreadyLoggedIn()){
                    Intent intent = new Intent(SplashScreenActivity.this, NavigationBarActivity.class);
                    startActivity(intent);
                    finish();
                }

                Intent intent = new Intent(SplashScreenActivity.this, FrontCoverActivity.class);
                startActivity(intent);
                finish();

            }
        }, 2000);


    }

    private boolean isAlreadyLoggedIn() {
        SharedPref sharedPref = new SharedPref(SplashScreenActivity.this);
        User user = sharedPref.load();
        if(user == null || user.getUserName() == null) return false;
        return true;
    }
}
