package id.ac.binus.recruito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    ImageView ProfilePic;
    TextView Name, Age, PhoneNumber, Email, Status;
    Button ChangeProfileButton, ChangePasswordButton, LogOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfilePic = findViewById(R.id.image_view_profile_pic);
        Name = findViewById(R.id.text_view_profile_name);
        Age = findViewById(R.id.text_view_age);
        PhoneNumber = findViewById(R.id.text_view_phone_number);
        Email = findViewById(R.id.text_view_email);
        Status = findViewById(R.id.text_view_status);
        ChangeProfileButton = findViewById(R.id.button_change_profile);

        setProfilePic(Name.getText().toString());

        ChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                intent.putExtra("name", Name.getText().toString());
                startActivity(intent);
            }
        });


    }

    private void setProfilePic(String Name){

        ProfilePic.setImageResource(R.drawable.fox);
    }

}
