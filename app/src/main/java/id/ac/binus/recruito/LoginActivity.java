package id.ac.binus.recruito;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.ac.binus.recruito.backend.BackendAPI;
import id.ac.binus.recruito.models.User;

public class LoginActivity extends AppCompatActivity {
    Button SignInButton;
    EditText Email, Password;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        if(isAlreadyLoggedIn()){
//            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
//            startActivity(intent);
//            finish();
//        }

        Email = findViewById(R.id.edit_text_email);
        Password = findViewById(R.id.edit_text_password);
        SignInButton = findViewById(R.id.button_sign_in);



        SignInButton.setOnClickListener(new View.OnClickListener() {
            String inputEmail = new String();
            String inputPassword = new String();

            @Override
            public void onClick(View v) {
                inputEmail = Email.getText().toString();
                inputPassword = Password.getText().toString();

                Log.d(TAG, "onClick: email = " + inputEmail);
                Log.d(TAG, "onClick: password = " + inputPassword);

                BackendAPI backendAPI = new BackendAPI(LoginActivity.this);
                User user = backendAPI.logIn(inputEmail, inputPassword);

                if(user != null){
                    SharedPref sharedPref = new SharedPref(LoginActivity.this);
                    sharedPref.save(user);

                    Intent intent = new Intent(LoginActivity.this, NavigationBarActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    /*
    Modified by Stephen
    Date : Saturday Feb 01, 2020
    Purpose : Adding validation for login input
     */
    private boolean isValidInput(String Email, String Password) {

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email);

        if (!matcher.matches() || Password == null || Password.equals("")) {
            return false;
        }
        return true;
    }

    /*
    Modified by Stephen
    Date : Monday Feb 03, 2020
    Purpose : Adding validation to check whether user has logged in before
     */
    private boolean isAlreadyLoggedIn() {
        SharedPref sharedPref = new SharedPref(LoginActivity.this);
        User user = sharedPref.load();
        if (user == null) {
            return false;
        }

        return true;
    }

}