package ru.aklem.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GridViewFragment extends Fragment {
    public static final String DATE = "date";

    public GridViewFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayList<Date> daysArray = new ArrayList<>();
        final GridView monthGrid = view.findViewById(R.id.month_grid_view);

        // Receive position of current page
        Bundle args = getArguments();
        long date = args.getLong(DATE);

        // Assign array of days to pass to grid adapter
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);

        // Initialize calendar
        long monthToPass = cal.getTimeInMillis();
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        // Roll calendar to the first monday of grid
        cal.set(Calendar.WEEK_OF_MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Fill an array with days
        int gridCalls = 42;
        while (daysArray.size() < gridCalls) {
            daysArray.add(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        monthGrid.setAdapter(new MonthGridAdapter(requireContext(), R.layout.day_cell, R.id.day_cell_text_view, daysArray, monthToPass));
    }
}
