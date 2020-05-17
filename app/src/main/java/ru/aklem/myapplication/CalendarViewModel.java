package ru.aklem.myapplication;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class CalendarViewModel extends ViewModel {

    private static final String TAG = "debug";
    Calendar mCal = Calendar.getInstance();
    ArrayList<Long> mItems = new ArrayList<>();


    public long itemId(int position) {
        return mItems.get(position);
    }

    public boolean contains(long itemId) {
        return mItems.contains(itemId);
    }

    public int size() {
        return mItems.size();
    }

    public void passCurrentMonth(Calendar month) {
        mCal = (Calendar) month.clone();
    }

    public void InitArray() {
        for (int i = 0; i < 5; i++) {
            mItems.add(mCal.getTimeInMillis());
            mCal.add(Calendar.MONTH, 1);
        }
    }

    public void addBefore() {
        mCal.setTimeInMillis(mItems.get(0));
        mCal.add(Calendar.MONTH, -1);
        mItems.add(0, mCal.getTimeInMillis());
        Log.i(TAG, "addBefore: mItems: " + mItems);
    }
}
