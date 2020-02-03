package id.ac.binus.recruito;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ChangeProfileActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddPersonalInformationA";

    private ArrayList<StatusItem> StatusList;
    private StatusAdapter StatusAdapterObj;
    private TextView DateOfBirth;
    private EditText name,PhoneNumber;
    private Spinner Status;
    private Button ButtonConfirm;
    private StatusItem ClickedItem;
    private DatePickerDialog.OnDateSetListener DateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_information);

        // Fill status list with all status
        initList();


        DateOfBirth = findViewById(R.id.text_view_date_picker);
        PhoneNumber = findViewById(R.id.edit_text_phone_number);
        Status = findViewById(R.id.spinner_status);
        ButtonConfirm = findViewById(R.id.button_next);
        name = findViewById(R.id.edit_text_name);

        StatusAdapterObj = new StatusAdapter(this, StatusList);
        Status.setAdapter(StatusAdapterObj);

        Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClickedItem = (StatusItem) parent.getItemAtPosition(position);
                String ClickedCategoryItem = ClickedItem.getStatusName();
                Toast.makeText(ChangeProfileActivity.this, ClickedCategoryItem + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar CalendarObj = Calendar.getInstance();
                int year = CalendarObj.get(Calendar.YEAR);
                int month = CalendarObj.get(Calendar.MONTH);
                int day = CalendarObj.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog Dialog = new DatePickerDialog(
                        ChangeProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateSetListener,
                        year, month, day);
                Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Dialog.show();
            }
        });

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String Date = month + "/" + dayOfMonth + "/" + year;
                DateOfBirth.setText(Date);
            }
        };

        ButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Dialog to confirm change profile
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeProfileActivity.this);
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String newName = name.getText().toString();
                                String DOB = DateOfBirth.getText().toString();
                                String phoneNumber = PhoneNumber.getText().toString();
                                String status = ClickedItem.getStatusName();

                                SharedPref sharedPref = new SharedPref(ChangeProfileActivity.this);
                                User user = sharedPref.load();

                                int userID = user.getUserID();

                                // Update profile to database
                                try {
                                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ChangeProfileActivity.this);
                                    databaseAccess.openDatabase();

                                    boolean isSuccessUpdate = databaseAccess.updateProfile(userID, newName, user.getGender(), DOB, phoneNumber, status);

                                    if(isSuccessUpdate){
                                        user.setUserName(newName);
                                        user.setDOB(DOB);
                                        user.setPhoneNumber(phoneNumber);
                                        user.setUserStatus(status);

                                        sharedPref.save(user);

                                        dialog.dismiss();

                                        Toast.makeText(ChangeProfileActivity.this, "Updated profile successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ChangeProfileActivity.this, ProfileActivity.class);
                                        startActivity(intent);
                                    }
                                    databaseAccess.closeDatabase();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                                Toast.makeText(ChangeProfileActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

            }
        });


    }

    /*
    Add all category for job
     */
    private void initList() {
        StatusList = new ArrayList<>();
        StatusList.add(new StatusItem("Single"));
        StatusList.add(new StatusItem("Married"));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


    }
}