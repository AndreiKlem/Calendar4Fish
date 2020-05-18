package ru.aklem.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthGridAdapter extends ArrayAdapter<Date> {

    private static final String TAG = "MonthGridAdapter, ";
    private long mCurrentMonth;
    Calendar cal = Calendar.getInstance();
    Calendar now = Calendar.getInstance();
    LayoutInflater inflater;

    public MonthGridAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<Date> days, long date) {
        super(context, resource, textViewResourceId, days);
        mCurrentMonth = date;
        cal.setTimeInMillis(mCurrentMonth);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.day_cell, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = view.findViewById(R.id.day_cell_text_view);
        Date date = getItem(position);
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(date);

        int day = currentDay.get(Calendar.DAY_OF_MONTH);
        int month = currentDay.get(Calendar.MONTH);
        int year = currentDay.get(Calendar.YEAR);
        if(cal.get(Calendar.MONTH) != month || cal.get(Calendar.YEAR) != year) {
            view.setVisibility(View.GONE);
        }
        else if(now.get(Calendar.DAY_OF_MONTH) == day && now.get(Calendar.MONTH) == month && now.get(Calendar.YEAR) == year) {
            textView.setTextColor(Color.BLUE);
        }
        textView.setText(String.valueOf(currentDay.get(Calendar.DATE)));
        return view;
    }
}
