package ru.aklem.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MonthGridAdapter extends ArrayAdapter<Date> {

    private Calendar cal = Calendar.getInstance();
    private Calendar now = Calendar.getInstance();
    private LayoutInflater inflater;

    MonthGridAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<Date> days, long date) {
        super(context, resource, textViewResourceId, days);
        cal.setTimeInMillis(date);
        inflater = LayoutInflater.from(context);
    }

    public static class ViewHolder {
        TextView textView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.day_cell, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.day_cell_text_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Date date = getItem(position);
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(date);

        int day = currentDay.get(Calendar.DAY_OF_MONTH);
        int month = currentDay.get(Calendar.MONTH);
        int year = currentDay.get(Calendar.YEAR);
        if(cal.get(Calendar.MONTH) != month || cal.get(Calendar.YEAR) != year) {
            convertView.setVisibility(View.GONE);
        }
        else if(now.get(Calendar.DAY_OF_MONTH) == day && now.get(Calendar.MONTH) == month && now.get(Calendar.YEAR) == year) {
            holder.textView.setTextColor(Color.BLUE);
        }
        holder.textView.setText(String.valueOf(currentDay.get(Calendar.DATE)));
        return convertView;
    }
}
