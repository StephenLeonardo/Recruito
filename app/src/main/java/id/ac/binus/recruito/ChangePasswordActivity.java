package id.ac.binus.recruito;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

import id.ac.binus.recruito.models.User;

public class ChangePasswordActivity extends AppCompatActivity {
    Button ConfirmButton;
    EditText CurrentPassword, NewPassword, ConfirmNewPassword;

    private static final String TAG = "ChangePasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        CurrentPassword = findViewById(R.id.edit_text_current_password);
        NewPassword = findViewById(R.id.edit_text_new_password);
        ConfirmNewPassword = findViewById(R.id.edit_text_new_password_2);
        ConfirmButton = findViewById(R.id.button_confirm_change_password);


        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String inputCurrentPassword;
                String inputNewPassword;
                String inputConfirmNewPassword;

                inputCurrentPassword = CurrentPassword.getText().toString();
                inputNewPassword = NewPassword.getText().toString();
                inputConfirmNewPassword = ConfirmNewPassword.getText().toString();

                Log.d(TAG, "onClick: current password = " + inputCurrentPassword);
                Log.d(TAG, "onClick: new password = " + inputNewPassword);
                Log.d(TAG, "onClick: confirm new password = " + inputConfirmNewPassword);


                // input validation
                if (isCurrentPassword(inputCurrentPassword)) {
                    if (isValidPassword(inputNewPassword)) {
                        if (isSamePassword(inputNewPassword, inputConfirmNewPassword)) {

                            Intent intent = getIntent();
                            int userID = intent.getIntExtra("UserID", 0);

                            String cryptedPassword = BCrypt.hashpw(inputNewPassword, BCrypt.gensalt(12));

                            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                            databaseAccess.openDatabase();
                            databaseAccess.updatePassword(userID, cryptedPassword);
                            databaseAccess.closeDatabase();
                            Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }


            }

            /*
            Modified by Stephen
            Date : Sunday Feb 02, 2020
            Purpose : function to check password in database
             */
            private boolean isCurrentPassword(String password) {

                // Get current email
                SharedPref sharedPref = new SharedPref(ChangePasswordActivity.this);
                User user = sharedPref.load();

                return BCrypt.checkpw(password, user.getUserPassword());
            }

            /*
            Modified by Stephen
            Date : Sunday Feb 02, 2020
            Purpose : function to check if password is valid or not
             */
            private boolean isValidPassword(String newPassword) {
                if (newPassword == null || newPassword.equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "New Password Invalid", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

            /*
            Modified by Stephen
            Date : Sunday Feb 02, 2020
            Purpose : function to check if confirmationPassword is same with newPassword
             */
            private boolean isSamePassword(String newPassword, String confirmationPassword) {
                if (confirmationPassword == null || confirmationPassword.equals("") || !confirmationPassword.equals(newPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Confirmation Password Invalid", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

        });


    }


}