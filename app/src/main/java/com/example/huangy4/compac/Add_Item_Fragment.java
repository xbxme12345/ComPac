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
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Add_Item_Fragment extends Fragment {

    private ArrayList<String> items;
    private ArrayList<String> quantities;
    static String quantity1;
    static String nameofItem2;
    FirebaseDatabase database;
    DatabaseReference myRef;
    static String tablename;
    private FirebaseAuth mAuth;
    private static final String TAG = "ComPac";

    private EditText mquantity;
    private EditText mnameofItem;
    private String UID;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        nameofItem2 = "";
        quantity1 = "";

        Log.v(TAG, "Entering Add_Item_Fragment clicked");
        mAuth = FirebaseAuth.getInstance();
        mquantity = (EditText) container.findViewById(R.id.item_quantity_input);mnameofItem = (EditText) container.findViewById(R.id.item_name_input);


         UID = mAuth.getCurrentUser().getUid();

        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);

        //nameofItem = findViewById(R.id.item_name_input);
        //mquantity = findViewById(R.id.item_quantity_input);


        if((nameofItem2.equalsIgnoreCase("")!= true) && (quantity1.equalsIgnoreCase("")!= true))
        {
            mquantity.setText(quantity1);
            mnameofItem.setText(nameofItem2);
        }


        final ImageButton myBtn = rootView.findViewById(R.id.confirm_add_button);
        myBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                nameofItem2 = mnameofItem.getText().toString();
                quantity1 = mquantity.getText().toString();

                myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("PackingList").child(tablename).child("List");
                myRef.child(nameofItem2).setValue(quantity1);
                closefragment();

//
            }
        });

        return rootView;
    }

    public void closfragment()
    {
        //closefragment();
    }
    public static void setTablename(String tableName){
        tablename = tableName;
    }
    public static void setQuantity1(String x){
        quantity1 = x;
    }
    public static void setNameofItem2(String y){
        nameofItem2 = y;
    }
    private void closefragment() {
       // getActivity().getFragmentManager().beginTransaction().remove(Add_Item_Fragment.this).commit();
    }


}
