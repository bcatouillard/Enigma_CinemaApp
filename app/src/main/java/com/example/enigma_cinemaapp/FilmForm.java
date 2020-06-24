package com.example.enigma_cinemaapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.enigma_cinemaapp.data.MovieDbHelper;
import com.example.enigma_cinemaapp.data.MovieEntry;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmForm extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FilmForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilmForm.
     */
    // TODO: Rename and change types and number of parameters
    public static FilmForm newInstance(String param1, String param2) {
        FilmForm fragment = new FilmForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    EditText etMovieName;
    TextView tvMovieName;

    TextView mTextViewDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    TextView mTextViewTime;
    TimePickerDialog.OnTimeSetListener mTimeSetListener;

    Spinner sScenario;
    Spinner sReal;
    Spinner sMusique;

    EditText edDescription;

    Button bSavedButton;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_film_form, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        etMovieName = (EditText) view.findViewById(R.id.EditTextMovieName);
        tvMovieName = (TextView) view.findViewById(R.id.TextViewMovieName);

        mTextViewDate = (TextView) view.findViewById(R.id.TextViewDate);
        mTextViewTime = (TextView) view.findViewById(R.id.TextViewTime);

        sScenario = (Spinner) view.findViewById(R.id.SpinnerScenario);
        sReal = (Spinner) view.findViewById(R.id.SpinnerReal);
        sMusique = (Spinner) view.findViewById(R.id.SpinnerMusique);

        edDescription = (EditText) view.findViewById(R.id.EditTextMovieDesc);

        bSavedButton = (Button) view.findViewById(R.id.ButtonSave);

        mTextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
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
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mTextViewTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Choisissez l'heure");
                mTimePicker.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;

                String date = dayOfMonth+"/"+month+"/"+year;
                mTextViewDate.setText(date);
            }
        };

        bSavedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieDescription = edDescription.getText().toString();
                String movieTitle = etMovieName.getText().toString();
                String date = mTextViewDate.getText().toString();
                String heure = mTextViewTime.getText().toString();
                String noteScenario = sScenario.getSelectedItem().toString();
                String noteReal = sReal.getSelectedItem().toString();
                String noteMusique = sMusique.getSelectedItem().toString();

                MovieDbHelper dbhelper = new MovieDbHelper(getContext());
                SQLiteDatabase db = dbhelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(MovieEntry.COLUMN_TITLE, movieTitle);
                values.put(MovieEntry.COLUMN_DATE, date);
                values.put(MovieEntry.COLUMN_HEURE, heure);
                values.put(MovieEntry.COLUMN_DESCRIPTION,movieDescription);
                values.put(MovieEntry.COLUMN_SCENARIO,noteScenario);
                values.put(MovieEntry.COLUMN_REALISATION,noteReal);
                values.put(MovieEntry.COLUMN_MUSIQUE,noteMusique);

                long newRowId = db.insert(MovieEntry.TABLE_NAME, null, values);

                getView().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavHostFragment.findNavController(FilmForm.this)
                                .navigate(R.id.action_MovieFilm_to_FirstFragment);
                    }
                });
            }
        });
    }
}