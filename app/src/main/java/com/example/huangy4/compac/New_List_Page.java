package com.example.huangy4.compac;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import java.util.Calendar;

public class New_List_Page extends AppCompatActivity {
    private static final String TAG = "ComPac";
    private String gender = "None";

    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering New_List_Page");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_page);

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

        //Get gender. gender is set to "None" by default
        Button male_btn = findViewById(R.id.male);
        male_btn.setOnClickListener( new ImageButton.OnClickListener() {
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {
                    button.setSelected(true);
                    gender = "Male";
                }
                else {
                    button.setSelected(false);
                }
            }
        });

        //set gender to female is female button is clicked
        Button female_btn = findViewById(R.id.female);
        female_btn.setOnClickListener( new ImageButton.OnClickListener() {
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {
                    button.setSelected(true);
                    gender = "Female";
                }
                else {
                    button.setSelected(false);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

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

                //start_date_input
                TextView start_date_textV = findViewById(R.id.start_date_text);
                String start_date = start_date_textV.getText().toString();

                //end_date_input
                TextView end_date_textV = findViewById(R.id.end_date_text);
                String end_date = end_date_textV.getText().toString();

                //reminder
                Switch reminder_textV = findViewById(R.id.reminder_switch);
                Boolean reminder = reminder_textV.isChecked();

                TextView reminder_date_textV = findViewById(R.id.reminder_date_text);
                String reminder_date = reminder_date_textV.getText().toString();

                TextView reminder_time_textV = findViewById(R.id.reminder_time_text);
                String reminder_time = reminder_time_textV.getText().toString();

                if (destination.length() == 0 ||
                    start_date.length() == 0  ||
                    end_date.length() == 0   ) {

                    Log.v(TAG, "Invalid input");
                    AlertDialogUtil.showDialog(New_List_Page.this, "Invalid!", "Invalid! Make sure all fields are entered.");

                }
                else if (reminder && (reminder_date.length() == 0 || reminder_time.length() == 0) ) {
                    Log.v(TAG, "Invalid input");
                    AlertDialogUtil.showDialog(New_List_Page.this, "Invalid!", "Invalid! Make sure all fields are entered.");
                }
                else {
                    if (reminder) {
                        //Reminder Alert
                        Log.v(TAG, "creating alert");
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        Intent notificationIntent = new Intent(New_List_Page.this, ReminderReceiver.class);
                        PendingIntent broadcast = PendingIntent.getBroadcast(New_List_Page.this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        Calendar cal = Calendar.getInstance();

//                        String[] date = reminder_date.split("-");
//                        String[] time = reminder_time.split(":");
//
//                        cal.clear();
//                        cal.set(Calendar.MONTH, Integer.parseInt(date[0])-1);
//                        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[1]));
//                        cal.set(Calendar.YEAR, Integer.parseInt(date[2]));
//                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
//                        cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
//                        cal.set(Calendar.SECOND, 0);

                        cal.add(Calendar.MINUTE, 1);
                        alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(), broadcast);
                    }

                    Log.v(TAG, "Proceeding");

                    String TableName = (destination + "" + start_date + " " + end_date);

                    String UID = mAuth.getCurrentUser().getUid().toString();
                    myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID);
                    myRef.child("PackingList").child(TableName).child("Destination").setValue(destination);
                    myRef.child("PackingList").child(TableName).child("Gender").setValue(gender);
                    myRef.child("PackingList").child(TableName).child("StartDate").setValue(start_date);
                    myRef.child("PackingList").child(TableName).child("EndDate").setValue(end_date);
                    myRef.child("PackingList").child(TableName).child("Reminder").setValue(reminder.toString());
                    myRef.child("PackingList").child(TableName).child("ReminderDate").setValue(reminder_date);
                    myRef.child("PackingList").child(TableName).child("ReminderTime").setValue(reminder_time);

                    try {
                        itemGenerator(myRef, TableName, gender, start_date, end_date);
                    }
                    catch (java.text.ParseException e) {
                        e.printStackTrace();

                        Intent intent = new Intent(New_List_Page.this, Item_List_Page.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("tableName", TableName);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }

            }
        });
    }

    public void itemGenerator(DatabaseReference myRef, String TableName, String gender, String start_date, String end_date) throws ParseException {
        Log.v(TAG, "Generating items");

        long results = daysBetween(start_date, end_date);

        //general item list
        myRef.child("PackingList").child(TableName).child("List").child("Shirts").setValue(results);
        myRef.child("PackingList").child(TableName).child("List").child("Underwear").setValue(results);
        myRef.child("PackingList").child(TableName).child("List").child("Pants").setValue(results);
        myRef.child("PackingList").child(TableName).child("List").child("Jackets").setValue(2);
        myRef.child("PackingList").child(TableName).child("List").child("Socks").setValue(results);
        myRef.child("PackingList").child(TableName).child("List").child("Toothbrush").setValue(1);
        myRef.child("PackingList").child(TableName).child("List").child("Toothpaste").setValue(1);
        myRef.child("PackingList").child(TableName).child("List").child("Shoes").setValue(1);
        myRef.child("PackingList").child(TableName).child("List").child("Shower slippers").setValue(1);
        myRef.child("PackingList").child(TableName).child("List").child("Skincare").setValue(1);
        myRef.child("PackingList").child(TableName).child("List").child("Phone Charger").setValue(1);
        myRef.child("PackingList").child(TableName).child("List").child("Pajamas").setValue(1);

        if (gender.equals("Male")) {
            myRef.child("PackingList").child(TableName).child("List").child("Dress shoes").setValue(1);
            myRef.child("PackingList").child(TableName).child("List").child("Suit").setValue(1);
        }
        else if (gender.equals("Female")) {
            myRef.child("PackingList").child(TableName).child("List").child("Bras").setValue(results);
            myRef.child("PackingList").child(TableName).child("List").child("Dresses").setValue(results);
            myRef.child("PackingList").child(TableName).child("List").child("Makeup Bag").setValue(1);
        }

    }

    private static long daysBetween(String start, String end) {
        long result = 0;

        try {
            SimpleDateFormat format = new SimpleDateFormat("mm-dd-yyyy");
            Date one = format.parse(start);
            Date two = format.parse(end);

            long diffInMillies = Math.abs(one.getTime() - two.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            result = diff;
        }
        catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return result;
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

    public void setGenderNone(View view) {
        gender = "";
    }

}
