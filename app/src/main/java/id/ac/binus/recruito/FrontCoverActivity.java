package id.ac.binus.recruito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import id.ac.binus.recruito.models.User;

public class    FrontCoverActivity extends AppCompatActivity {

    TextView Greeting;
    Button ButtonSignIn, ButtonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_cover);


        if(isAlreadyLoggedIn()){
            Intent intent = new Intent(FrontCoverActivity.this, NavigationBarActivity.class);
            startActivity(intent);
            finish();
        }

        Greeting = findViewById(R.id.text_view_greetings);
        ButtonSignIn = findViewById(R.id.button_sign_in);
        ButtonSignUp = findViewById(R.id.button_sign_up);

        setGreeting();

        ButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontCoverActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        ButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontCoverActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean isAlreadyLoggedIn() {
        SharedPref sharedPref = new SharedPref(FrontCoverActivity.this);
        User user = sharedPref.load();
        if(user == null) return false;
        return true;
    }

    public void setGreeting() {
        Calendar CalendarObj = Calendar.getInstance();
        int hour = CalendarObj.get(Calendar.HOUR_OF_DAY);
        int minute = CalendarObj.get(Calendar.MINUTE);

        String TextToGreet;

        if(hour > 5 && hour < 11){
            TextToGreet = "Good Morning";
        }
        else if(hour >= 11 && hour < 16){
            TextToGreet = "Good Afternoon";
        }
        else if(hour >= 16 && hour <= 21){
            TextToGreet = "Good Evening";
        }
        else{
            TextToGreet = "Good Night";
        }
        Greeting.setText(TextToGreet);
    }

}
