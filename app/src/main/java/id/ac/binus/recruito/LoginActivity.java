package id.ac.binus.recruito;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        final String[] inputEmail = new String[1];
        final String[] inputPassword = new String[1];

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEmail[0] = Email.getText().toString();
                inputPassword[0] = Password.getText().toString();

                Log.d(TAG, "onClick: email = " + inputEmail[0]);
                Log.d(TAG, "onClick: password = " + inputPassword[0]);

                /*
                Modified by Stephen
                Date : Saturday Feb 01, 2020
                Purpose : Adding intent to go to next page

                Modified by Stephen
                Date : Monday Feb 03, 2020
                Purpose : Adding validation from database
                 */
                if (isValidInput(inputEmail[0], inputPassword[0])) {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.openDatabase();
                    Cursor cursor = databaseAccess.login(inputEmail[0], inputPassword[0]);

                    if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {

                        int UserID = cursor.getInt(cursor.getColumnIndex("UserID"));
                        int ImageID = cursor.getInt(cursor.getColumnIndex("ImageID"));
                        String UserName = cursor.getString(cursor.getColumnIndex("UserName"));
                        String DOB = cursor.getString(cursor.getColumnIndex("DOB"));
                        int Age = cursor.getInt(cursor.getColumnIndex("Age"));
                        String Gender = cursor.getString(cursor.getColumnIndex("Gender"));
                        String PhoneNumber = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
                        String UserStatus = cursor.getString(cursor.getColumnIndex("UserStatus"));
                        String Email = cursor.getString(cursor.getColumnIndex("Email"));
                        String UserPassword = cursor.getString(cursor.getColumnIndex("UserPassword"));
                        String ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));


                        // if cursor has value then in user database there is user associated with this given email
                        User currentUser = new User(UserID, ImageID, UserName, DOB, Age, Gender, PhoneNumber, UserStatus, Email, UserPassword, ImageName);

                        // Save user data into shared preference
                        SharedPref sharedPref = new SharedPref(LoginActivity.this);
                        sharedPref.save(currentUser);

                        Intent intent = new Intent(LoginActivity.this, NavigationBarActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    databaseAccess.closeDatabase();

                } else {
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