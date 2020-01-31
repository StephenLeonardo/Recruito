package id.ac.binus.recruito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    ImageView ProfilePic;
    TextView Name, Age, PhoneNumber, Email, Status;
    Button ChangeProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfilePic = findViewById(R.id.image_view_profile_pic);
        Name = findViewById(R.id.text_view_profile_name);
        Age = findViewById(R.id.text_view_age);
        PhoneNumber = findViewById(R.id.text_view_email);
        Status = findViewById(R.id.text_view_status);
        ChangeProfileButton = findViewById(R.id.button_change_profile);





    }
}
