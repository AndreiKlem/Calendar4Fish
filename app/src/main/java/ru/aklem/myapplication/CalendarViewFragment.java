package ru.aklem.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarViewFragment extends Fragment {

    private static final String TAG = "";
    MonthCollectionAdapter monthCollectionAdapter;
    ViewPager2 viewPager;
    Calendar mCurrentMonth = Calendar.getInstance();
    Calendar mMonth = Calendar.getInstance();
    CalendarViewModel calendarViewModel;
    TextView monthYearText;
    int currentPosition = 2;

    public CalendarViewFragment() {
        // empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMonth.getTime();
        mMonth.add(Calendar.MONTH, - currentPosition);
        monthCollectionAdapter = new MonthCollectionAdapter(this);
        viewPager = view.findViewById(R.id.month_view_pager);

        monthYearText = view.findViewById(R.id.month_year_text_view);
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        calendarViewModel.passCurrentMonth(mMonth);
        calendarViewModel.InitArray();

        // Set current date in the text view
        monthYearText.setText(getMonthYear(mCurrentMonth.getTimeInMillis()));

        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setAdapter(monthCollectionAdapter);
        viewPager.setCurrentItem(currentPosition, false);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position < currentPosition) {
                    mCurrentMonth.add(Calendar.MONTH, -1);
                    currentPosition = position;
                    if (position < 2) {
                        calendarViewModel.addBefore();
                        monthCollectionAdapter.notifyItemInserted(0);
                    }
                } else if (position > currentPosition) {
                    mCurrentMonth.add(Calendar.MONTH, 1);
                    currentPosition = position;
                }
                monthYearText.setText(getMonthYear(mCurrentMonth.getTimeInMillis()));
            }
        });

    }

    private String getMonthYear(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        return formatter.format(date);
    }


    class MonthCollectionAdapter extends FragmentStateAdapter {

        MonthCollectionAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            Fragment fragment = new GridViewFragment();
            Bundle args = new Bundle();
            long month = getItemId(position);
            args.putLong(GridViewFragment.DATE, month);

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return calendarViewModel.size();
        }

        @Override
        public long getItemId(int position) {
            return calendarViewModel.itemId(position);
        }

        @Override
        public boolean containsItem(long itemId) {
            return calendarViewModel.contains(itemId);
        }
    }
}