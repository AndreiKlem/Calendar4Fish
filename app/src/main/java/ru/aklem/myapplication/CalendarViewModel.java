package ru.aklem.myapplication;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.Calendar;

public class CalendarViewModel extends ViewModel {

    private static final String TAG = "CalendarViewModel, ";
    private long mDate;
    private long mMontYearText;

    public long getMontYearText() {
        return mMontYearText;
    }

    public void setMontYearText(long month) {
        this.mMontYearText = month;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(month);
        Log.i(TAG, "setMontYearText, mMontYearText = " + cal.getTime());
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        this.mDate = date;
    }
}
