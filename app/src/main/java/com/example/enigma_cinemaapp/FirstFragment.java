package com.example.enigma_cinemaapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.enigma_cinemaapp.data.MovieDbHelper;
import com.example.enigma_cinemaapp.data.MovieEntry;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_MovieFragment);
            }
        });


        MovieDbHelper dbHelper = new MovieDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                MovieEntry.COLUMN_TITLE,
                MovieEntry.COLUMN_DATE,
                MovieEntry.COLUMN_HEURE,
                MovieEntry.COLUMN_SCENARIO,
                MovieEntry.COLUMN_REALISATION,
                MovieEntry.COLUMN_MUSIQUE,
                MovieEntry.COLUMN_DESCRIPTION
        };

        String sortOrder =
                MovieEntry.COLUMN_DATE + " DESC";

        Cursor cursor = db.query(MovieEntry.TABLE_NAME, projection, null, null,null, null, sortOrder);

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(MovieEntry._ID));
            itemIds.add(itemId);
        }
        cursor.close();

    }
}