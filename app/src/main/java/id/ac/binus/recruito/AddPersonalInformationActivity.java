package id.ac.binus.recruito;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import id.ac.binus.recruito.adapter.GenderAdapter;
import id.ac.binus.recruito.adapter.StatusAdapter;
import id.ac.binus.recruito.backend.BackendAPI;
import id.ac.binus.recruito.models.StatusItem;
import id.ac.binus.recruito.models.User;

public class AddPersonalInformationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddPersonalInformationA";

    private ArrayList<StatusItem> StatusList;
    private ArrayList<String> GenderList;
    StatusAdapter StatusAdapterObj;
    GenderAdapter genderAdapter;
    TextView DateOfBirth;
    EditText PhoneNumber;
    Spinner Status, Gender;
    Button ButtonNext;
    StatusItem statusItem;
    String genderString;
    private DatePickerDialog.OnDateSetListener DateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_information);

        // Fill status list with all status
        fillStatusList();
        fillGenderList();


        DateOfBirth = findViewById(R.id.text_view_date_picker);
        PhoneNumber = findViewById(R.id.edit_text_phone_number);
        Status = findViewById(R.id.spinner_status);
        ButtonNext = findViewById(R.id.button_next);
        Gender = findViewById(R.id.spinner_gender);



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
                        AddPersonalInformationActivity.this,
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

        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                String email = intent.getStringExtra("email");
                String password = intent.getStringExtra("password");
                String DOB = DateOfBirth.getText().toString();
                String phoneNumber = PhoneNumber.getText().toString();
                String status = statusItem.getStatusName();

                Log.d(TAG, "onClick: name: " + name);
                Log.d(TAG, "onClick: email: " + email);
                Log.d(TAG, "onClick: password: " + password);
                Log.d(TAG, "onClick: DOB : " + DOB);
                Log.d(TAG, "onClick: Gender : " + genderString);
                Log.d(TAG, "onClick: Phone : " + phoneNumber);
                Log.d(TAG, "onClick: Status : " + status);


                int imageID = pickRandomProfileImage();

                String cryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

//                BackendAPI backendAPI = new BackendAPI(AddPersonalInformationActivity.this);
//
//                boolean isInserted = backendAPI.Register(name, password, email, DOB, status, phoneNumber);

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(AddPersonalInformationActivity.this);
                databaseAccess.openDatabase();

                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

                boolean isInserted = databaseAccess.insertUser(imageID, name, DOB, genderString, phoneNumber, status, email,hashedPassword);

                databaseAccess.closeDatabase();

                if (isInserted) {
                    Toast.makeText(AddPersonalInformationActivity.this, "Register success!", Toast.LENGTH_SHORT).show();

//                    User user = backendAPI.logIn(name, password);
                    databaseAccess.openDatabase();
                    Cursor cursor = databaseAccess.login(email);

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
                            if (BCrypt.checkpw(password, user.getUserPassword())){
                                SharedPref sharedPref = new SharedPref(AddPersonalInformationActivity.this);
                                sharedPref.save(user);

                                databaseAccess.closeDatabase();
                                intent = new Intent(AddPersonalInformationActivity.this, NavigationBarActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                                Toast.makeText(AddPersonalInformationActivity.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(AddPersonalInformationActivity.this, "Email incorrect", Toast.LENGTH_SHORT).show();
                    }

                    databaseAccess.closeDatabase();


                }
                else {
                    Toast.makeText(AddPersonalInformationActivity.this, "Register failed, please try again", Toast.LENGTH_SHORT).show();
                }

            }

            private int pickRandomProfileImage() {

                int lowestImageID = 1;
                int highestImageID = 12;

                Random rand = new Random();
                return rand.nextInt((highestImageID-lowestImageID) + 1) + lowestImageID;
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
}
