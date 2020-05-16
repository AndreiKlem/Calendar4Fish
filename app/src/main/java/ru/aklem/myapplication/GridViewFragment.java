package ru.aklem.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GridViewFragment extends Fragment {
    public static final String POSITION = "position";
    private final int GRID_CELLS = 42;
    CalendarViewModel calendarViewModel;


    public GridViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Receive current date from parent fragment
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        long currentMonth = calendarViewModel.getDate();

        // Receive position of current page
        Bundle args = getArguments();
        int position = args.getInt(POSITION);


        // Assign array of days to pass to grid adapter
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentMonth);

        // Offsetting month on swipe
        cal.add(Calendar.MONTH, position);

        // Revolving month to the first day
        cal.set(Calendar.DAY_OF_MONTH, 1);
        long monthToPass = cal.getTimeInMillis();

        int dayOffset = cal.get(Calendar.DAY_OF_WEEK) -2;
        cal.add(Calendar.DAY_OF_MONTH, -dayOffset);
        ArrayList<Date> daysArray = new ArrayList<>();
        MonthGridAdapter adapter;
        for (int i = 0; i < GRID_CELLS; i++) {
            daysArray.add(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        GridView monthGrid = view.findViewById(R.id.month_grid_view);
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(currentMonth);
        adapter = new MonthGridAdapter(requireContext(), R.layout.day_cell, R.id.day_cell_text_view, daysArray, monthToPass);
        monthGrid.setAdapter(adapter);
    }
}
