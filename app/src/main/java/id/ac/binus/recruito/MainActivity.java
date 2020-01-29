package id.ac.binus.recruito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "MainActivity";

    private ArrayList<CategoryItem> CategoryList;
    private CategoryAdapter CategoryAdapter;
    private Spinner SpinnerCategories;
    private Button ButtonSubmit;
    private Button ButtonTimePicker;
    private TextView TextViewDatePicker;
    private DatePickerDialog.OnDateSetListener DateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initList();

        SpinnerCategories = findViewById(R.id.spinner_categories);
        ButtonSubmit = findViewById(R.id.button_submit);
        ButtonTimePicker = findViewById(R.id.button_time_picker);
        TextViewDatePicker = findViewById(R.id.text_view_date_picker);

        CategoryAdapter = new CategoryAdapter(this, CategoryList);
        SpinnerCategories.setAdapter(CategoryAdapter);

        SpinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryItem ClickedItem = (CategoryItem) parent.getItemAtPosition(position);
                String ClickedCategoryItem = ClickedItem.getCategoryName();
                Toast.makeText(MainActivity.this, ClickedCategoryItem + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
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
                        MainActivity.this,
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
    private void initList(){
        CategoryList = new ArrayList<>();
        CategoryList.add(new CategoryItem("Music"));
        CategoryList.add(new CategoryItem("Culinary"));
        CategoryList.add(new CategoryItem("Sports"));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ButtonTimePicker.setText(hourOfDay + ":" + minute);
    }
}
