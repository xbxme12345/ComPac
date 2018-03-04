package com.example.huangy4.compac;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class New_List_Page extends AppCompatActivity {
    private static final String TAG = "ComPac";
    private String gender;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering New_List_Page");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_page);
        gender = "Male";

        mAuth.addAuthStateListener(authStateListener);

        ImageButton back_button = findViewById(R.id.exit_new_list_page);
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.v(TAG, "main_button clicked");

                Intent intent = new Intent(New_List_Page.this, Main_Page.class);

                Bundle bundle = new Bundle();

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        ImageButton add_new_list_button = findViewById(R.id.add_new_list_button);
        add_new_list_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.v(TAG, "main_button clicked");

                //destination
                TextView destination_textV = findViewById(R.id.destination_input);
                String destination = destination_textV.getText().toString();

                //gender


                //start_date_input
                TextView start_date_textV = findViewById(R.id.start_date_input);
                String start_date = start_date_textV.getText().toString();

                //end_date_input
                TextView end_date_textV = findViewById(R.id.end_date_input);
                String end_date = end_date_textV.getText().toString();

                //reminder
                Switch reminder_textV = findViewById(R.id.reminder_switch);
                Boolean reminder = false;



                String TableName = (destination+" "+start_date+" " +end_date);

                String UID = mAuth.getCurrentUser().getUid().toString();
                myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID);
                myRef.child("PackingList").child(TableName).child("Destination").setValue(destination);
                myRef.child("PackingList").child(TableName).child("Gender").setValue(gender);
                myRef.child("PackingList").child(TableName).child("StartDate").setValue(start_date);
                myRef.child("PackingList").child(TableName).child("EndDate").setValue(end_date);
                myRef.child("PackingList").child(TableName).child("Reminder").setValue(reminder.toString());

                Intent intent = new Intent(New_List_Page.this, Item_List_Page.class);
                Bundle bundle = new Bundle();
                bundle.putString("tableName", TableName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void datePicker(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        android.support.v4.app.Fragment pick_date_frag = new Pick_Date_Fragment();
        transaction.add(R.id.new_list_page, pick_date_frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setGenderMale(View view) {
        gender = "Male";
    }

    public void setGenderFemale(View view) {
        gender = "Female";
    }
}
