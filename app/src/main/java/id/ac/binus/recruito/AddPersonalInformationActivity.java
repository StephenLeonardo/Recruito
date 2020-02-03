package id.ac.binus.recruito;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class AddPersonalInformationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddPersonalInformationA";

    private ArrayList<StatusItem> StatusList;
    StatusAdapter StatusAdapterObj;
    TextView DateOfBirth;
    EditText PhoneNumber;
    Spinner Status;
    Button ButtonNext;
    StatusItem ClickedItem;
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
        ButtonNext = findViewById(R.id.button_next);

        StatusAdapterObj = new StatusAdapter(this, StatusList);
        Status.setAdapter(StatusAdapterObj);

        Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClickedItem = (StatusItem) parent.getItemAtPosition(position);
                String ClickedCategoryItem = ClickedItem.getStatusName();
                Toast.makeText(AddPersonalInformationActivity.this, ClickedCategoryItem + " selected", Toast.LENGTH_SHORT).show();
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
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String Date = month + "/" + dayOfMonth + "/" + year;
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

                Log.d(TAG, "onClick: name: " + name);
                Log.d(TAG, "onClick: email: " + email);
                Log.d(TAG, "onClick: password: " + password);
                Log.d(TAG, "onClick: DOB : " + DateOfBirth.getText().toString());


                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.openDatabase();
                boolean isInserted = databaseAccess.insertUser(1, name, DateOfBirth.getText().toString(), "Male", PhoneNumber.getText().toString(), ClickedItem.getStatusName(), email, password);
                if (isInserted) {
                    Toast.makeText(AddPersonalInformationActivity.this, "Register success!", Toast.LENGTH_SHORT).show();

                    Cursor csr = databaseAccess.getAllUsers();  //<<<<<<<<<< Get The Cursor
                    DatabaseUtils.dumpCursor(csr); //<<<<<<<<<< Dump the cursor (to the log)
                    csr.close(); //<<<<<<<<< Should always close a Cursor when done with it

//                    intent = new Intent(AddPersonalInformationActivity.this, ProfileActivity.class);
//                    startActivity(intent);
//                    finish();

                }
                else {
                    Toast.makeText(AddPersonalInformationActivity.this, "Register failed, please try again", Toast.LENGTH_SHORT).show();
                }
                databaseAccess.closeDatabase();

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
