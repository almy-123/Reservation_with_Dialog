package sg.edu.rp.c346.reservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etTelephone;
    EditText etSize;
    CheckBox checkBox;
    Button btReserve;
    Button btReset;
    EditText timePicker;
    EditText datePicker;

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("date", datePicker.getText().toString());
        prefEdit.putString("time", timePicker.getText().toString());
        prefEdit.putString("name", etName.getText().toString());
        prefEdit.putString("telephone", etTelephone.getText().toString());
        prefEdit.putString("size", etSize.getText().toString());
        prefEdit.putBoolean("smoking", checkBox.isChecked());

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String date = prefs.getString("date", "");
        String time = prefs.getString("time", "");
        String name = prefs.getString("name", "");
        String telephone = prefs.getString("telephone", "");
        String size = prefs.getString("size", "");
        Boolean smoke = prefs.getBoolean("smoking", false);

        datePicker.setText(date);
        timePicker.setText(time);
        etName.setText(name);
        etTelephone.setText(telephone);
        etSize.setText(size);
        checkBox.setChecked(smoke);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        datePicker = findViewById(R.id.etDatePicker);
        timePicker = findViewById(R.id.etTimePicker);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);

        datePicker.setFocusable(false);
        timePicker.setFocusable(false);

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                }
                else {
                    isSmoke = "non-smoking";
                }

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                myBuilder.setTitle("Confirm Your Order");
                String msg = "New Reservation\n"+"Name: "+etName.getText().toString()+
                        "\nSmoking: "+isSmoke+"\nSize: "+etSize.getText().toString()+"\nDate: "+
                        datePicker.getText()+"\nTime: "+timePicker.getText();
                myBuilder.setMessage(msg);
                myBuilder.setNeutralButton("Cancel", null);
                myBuilder.setPositiveButton("Confirm", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                checkBox.setChecked(false);
                Calendar curr = Calendar.getInstance();
                int day = curr.get(Calendar.DAY_OF_MONTH);
                int month = curr.get(Calendar.MONTH);
                int year = curr.get(Calendar.YEAR);
                int hour = curr.get(Calendar.HOUR_OF_DAY);
                int minute = curr.get(Calendar.MINUTE);
                datePicker.setText(day+"/"+month+"/"+year);
                timePicker.setText(hour+":"+minute);
            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener myDateListender = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        datePicker.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                };

                Calendar curr = Calendar.getInstance();
                int year = curr.get(Calendar.YEAR);
                int month = curr.get(Calendar.MONTH);
                int day = curr.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListender,
                        year, month, day);
                myDateDialog.show();
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timePicker.setText(hourOfDay+":"+minute);
                    }
                };

                Calendar curr = Calendar.getInstance();
                int hour = curr.get(Calendar.HOUR_OF_DAY);
                int minute = curr.get(Calendar.MINUTE);
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, hour, minute, true);
                myTimeDialog.show();
            }
        });


    }
}