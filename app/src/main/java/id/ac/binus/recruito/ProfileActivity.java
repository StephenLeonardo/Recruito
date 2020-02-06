package id.ac.binus.recruito;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.binus.recruito.models.User;

public class ProfileActivity extends Fragment {

    private static final String TAG = "ProfileActivity";

    ImageView ProfilePic;
    TextView Name, Age, Gender, PhoneNumber, Email, Status;
    Button changeProfileButton, changePasswordButton, logOutButton;
    User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);

        ProfilePic = rootView.findViewById(R.id.image_view_profile_pic);
        Name = rootView.findViewById(R.id.text_view_profile_name);
        Age = rootView.findViewById(R.id.text_view_age);
        Gender = rootView.findViewById(R.id.text_view_gender);
        PhoneNumber = rootView.findViewById(R.id.text_view_phone_number);
        Email = rootView.findViewById(R.id.text_view_email);
        Status = rootView.findViewById(R.id.text_view_status);
        changeProfileButton = rootView.findViewById(R.id.button_change_profile);
        changePasswordButton = rootView.findViewById(R.id.button_change_password);
        logOutButton = rootView.findViewById(R.id.button_log_out);


        setProfileFromSharedPref();


        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref sharedPref = new SharedPref(getActivity());
                sharedPref.clearAll(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                intent.putExtra("UserID", user.getUserID());
                startActivity(intent);
                getActivity().finish();
            }
        });

        changeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
                intent.putExtra("UserID", user.getUserID());
                startActivity(intent);
                getActivity().finish();
            }
        });


        return rootView;
    }

    private void setProfileFromSharedPref() {
        SharedPref sharedPref = new SharedPref(getActivity());
        user = sharedPref.load();

        Log.d(TAG, "setProfileFromSharedPref: Age = " + user.getAge());
        Log.d(TAG, "setProfileFromSharedPref: DOB = " + user.getDOB());

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



        ProfilePic.setImageResource(getResources().getIdentifier(ImageName, "drawable", getActivity().getPackageName()));

    }

    @Override
    public void onResume() {
        super.onResume();
        setProfileFromSharedPref();
    }
}
