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

import org.mindrot.jbcrypt.BCrypt;

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

                if(isValidInput(inputEmail, inputPassword)){
//                    BackendAPI backendAPI = new BackendAPI(LoginActivity.this);
//                    User user = backendAPI.logIn(inputEmail, inputPassword);

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(LoginActivity.this);
                    databaseAccess.openDatabase();

                    Cursor cursor = databaseAccess.login(inputEmail);

                    if(cursor != null && cursor.moveToFirst() && cursor.isFirst()){
                        User user =  new User();
                        user.setUserID(cursor.getInt(cursor.getColumnIndex("UserID")));
                        user.setUserName(cursor.getString(cursor.getColumnIndex("UserName")));
                        user.setAge(cursor.getInt(cursor.getColumnIndex("Age")));
                        user.setDOB(cursor.getString(cursor.getColumnIndex("DOB")));
                        user.setGender(cursor.getString(cursor.getColumnIndex("Gender")));
                        user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("PhoneNumber")));
                        user.setUserStatus(cursor.getString(cursor.getColumnIndex("UserStatus")));
                        user.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                        user.setUserPassword(cursor.getString(cursor.getColumnIndex("UserPassword")));
                        user.setImageName(cursor.getString(cursor.getColumnIndex("ImageName")));
                        if(user != null){
                            if (BCrypt.checkpw(inputPassword, user.getUserPassword())){
                                SharedPref sharedPref = new SharedPref(LoginActivity.this);
                                sharedPref.save(user);

                                databaseAccess.closeDatabase();
                                Intent intent = new Intent(LoginActivity.this, NavigationBarActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                                Toast.makeText(LoginActivity.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(LoginActivity.this, "Email incorrect", Toast.LENGTH_SHORT).show();
                    }

                    databaseAccess.closeDatabase();



                }
                else {
                    Toast.makeText(LoginActivity.this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    private boolean isValidInput(String Email, String Password) {

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email);

        if (!matcher.matches() || Password == null || Password.equals("")) {
            return false;
        }
        return true;
    }



}