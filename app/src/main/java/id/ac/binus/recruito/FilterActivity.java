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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

import id.ac.binus.recruito.adapter.CategoryAdapter;
import id.ac.binus.recruito.adapter.LocationAdapter;
import id.ac.binus.recruito.models.CategoryItem;
import id.ac.binus.recruito.models.LocationItem;

public class FilterActivity extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "FilterActivity";

    private LocationAdapter locationAdapter;
    private ArrayList<LocationItem> locationItemArrayList;
    private CategoryAdapter categoryAdapter;
    private ArrayList<CategoryItem> categoryItemArrayList;
    private Spinner spinnerLocation, spinnerCategory;
    private Button buttonTimePickerStart, buttonTimePickerEnd, buttonApply, buttonReset;
    private TextView textViewDatePicker;
    private EditText editTextPeopleRange;
    private CategoryItem clickedCategoryItem;
    private LocationItem clickedLocationItem;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private Button increment, decrement;
    int totalPeople = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_filter, container, false);

        spinnerLocation = rootView.findViewById(R.id.spinner_location);
        spinnerCategory = rootView.findViewById(R.id.spinner_categories);
        buttonTimePickerStart = rootView.findViewById(R.id.button_time_picker_start);
        buttonTimePickerEnd = rootView.findViewById(R.id.button_time_picker_end);
        textViewDatePicker = rootView.findViewById(R.id.text_view_date_picker);
        editTextPeopleRange = rootView.findViewById(R.id.edit_text_total_people);
        buttonApply = rootView.findViewById(R.id.button_apply);
        editTextPeopleRange.setText("1");
        increment = rootView.findViewById(R.id.incrementButton);
        decrement = rootView.findViewById(R.id.decrementButton);

        totalPeople = Integer.parseInt(editTextPeopleRange.getText().toString());

        increment.setOnClickListener(new View.OnClickListener() {
            public void display(int totalPeople) {
                editTextPeopleRange.setText("" + totalPeople);
            }
            @Override
            public void onClick(View v) {
                int total = 0;
                if(!editTextPeopleRange.getText().toString().matches("")){
                    total = Integer.parseInt(editTextPeopleRange.getText().toString());
                    total++;
                }
                display(total);
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            public void display(int totalPeople) {
                editTextPeopleRange.setText("" + totalPeople);
            }
            @Override
            public void onClick(View v) {
                int total = 0;
                if(!editTextPeopleRange.getText().toString().matches("")){
                    total = Integer.parseInt(editTextPeopleRange.getText().toString());
                    if (total == 1) return;
                    total--;
                }
                display(total);
            }
        });


        addLocation();
        addCategories();

        setCurrentTime();

        buttonTimePickerStart.setOnClickListener(new View.OnClickListener() {
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
                        buttonTimePickerStart.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, DateFormat.is24HourFormat(getActivity()));
                TimePicker.show();
            }
        });

        buttonTimePickerEnd.setOnClickListener(new View.OnClickListener() {
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
                        buttonTimePickerEnd.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, DateFormat.is24HourFormat(getActivity()));
                TimePicker.show();
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
                Intent intent = new Intent(getActivity(), NavigationBarActivity.class);
                intent.putExtra("location", clickedLocationItem.getLocationName());
                intent.putExtra("category", clickedCategoryItem.getCategoryName());
                intent.putExtra("startTime", buttonTimePickerStart.getText().toString());
                intent.putExtra("endTime", buttonTimePickerEnd.getText().toString());
                intent.putExtra("date", textViewDatePicker.getText().toString());
                intent.putExtra("peopleRange", editTextPeopleRange.getText().toString());
                startActivity(intent);
                getActivity().finish();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NavigationBarActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return rootView;
    }

    private void addCategories() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        databaseAccess.openDatabase();
        categoryItemArrayList = databaseAccess.getAllCategory();
        databaseAccess.closeDatabase();

        // Set Adapter
        categoryAdapter = new CategoryAdapter(getActivity(), categoryItemArrayList);
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
        locationAdapter = new LocationAdapter(getActivity(), locationItemArrayList);
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
