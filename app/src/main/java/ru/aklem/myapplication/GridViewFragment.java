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
    private static final String TAG = "debug, GridViewFragment, ";
    private final int GRID_CELLS = 42;

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
        GridView monthGrid = view.findViewById(R.id.month_grid_view);

        // Receive position of current page
        Bundle args = getArguments();
        long date = args.getLong(DATE);

        // Assign array of days to pass to grid adapter
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);

        // Revolving month to the first day
        long monthToPass = cal.getTimeInMillis();
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Log.i(TAG, "onViewCreated: cal: " + cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        while (daysArray.size() < GRID_CELLS) {
            daysArray.add(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        monthGrid.setAdapter(new MonthGridAdapter(requireContext(), R.layout.day_cell, R.id.day_cell_text_view, daysArray, monthToPass));
    }
}
