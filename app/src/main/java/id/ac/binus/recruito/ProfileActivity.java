package id.ac.binus.recruito;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import id.ac.binus.recruito.databinding.ActivityProfileBinding;
import id.ac.binus.recruito.models.User;

public class ProfileActivity extends Fragment {

    private static final String TAG = "ProfileActivity";

    private User user;
    private ActivityProfileBinding binding;
    private ClickHandler handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_profile, container, false);
        View view = binding.getRoot();
        setProfileFromSharedPref();

        handler = new ClickHandler(getActivity());

        binding.setClickHandler(handler);

        return view;
    }

    private void setProfileFromSharedPref() {
        SharedPref sharedPref = new SharedPref(getActivity());
        user = sharedPref.load();

        Log.d(TAG, "setProfileFromSharedPref: Age = " + user.getAge());
        Log.d(TAG, "setProfileFromSharedPref: DOB = " + user.getDOB());

        binding.setUser(user);

        int id = getContext().getResources().getIdentifier("drawable/"+user.getImageName(), null, getContext().getPackageName());
        binding.imageViewProfilePic.setImageResource(id);

        user.setmContext(getActivity());

    }

    public class ClickHandler{
        private Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void logOutButtonClick(View view){
            SharedPref sharedPref = new SharedPref(getActivity());
            sharedPref.clearAll(getActivity());
            Intent intent = new Intent(getActivity(), FrontCoverActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        public void changeProfileButtonClick(View view){
            SharedPref sharedPref = new SharedPref(getActivity());
            user = sharedPref.load();
            Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
            intent.putExtra("UserID", user.getUserID());
            startActivity(intent);
            getActivity().finish();
        }

        public void changePasswordButtonClick(View view){
            SharedPref sharedPref = new SharedPref(getActivity());
            user = sharedPref.load();
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            intent.putExtra("UserID", user.getUserID());
            startActivity(intent);
            getActivity().finish();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setProfileFromSharedPref();
    }
}
