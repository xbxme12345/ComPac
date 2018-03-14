package com.example.huangy4.compac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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


        //Start Date
        TextView start_date_btn = findViewById(R.id.start_date_text);
        final FragmentManager fm = getSupportFragmentManager();

        start_date_btn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View arg0) {
                Pick_Date_Fragment dateFrag = new Pick_Date_Fragment();
                dateFrag.show(fm, "date picker");
            }
        });

        //End Date
        TextView end_date_btn = findViewById(R.id.end_date_text);
        end_date_btn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View arg0) {
                Pick_Date_Fragment dateFrag = new Pick_Date_Fragment();
                dateFrag.show(fm, "date picker");
            }
        });

        //Reminder Switch
        Switch s1 = findViewById(R.id.reminder_switch);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    findViewById(R.id.reminder_date_pair).setVisibility(View.VISIBLE);
                    findViewById(R.id.reminder_time_pair).setVisibility(View.VISIBLE);
                }
                else {
                    findViewById(R.id.reminder_date_pair).setVisibility(View.GONE);
                    findViewById(R.id.reminder_time_pair).setVisibility(View.GONE);
                }
            }
        });

        //Reminder Date
        TextView reminder_date_btn = findViewById(R.id.reminder_date_text);
        reminder_date_btn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View arg0) {
                Pick_Date_Fragment dateFrag = new Pick_Date_Fragment();
                dateFrag.show(fm, "date picker");
            }
        });

        //Reminder Time
        TextView reminder_time_btn = findViewById(R.id.reminder_time_text);
        reminder_time_btn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View arg0) {
                Pick_Time_Fragment dateFrag = new Pick_Time_Fragment();
                dateFrag.show(fm, "time picker");
            }
        });


        //Gender
        ImageButton male_btn = findViewById(R.id.male);
        male_btn.setOnClickListener( new ImageButton.OnClickListener() {
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {
                    button.setSelected(true);
                }
                else {
                    button.setSelected(false);
                }
            }
        });

        ImageButton female_btn = findViewById(R.id.female);
        female_btn.setOnClickListener( new ImageButton.OnClickListener() {
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {
                    button.setSelected(true);
                }
                else {
                    button.setSelected(false);
                }
            }
        });

        gender = "Male";
        mAuth = FirebaseAuth.getInstance();

        //mAuth.addAuthStateListener(authStateListener);

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
                TextView start_date_textV = findViewById(R.id.start_date_text);
                String start_date = start_date_textV.getText().toString();

                //end_date_input
                TextView end_date_textV = findViewById(R.id.end_date_text);
                String end_date = end_date_textV.getText().toString();

                //reminder
                Switch reminder_textV = findViewById(R.id.reminder_switch);
                Boolean reminder = false;



                String TableName = (destination+""+start_date+" " +end_date);

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
       DialogFragment newFrag = new Pick_Date_Fragment();
       newFrag.show(getSupportFragmentManager(), "Date");
    }

    public void timePicker(View v) {
        DialogFragment newFrag = new Pick_Time_Fragment();
        newFrag.show(getSupportFragmentManager(), "time");
    }

    public void setGenderMale(View view) {
        gender = "Male";
    }

    public void setGenderFemale(View view) {
        gender = "Female";
    }
}
