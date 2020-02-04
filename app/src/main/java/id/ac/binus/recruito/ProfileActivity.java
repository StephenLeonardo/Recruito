package id.ac.binus.recruito;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import id.ac.binus.recruito.models.User;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    ImageView ProfilePic;
    TextView Name, Age, Gender, PhoneNumber, Email, Status;
    Button changeProfileButton, changePasswordButton, logOutButton;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfilePic = findViewById(R.id.image_view_profile_pic);
        Name = findViewById(R.id.text_view_profile_name);
        Age = findViewById(R.id.text_view_age);
        Gender = findViewById(R.id.text_view_gender);
        PhoneNumber = findViewById(R.id.text_view_phone_number);
        Email = findViewById(R.id.text_view_email);
        Status = findViewById(R.id.text_view_status);
        changeProfileButton = findViewById(R.id.button_change_profile);
        changePasswordButton = findViewById(R.id.button_change_password);
        logOutButton = findViewById(R.id.button_log_out);


        setProfileFromSharedPref();


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref sharedPref = new SharedPref(ProfileActivity.this);
                sharedPref.clearAll(ProfileActivity.this);
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                intent.putExtra("UserID", user.getUserID());
                startActivity(intent);
            }
        });

        changeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangeProfileActivity.class);
                intent.putExtra("UserID", user.getUserID());
                startActivity(intent);
            }
        });


    }

    private void setProfileFromSharedPref() {
        SharedPref sharedPref = new SharedPref(ProfileActivity.this);
        user = sharedPref.load();

        Log.d(TAG, "setProfileFromSharedPref: Age = " + user.getAge());

        Name.setText(user.getUserName());
        Gender.setText("Gender: " + user.getGender());
        Age.setText("Age: " + user.getAge());
        PhoneNumber.setText("Phone Number: " + user.getPhoneNumber());
        Email.setText("Email: " + user.getEmail());
        Status.setText("Status: " + user.getUserStatus());

        setProfilePic(user.getImageName());

    }

    private void setProfilePic(String ImageName) {

        ProfilePic.getLayoutParams().height = 400;
        ProfilePic.getLayoutParams().width = 400;

        ProfilePic.setImageResource(getResources().getIdentifier(ImageName, "drawable", getPackageName()));

    }

}
