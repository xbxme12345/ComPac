package com.example.huangy4.compac;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class Pick_Date_Fragment extends DialogFragment {
    private static final String TAG = "ComPac";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Log.v(TAG, "Entering Pick_Date_Fragment");

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Log.v(TAG, "Entering onDateSet");

            TextView tv = getActivity().findViewById(getActivity().getCurrentFocus().getId());
            tv.setText(view.getMonth() + "-" + view.getDayOfMonth() + "-" + view.getYear());
        }
    };

}
