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

public class LoginActivity extends AppCompatActivity {
    Button SignInButton;
    EditText Email, Password;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.edit_text_email);
        Password = findViewById(R.id.edit_text_password);
        SignInButton = findViewById(R.id.button_sign_in);

        final String[] inputEmail = new String[1];
        final String[] inputPassword = new String[1];

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                inputEmail[0] = Email.getText().toString();
                inputPassword[0] = Password.getText().toString();

                Log.d(TAG, "onClick: email = " + inputEmail[0]);
                Log.d(TAG, "onClick: password = " + inputPassword[0]);

                /*
                Modified by Stephen
                Date : Saturday Feb 01, 2020
                Purpose : Adding intent to go to next page
                 */
                if(isValidInput(inputEmail[0], inputPassword[0])){
                    Intent intent = new Intent(LoginActivity.this, AddPersonalInformationActivity.class);
                    intent.putExtra("email", inputEmail[0]);
                    intent.putExtra("password", inputPassword[0]);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
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

        if(Email == null || !matcher.matches() || Password == null || Password == "" ){
            return false;
        }
        return true;
    }
}