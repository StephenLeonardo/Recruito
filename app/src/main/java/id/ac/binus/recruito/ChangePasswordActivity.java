package id.ac.binus.recruito;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        final String[] inputCurrentPassword = new String[1];
        final String[] inputNewPassword = new String[1];
        final String[] inputConfirmNewPassword = new String[1];

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCurrentPassword[0] = CurrentPassword.getText().toString();
                inputNewPassword[0] = NewPassword.getText().toString();
                inputConfirmNewPassword[0] = ConfirmNewPassword.getText().toString();

                Log.d(TAG, "onClick: current password = " + inputCurrentPassword[0]);
                Log.d(TAG, "onClick: new password = " + inputNewPassword[0]);
                Log.d(TAG, "onClick: confirm new password = " + inputConfirmNewPassword[0]);

                /*
                Modified by Stephen
                Date : Sunday Feb 02, 2020
                Purpose : Adding validation for inputs
                 */
                if (isCurrentPassword(inputCurrentPassword[0])) {
                    if (isValidPassword(inputNewPassword[0])) {
                        if (isSamePassword(inputNewPassword[0], inputConfirmNewPassword[0])) {
                            Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
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
                return true;
            }

            /*
            Modified by Stephen
            Date : Sunday Feb 02, 2020
            Purpose : function to check if password is valid or not
             */
            private boolean isValidPassword(String newPassword) {
                if (newPassword == null || newPassword == "") {
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
                if (confirmationPassword == null || confirmationPassword == "" || !confirmationPassword.equals(newPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Confirmation Password Invalid", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

        });

    }

}