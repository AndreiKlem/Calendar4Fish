package ru.aklem.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainFragment extends Fragment implements View.OnClickListener {

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        FloatingActionButton createEventFab = view.findViewById(R.id.create_event_fab);
        createEventFab.setOnClickListener(this);

        insertCalendarFragment();
        insertEventRecyclerViewFragment();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.create_event_fab:
                navController.navigate(R.id.action_mainFragment_to_createNewEventFragment);
        }
    }

    private void insertCalendarFragment() {
        Fragment childFragment = new CalendarViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.calendar_frame, childFragment).commit();
    }

    private void insertEventRecyclerViewFragment() {
        Fragment childFragment = new EventRecyclerViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.event_frame, childFragment).commit();
    }
}
