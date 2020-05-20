package ru.aklem.myapplication;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarViewModel extends ViewModel {

    private Calendar mCal = Calendar.getInstance();
    private ArrayList<Long> mItems = new ArrayList<>();


    public long itemId(int position) {
        return mItems.get(position);
    }

    public boolean contains(long itemId) {
        return mItems.contains(itemId);
    }

    public int size() {
        return mItems.size();
    }

    public void passPreviousMonth(Calendar month) {
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
    }

    public void addAfter() {
        mCal.setTimeInMillis(mItems.get(mItems.size() - 1));
        mCal.add(Calendar.MONTH, 1);
        mItems.add(mItems.size(), mCal.getTimeInMillis());
    }
}
