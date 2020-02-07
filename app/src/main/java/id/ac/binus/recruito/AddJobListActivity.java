package id.ac.binus.recruito;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

import id.ac.binus.recruito.adapter.CategoryAdapter;
import id.ac.binus.recruito.models.CategoryItem;

public class AddJobListActivity extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AddJobListActivity";

    private ArrayList<CategoryItem> CategoryList;
    private id.ac.binus.recruito.adapter.CategoryAdapter CategoryAdapter;
    private Spinner SpinnerCategories;
    private Button ButtonSubmit;
    private Button ButtonTimePicker;
    private TextView TextViewDatePicker;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private CategoryItem ClickedItem;
    private EditText editTextJobTitle, editTextAddress, editTextJobDesc, editTextTotalPeople;
    private Button increment, decrement;
    int totalPeople = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.activity_add_job_list, container, false);


        FillCategory();

        SpinnerCategories = rootView.findViewById(R.id.spinner_categories);
        ButtonSubmit = rootView.findViewById(R.id.button_submit);
        ButtonTimePicker = rootView.findViewById(R.id.button_time_picker);
        TextViewDatePicker = rootView.findViewById(R.id.text_view_date_picker);
        editTextJobTitle = rootView.findViewById(R.id.edit_text_job_title);
        editTextAddress = rootView.findViewById(R.id.edit_text_job_address);
        editTextJobDesc = rootView.findViewById(R.id.edit_text_job_desc);
        editTextTotalPeople = rootView.findViewById(R.id.edit_text_total_people);
        editTextTotalPeople.setText("1");
        increment = rootView.findViewById(R.id.incrementButton);
        decrement = rootView.findViewById(R.id.decrementButton);

        CategoryAdapter = new CategoryAdapter(getActivity(), CategoryList);
        SpinnerCategories.setAdapter(CategoryAdapter);

        totalPeople = Integer.parseInt(editTextTotalPeople.getText().toString());

        increment.setOnClickListener(new View.OnClickListener() {
            public void display(int totalPeople) {
                editTextTotalPeople.setText("" + totalPeople);
            }
            @Override
            public void onClick(View v) {
                int total = Integer.parseInt(editTextTotalPeople.getText().toString());
                total++;
                display(total);
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            public void display(int totalPeople) {
                editTextTotalPeople.setText("" + totalPeople);
            }
            @Override
            public void onClick(View v) {
                int total = Integer.parseInt(editTextTotalPeople.getText().toString());
                if (total == 1) return;
                total--;
                display(total);
            }
        });

        setCurrentTime();

        SpinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClickedItem = (CategoryItem) parent.getItemAtPosition(position);
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


                String categoryName = ClickedItem.getCategoryName();
                String title = editTextJobTitle.getText().toString();
                String time = ButtonTimePicker.getText().toString();
                String date = TextViewDatePicker.getText().toString();
                String address = editTextAddress.getText().toString();
                String jobDesc = editTextJobDesc.getText().toString();
                int totalPeople = Integer.parseInt(editTextTotalPeople.getText().toString());

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                databaseAccess.openDatabase();
                try {
                    databaseAccess.insertThread(categoryName, title, time, date, address, jobDesc, totalPeople);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                databaseAccess.closeDatabase();

                Toast.makeText(getActivity(), "JobThread inserted", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), NavigationBarActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });

        ButtonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar CalendarObj = Calendar.getInstance();
                int hour = CalendarObj.get(Calendar.HOUR_OF_DAY);
                int minute = CalendarObj.get(Calendar.MINUTE);

                TimePickerDialog TimePicker = new TimePickerDialog(getActivity(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        ButtonTimePicker.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, DateFormat.is24HourFormat(getActivity()));
                TimePicker.show();

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
                        getActivity(),
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
                TextViewDatePicker.setText(Date);
            }
        };

        return rootView;
    }


    /*
    Add all category for job
     */
    private void FillCategory() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        databaseAccess.openDatabase();
        CategoryList = databaseAccess.getAllCategory();
        databaseAccess.closeDatabase();

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
