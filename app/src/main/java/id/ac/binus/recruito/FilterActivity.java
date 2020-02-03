package id.ac.binus.recruito;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class FilterActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "FilterActivity";

    private LocationAdapter locationAdapter;
    private ArrayList<LocationItem> locationItemArrayList;
    private CategoryAdapter categoryAdapter;
    private ArrayList<CategoryItem> categoryItemArrayList;
    private Spinner spinnerLocation, spinnerCategory;
    private Button buttonTimePickerStart, buttonTimePickerEnd, buttonApply;
    private TextView textViewDatePicker;
    private EditText editTextPeopleRange;
    private CategoryItem clickedCategoryItem;
    private LocationItem clickedLocationItem;
    private DatePickerDialog.OnDateSetListener DateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        addLocation();
        addCategories();

        spinnerLocation = findViewById(R.id.spinner_location);
        spinnerCategory = findViewById(R.id.spinner_categories);
        buttonTimePickerStart = findViewById(R.id.button_time_picker_start);
        buttonTimePickerEnd = findViewById(R.id.button_time_picker_end);
        textViewDatePicker = findViewById(R.id.text_view_date_picker);
        editTextPeopleRange = findViewById(R.id.edit_text_total_people);
        buttonApply = findViewById(R.id.button_apply);


        setCurrentTime();

        buttonTimePickerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment TimePicker = new TimePickerFragment();
                TimePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        buttonTimePickerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment TimePicker = new TimePickerFragment();
                TimePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        textViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar CalendarObj = Calendar.getInstance();
                int year = CalendarObj.get(Calendar.YEAR);
                int month = CalendarObj.get(Calendar.MONTH);
                int day = CalendarObj.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog Dialog = new DatePickerDialog(
                        FilterActivity.this,
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
                textViewDatePicker.setText(Date);
            }
        };

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clickedCategoryItem = (CategoryItem) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clickedLocationItem = (LocationItem) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, ProfileActivity.class);
                intent.putExtra("location", clickedLocationItem.getLocationName());
                intent.putExtra("category", clickedCategoryItem.getCategoryName());
                intent.putExtra("startTime", buttonTimePickerStart.getText().toString());
                intent.putExtra("endTime", buttonTimePickerEnd.getText().toString());
                intent.putExtra("date", textViewDatePicker.getText().toString());
                intent.putExtra("peopleRange", editTextPeopleRange.getText().toString());
                startActivity(intent);
                finish();
            }
        });

    }

    private void addCategories() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(FilterActivity.this);
        categoryItemArrayList = databaseAccess.getAllCategory();

        // Set Adapter
        categoryAdapter = new CategoryAdapter(FilterActivity.this, categoryItemArrayList);
        spinnerCategory.setAdapter(categoryAdapter);

    }

    private void addLocation() {
        locationItemArrayList = new ArrayList<>();
        locationItemArrayList.add(new LocationItem("Jakarta Barat"));
        locationItemArrayList.add(new LocationItem("Jakarta Utara"));
        locationItemArrayList.add(new LocationItem("Jakarta Timur"));
        locationItemArrayList.add(new LocationItem("Jakarta Selatan"));
        locationItemArrayList.add(new LocationItem("Jakarta Pusat"));
        locationItemArrayList.add(new LocationItem("Sumatra Utara"));
        locationItemArrayList.add(new LocationItem("Sumatra Barat"));
        locationItemArrayList.add(new LocationItem("Sumatra Selatan"));
        locationItemArrayList.add(new LocationItem("Kalimantan Barat"));
        locationItemArrayList.add(new LocationItem("Kalimantan Tengah"));
        locationItemArrayList.add(new LocationItem("Kalimantan Timur"));
        locationItemArrayList.add(new LocationItem("Kalimantan Selatan"));
        locationItemArrayList.add(new LocationItem("Jawa Barat"));
        locationItemArrayList.add(new LocationItem("Jawa Tengah"));
        locationItemArrayList.add(new LocationItem("Jawa Timur"));
        locationItemArrayList.add(new LocationItem("Sulawesi Utara"));
        locationItemArrayList.add(new LocationItem("Sulawesi Tenggara"));
        locationItemArrayList.add(new LocationItem("Sulawesi Selatan"));
        locationItemArrayList.add(new LocationItem("Sulawesi Barat"));
        locationItemArrayList.add(new LocationItem("Maluku Utara"));
        locationItemArrayList.add(new LocationItem("Sulawesi Tenggara"));
        locationItemArrayList.add(new LocationItem("NTB"));
        locationItemArrayList.add(new LocationItem("NTT"));
        locationItemArrayList.add(new LocationItem("Papua Barat"));


        // Set Adapter
        locationAdapter = new LocationAdapter(FilterActivity.this, locationItemArrayList);
        spinnerLocation.setAdapter(locationAdapter);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        buttonTimePickerStart.setText(hourOfDay + ":" + minute);
        buttonTimePickerEnd.setText(hourOfDay + ":" + minute);
    }


    public void setCurrentTime() {
        Calendar CalendarObj = Calendar.getInstance();
        int hour = CalendarObj.get(Calendar.HOUR_OF_DAY);
        int minute = CalendarObj.get(Calendar.MINUTE);
        buttonTimePickerStart.setText(hour + ":" + minute);
        buttonTimePickerEnd.setText(hour + ":" + minute);
    }

}
