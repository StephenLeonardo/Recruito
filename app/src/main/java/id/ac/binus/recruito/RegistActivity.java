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

public class RegistActivity extends AppCompatActivity {
    Button SignUpButton;
    EditText Name, Email, Password, PasswordConfirmation;

    private static final String TAG = "RegistActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        Name = findViewById(R.id.edit_text_name);
        Email = findViewById(R.id.edit_text_email);
        Password = findViewById(R.id.edit_text_password);
        PasswordConfirmation = findViewById(R.id.edit_text_password_confirmation);
        SignUpButton = findViewById(R.id.button_sign_up);

        final String[] inputName = new String[1];
        final String[] inputEmail = new String[1];
        final String[] inputPassword = new String[1];
        final String[] inputPasswordConfirmation = new String[1];

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName[0] = Name.getText().toString();
                inputEmail[0] = Email.getText().toString();
                inputPassword[0] = Password.getText().toString();
                inputPasswordConfirmation[0] = PasswordConfirmation.getText().toString();

                Log.d(TAG, "onClick: name = " + inputName[0]);
                Log.d(TAG, "onClick: email = " + inputEmail[0]);
                Log.d(TAG, "onClick: password = " + inputPassword[0]);
                Log.d(TAG, "onClick: password confirmation = " + inputPasswordConfirmation[0]);

                /*
                Modified by Stephen
                Date : Saturday Feb 01, 2020
                Purpose : Adding intent to go to next page
                 */
                if (isValidInput(inputName[0], inputEmail[0], inputPassword[0], inputPasswordConfirmation[0])) {


                    Intent intent = new Intent(RegistActivity.this, AddPersonalInformationActivity.class);
                    intent.putExtra("name", inputName[0]);
                    intent.putExtra("email", inputEmail[0]);
                    intent.putExtra("password", inputPassword[0]);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegistActivity.this, "Please input data correctly", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    /*
    Modified by Stephen
    Date : Saturday Feb 01, 2020
    Purpose : Adding validation for registration
     */
    private boolean isValidInput(String Name, String Email, String Password, String ConfirmationPassword) {

        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(Email);

        if (Name == null || Name.equals("") || Name.equals(" ") ||
                !matcher.matches() ||
                Password == null || Password.equals("") ||
                ConfirmationPassword == null || !ConfirmationPassword.equals(Password)) {
            return false;
        }
        return true;
    }
}