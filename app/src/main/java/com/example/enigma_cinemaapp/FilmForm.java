package com.example.enigma_cinemaapp;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;

public class FilmForm extends AppCompatActivity {

    Button bSavedButton;

    EditText etMovieName;
    TextView tvMovieName;

    TextView mTextViewDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    TextView mTextViewTime;
    TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_form);

        mTextViewDate = (TextView) findViewById(R.id.TextViewDate);
        bSavedButton = (Button) findViewById(R.id.ButtonSave);
        etMovieName = (EditText) findViewById(R.id.EditTextMovieName);
        tvMovieName = (TextView) findViewById(R.id.TextViewMovieName);
        mTextViewTime = (TextView) findViewById(R.id.TextViewTime);

        mTextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        FilmForm.this,
                        android.R.style.Theme_Holo_Light_Panel,
                        mDateSetListener,
                        day,month,year);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
            }
        });

        mTextViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FilmForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mTextViewTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Choisissez l'heure");
                mTimePicker.show();


            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                Log.d("FilmForm", "onDateSet: date: " + month + "/"  + dayOfMonth + "/" + year);

                String date = dayOfMonth+"/"+month+"/"+year;
                mTextViewDate.setText(date);
            }
        };

        bSavedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

