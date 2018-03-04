package com.example.huangy4.compac;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Pick_Date_Fragment extends Fragment {
    private static final String TAG = "ComPac";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.v(TAG, "Entering Date_Pick_Fragment clicked");

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pick_date, container, false);

        ImageButton myBtn = rootView.findViewById(R.id.exit_pick_date_fragment);
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                Fragment fragment2 = new MyFragment2();
//                transaction.replace(R.id.container, fragment2);
//                transaction.commit();
            }
        });

        return rootView;
    }


}
