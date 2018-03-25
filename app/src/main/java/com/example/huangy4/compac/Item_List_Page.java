package com.example.huangy4.compac;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Item_List_Page extends AppCompatActivity {
    private static final String TAG = "ComPac";
    //private String end_date;
    //private String start_date;
    //private String destination;

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference destination;
    DatabaseReference startDate;
    DatabaseReference endDate;

    private ListView listview;
    private TextView titleView;

    private TextView start_end_date_view;
    private String startDateVal = "";

    private ArrayList<String> items;
    private ArrayList<String> quantities;

    private EditText quantity;
    private EditText nameofItem;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering Item_List_Page");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);

        Bundle bundle = this.getIntent().getExtras();
        final String tableName = bundle.getString("tableName");

        mAuth = FirebaseAuth.getInstance();

        String UID = mAuth.getCurrentUser().getUid();

        listview = findViewById(R.id.newListView);
        titleView = findViewById(R.id.description_value);
        start_end_date_view = findViewById(R.id.start_end_date_value);

        myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("PackingList").child(tableName).child("List");
        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                items = new ArrayList<>();
                // quantities = new ArrayList<>();
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while(iterator.hasNext())
                {
                    try
                    {
                        DataSnapshot s = iterator.next();
                        String namevalue = s.getKey().toString();
                        String quantityvalue = s.getValue().toString();
                        items.add(namevalue + " " + quantityvalue);
                        //quantities.add(quantityvalue);
                    }
                    catch (DatabaseException e)
                    {
                        Log.v(TAG, "preferences=" + e);

                    }
                }

                ArrayAdapter<String> arrayAdapter;
                arrayAdapter = new ArrayAdapter<String>(Item_List_Page.this, android.R.layout.simple_list_item_1, items);
                listview.setAdapter(arrayAdapter);

                //display items
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        String UID = mAuth.getCurrentUser().getUid().toString();
                        String stuff = (listview.getItemAtPosition(position)).toString();
                        String [] container = stuff.split(" ");
                        myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("PackingList").child(tableName).child("List").child(container[0]);
                        myRef.removeValue();

                        /* I dont think we need this to display items
                        String stuff = (listview.getItemAtPosition(position)).toString();
                        String [] container = stuff.split(" ");
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        Fragment add_item_frag = new Add_Item_Fragment();
                        Add_Item_Fragment.setTablename(tableName);
                        Add_Item_Fragment.setNameofItem2(container[0]);
                        Add_Item_Fragment.setQuantity1(container[1]);
                        transaction.add(R.id.item_list_page, add_item_frag);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        */
                    }
                });

                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //displays the name of the trip on top of the page
        destination = FirebaseDatabase.getInstance().getReference("Users").child(UID)
                .child("PackingList").child(tableName).child("Destination");
        destination.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                titleView.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //gets start and end date for the trip
        startDate = FirebaseDatabase.getInstance().getReference("Users").child(UID)
                .child("PackingList").child(tableName).child("StartDate");
        endDate = FirebaseDatabase.getInstance().getReference("Users").child(UID)
                .child("PackingList").child(tableName).child("EndDate");
        endDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                startDateVal = dataSnapshot.getValue().toString();
                start_end_date_view.setText(startDateVal + " to " + dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ImageButton back_button = findViewById(R.id.exit_item_list_page);
        back_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Log.v(TAG, "Main_Page button clicked");
                Intent intent = new Intent(Item_List_Page.this, Existing_List_Page.class);
                startActivity(intent);
            }
        });

        ImageButton add_item = findViewById(R.id.add_item_button);
        add_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.v(TAG, "add_item_button button clicked");

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                Fragment add_item_frag = new Add_Item_Fragment();
                Add_Item_Fragment.setTablename(tableName);
                transaction.add(R.id.item_list_page, add_item_frag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}
