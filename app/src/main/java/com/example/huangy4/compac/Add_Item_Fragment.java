package com.example.huangy4.compac;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Add_Item_Fragment extends Fragment {

    private ArrayList<String> items;
    private ArrayList<String> quantities;
    static String quantity;
    static String nameofItem;
    FirebaseDatabase database;
    DatabaseReference myRef;
    static String tablename;
    private FirebaseAuth mAuth;
    private static final String TAG = "ComPac";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.v(TAG, "Entering Add_Item_Fragment clicked");

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);

        ImageButton myBtn = rootView.findViewById(R.id.exit_add_item_fragment);
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                Fragment fragment2 = new MyFragment2();
//                transaction.remove(R.id.container, fragment2);
//                transaction.commit();
            }
        });

        return rootView;
    }
    public static void setTablename(String myEmp)
    {
        tablename = myEmp;
    }

    public static void setnameofItem(String myEmp)
    {
        nameofItem = myEmp;
    }

    public static void setquantity(String myEmp)
    {
        quantity = myEmp;
    }


}
