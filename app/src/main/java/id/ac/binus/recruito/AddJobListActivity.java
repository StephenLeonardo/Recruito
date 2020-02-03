package id.ac.binus.recruito;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
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

public class AddJobListActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddJobListActivity";

    private ArrayList<CategoryItem> CategoryList;
    private CategoryAdapter CategoryAdapter;
    private Spinner SpinnerCategories;
    private Button ButtonSubmit;
    private Button ButtonTimePicker;
    private TextView TextViewDatePicker;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private CategoryItem ClickedItem;
    private EditText editTextJobTitle, editTextAddress, editTextJobDesc, editTextTotalPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_list);

        initList();

        SpinnerCategories = findViewById(R.id.spinner_categories);
        ButtonSubmit = findViewById(R.id.button_submit);
        ButtonTimePicker = findViewById(R.id.button_time_picker);
        TextViewDatePicker = findViewById(R.id.text_view_date_picker);
        editTextJobTitle = findViewById(R.id.edit_text_job_title);
        editTextAddress = findViewById(R.id.edit_text_job_address);
        editTextJobDesc = findViewById(R.id.edit_text_job_desc);
        editTextTotalPeople = findViewById(R.id.edit_text_total_people);

        CategoryAdapter = new CategoryAdapter(this, CategoryList);
        SpinnerCategories.setAdapter(CategoryAdapter);

        setCurrentTime();

        SpinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClickedItem = (CategoryItem) parent.getItemAtPosition(position);
                String ClickedCategoryItem = ClickedItem.getCategoryName();
                Toast.makeText(AddJobListActivity.this, position + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        Modified by Stephen
        Date : Monday Feb 02 3 2020
        Purpose : Add thread into database
         */
        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*
                Commented by Stephen
                Date : Monday Feb 03, 2020
                Purpose : CategoryID needs to be updated
                 */
                int categoryID = 1;
                String title = editTextJobTitle.getText().toString();
                String time = ButtonTimePicker.getText().toString();
                String date = TextViewDatePicker.getText().toString();
                String address = editTextAddress.getText().toString();
                String jobDesc = editTextJobDesc.getText().toString();
                int totalPeople = Integer.parseInt(editTextTotalPeople.getText().toString());

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(AddJobListActivity.this);
                databaseAccess.openDatabase();
                try {
                    databaseAccess.insertThread(categoryID, title, time, date, address, jobDesc, totalPeople);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                databaseAccess.closeDatabase();

                Toast.makeText(AddJobListActivity.this, "Thread inserted", Toast.LENGTH_SHORT).show();
            }
        });

        ButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment TimePicker = new TimePickerFragment();
                TimePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        TextViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar CalendarObj = Calendar.getInstance();
                int year = CalendarObj.get(Calendar.YEAR);
                int month = CalendarObj.get(Calendar.MONTH);
                int day = CalendarObj.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog Dialog = new DatePickerDialog(
                        AddJobListActivity.this,
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
                TextViewDatePicker.setText(Date);
            }
        };


    }


    /*
    Add all category for job
     */
    private void initList() {
        CategoryList = new ArrayList<>();
        CategoryList.add(new CategoryItem("Music"));
        CategoryList.add(new CategoryItem("Culinary"));
        CategoryList.add(new CategoryItem("Sports"));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ButtonTimePicker.setText(hourOfDay + ":" + minute);
    }


    public void setCurrentTime() {
        Calendar CalendarObj = Calendar.getInstance();
        int hour = CalendarObj.get(Calendar.HOUR_OF_DAY);
        int minute = CalendarObj.get(Calendar.MINUTE);
        ButtonTimePicker.setText(hour + ":" + minute);
    }

}
