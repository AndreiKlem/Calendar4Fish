package ru.aklem.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GridViewFragment extends Fragment {
    static final String DATE = "date";
    private Drawable drawable;
    private MonthGridAdapter adapter;
    private int previousPosition = -1;
    private View previousView;

    public GridViewFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        drawable = ContextCompat.getDrawable(requireContext(), R.drawable.bg_cursor);

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
        adapter = new MonthGridAdapter(requireContext(), R.layout.day_cell, R.id.day_cell_text_view, daysArray, monthToPass);
        monthGrid.setAdapter(adapter);
        monthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (previousPosition != -1) {
                    previousView.setBackground(null);
                }
                previousView = view;
                view.setBackground(drawable);
                previousPosition = position;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
