package id.ac.binus.recruito;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import id.ac.binus.recruito.adapter.GenderAdapter;
import id.ac.binus.recruito.adapter.StatusAdapter;
import id.ac.binus.recruito.models.StatusItem;
import id.ac.binus.recruito.models.User;

public class ChangeProfileActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddPersonalInformationA";

    private ArrayList<StatusItem> StatusList;
    private ArrayList<String> GenderList;
    private StatusAdapter StatusAdapterObj;
    private GenderAdapter genderAdapter;
    private TextView DateOfBirth;
    private EditText name,PhoneNumber;
    private Spinner Status, Gender;
    private Button ButtonConfirm;
    private StatusItem statusItem;
    private String genderString;
    private DatePickerDialog.OnDateSetListener DateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        // Fill status list with all status
        fillStatusList();
        fillGenderList();


        DateOfBirth = findViewById(R.id.text_view_date_picker);
        PhoneNumber = findViewById(R.id.edit_text_phone_number);
        Status = findViewById(R.id.spinner_status);
        Gender = findViewById(R.id.spinner_gender);
        ButtonConfirm = findViewById(R.id.button_next);
        name = findViewById(R.id.edit_text_name);

        StatusAdapterObj = new StatusAdapter(this, StatusList);
        Status.setAdapter(StatusAdapterObj);

        genderAdapter = new GenderAdapter(this, GenderList);
        Gender.setAdapter(genderAdapter);

        Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusItem = (StatusItem) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderString = (String) parent.getItemAtPosition(position);
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
                String monthEdited = Integer.toString(month);
                String dayOfMonthEdited = Integer.toString(dayOfMonth);
                if(month < 10){
                    monthEdited = "0" + month;
                }
                if(dayOfMonth < 10){
                    dayOfMonthEdited = "0" + dayOfMonth;
                }
                Log.d(TAG, "onDateSet: yyyy-mm-dd: " + year + "-" + monthEdited + "-" + dayOfMonthEdited);

                String Date = year + "-" + monthEdited + "-" + dayOfMonthEdited;
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
                                String status = statusItem.getStatusName();

                                SharedPref sharedPref = new SharedPref(ChangeProfileActivity.this);
                                User user = sharedPref.load();

                                int userID = user.getUserID();

                                // Update profile to database
                                try {
                                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ChangeProfileActivity.this);
                                    databaseAccess.openDatabase();

                                    boolean isSuccessUpdate = databaseAccess.updateProfile(userID, newName, genderString, DateOfBirth.getText().toString(), phoneNumber, status);

                                    if(isSuccessUpdate){
                                        user.setAge(getAgeFromDOB(DOB));
                                        user.setUserName(newName);
                                        user.setDOB(DateOfBirth.getText().toString());
                                        user.setPhoneNumber(phoneNumber);
                                        user.setUserStatus(status);

                                        sharedPref.clearAll(ChangeProfileActivity.this);

                                        sharedPref.save(user);

                                        dialog.dismiss();

                                        Log.d(TAG, "onClick: DOB = " + user.getDOB());

                                        Toast.makeText(ChangeProfileActivity.this, "Updated profile successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ChangeProfileActivity.this, NavigationBarActivity.class);
                                        intent.putExtra("goToProfileFragment", true);
                                        startActivity(intent);
                                        finish();
                                    }
                                    databaseAccess.closeDatabase();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(ChangeProfileActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                                }


                            }

                            private int getAgeFromDOB(String dob) {
                                int thisYear =  Calendar.getInstance().get(Calendar.YEAR);
                                int yearOfDOB = Integer.parseInt(dob.substring(0, 4));
                                return thisYear - yearOfDOB;
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
    Add all gender
     */
    private void fillGenderList() {
        GenderList = new ArrayList<>();
        GenderList.add("Male");
        GenderList.add("Female");
    }


    /*
    Add all category for job
     */
    private void fillStatusList() {
        StatusList = new ArrayList<>();
        StatusList.add(new StatusItem("Single"));
        StatusList.add(new StatusItem("Married"));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangeProfileActivity.this, NavigationBarActivity.class);
        intent.putExtra("goToProfileFragment", true);
        startActivity(intent);
        finish();
    }
}