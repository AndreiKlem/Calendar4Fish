package ru.aklem.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarViewFragment extends Fragment {

    private static final String TAG = "debug";
    private MonthCollectionAdapter monthCollectionAdapter;
    private ViewPager2 viewPager;
    private Calendar mCurrentMonth = Calendar.getInstance();
    private CalendarViewModel calendarViewModel;
    private TextView monthYearText;
    private int currentPosition = 2;


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


        
        viewPager = view.findViewById(R.id.month_view_pager);
        monthYearText = view.findViewById(R.id.month_year_text_view);

        monthCollectionAdapter = new MonthCollectionAdapter(this);
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);

        mCurrentMonth.getTime();

        // Set current date in the text view
        monthYearText.setText(getMonthYear(mCurrentMonth.getTimeInMillis()));

        mCurrentMonth.add(Calendar.MONTH, - currentPosition);
        calendarViewModel.passPreviousMonth(mCurrentMonth);
        calendarViewModel.InitArray();
        mCurrentMonth.add(Calendar.MONTH, currentPosition);

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
                        currentPosition ++;
                    }
                } else if (position > currentPosition) {
                    mCurrentMonth.add(Calendar.MONTH, 1);
                    currentPosition = position;
                    if (position > calendarViewModel.size() - 2) {
                        calendarViewModel.addAfter();
                        monthCollectionAdapter.notifyItemInserted(calendarViewModel.size());
                    }
                }
                monthYearText.setText(getMonthYear(mCurrentMonth.getTimeInMillis()));
            }
        });
    }

    private String getMonthYear(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    /**
     * Adapter for the calendar view
     */
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