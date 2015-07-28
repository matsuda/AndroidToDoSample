package com.example.matsuda.testtodo.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.matsuda.testtodo.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DateTimePickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DateTimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateTimePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = DateTimePickerFragment.class.getSimpleName();

    public interface DateTimeChangeDelegate {
        void onDateTimePickerDateTimeChanged(int year, int monthOfYear, int dayOfMonth);
    }
    private DateTimeChangeDelegate dateTimeChangeDelegate;
    public void setDateTimeChangeDelegate(DateTimeChangeDelegate delegate) {
        dateTimeChangeDelegate = delegate;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DateTimePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateTimePickerFragment newInstance() {
        DateTimePickerFragment fragment = new DateTimePickerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DateTimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, monthOfYear, dayOfMonth);
        dialog.setTitle(getString(R.string.task_date));
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // http://chirimo.info/?p=74
        if (view.isShown()) { return; }
        Log.d(TAG, "onDateSet");
        dateTimeChangeDelegate.onDateTimePickerDateTimeChanged(year, monthOfYear, dayOfMonth);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        // return inflater.inflate(R.layout.fragment_date_time_picker, container, false);
//        View view = inflater.inflate(R.layout.fragment_date_time_picker, container, false);
//        DatePicker datePicker = (DatePicker)view.findViewById(R.id.datePicker);
//        return view;
//    }

}
