package com.example.huangy4.compac;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
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

import static com.example.huangy4.compac.Add_Item_Fragment.tablename;

public class Item_List_Page extends AppCompatActivity {
    private static final String TAG = "ComPac";
    //private String end_date;
    //private String start_date;
    //private String destination;

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference destination;
    DatabaseReference getDate;

    private ListView listview;
    private TextView titleView;
    private Switch mswitch;

    private TextView start_end_date_view;
    private String startDateVal;
    private String endDateVal;

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

        final String UID = mAuth.getCurrentUser().getUid();
        mswitch = findViewById(R.id.switch1);

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


                        if ( mswitch.isChecked())
                        {
                            String UID = mAuth.getCurrentUser().getUid().toString();
                            String stuff = (listview.getItemAtPosition(position)).toString();
                            String[] container = stuff.split(" ");
                            myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("PackingList")
                                    .child(tableName).child("List").child(container[0]);
                            myRef.removeValue();
                        }
                        else
                        {
                            String stuff = (listview.getItemAtPosition(position)).toString();
                            String[] container = stuff.split(" ");


                            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Item_List_Page.this);
                            final View mView = getLayoutInflater().inflate(R.layout.fragment_add_item, null);

                            final EditText mnameofItem = mView.findViewById(R.id.item_name_input);
                            final EditText mquantity = mView.findViewById(R.id.item_quantity_input);

                            mnameofItem.setText(container[0]);
                            mquantity.setText(container[1]);


                            final ImageButton myBtn = mView.findViewById(R.id.confirm_add_button);
                            myBtn.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    String nameofItem2 = mnameofItem.getText().toString();
                                    String quantity1 = mquantity.getText().toString();
                                    String UID = mAuth.getCurrentUser().getUid().toString();
                                    myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("PackingList").child(tableName).child("List");
                                    myRef.child(nameofItem2).setValue(quantity1);


//
                                }
                            });

                            mBuilder.setView(mView);
                            AlertDialog dialog = mBuilder.create();
                            dialog.show();

                        }
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


        //gets start and end date and sets the value
        getDate = FirebaseDatabase.getInstance().getReference("Users").child(UID)
                .child("PackingList").child(tableName);
        getDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                startDateVal = dataSnapshot.child("StartDate").getValue().toString();
                endDateVal = dataSnapshot.child("EndDate").getValue().toString();

                start_end_date_view.setText(startDateVal + " to " + endDateVal);
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


                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Item_List_Page.this);
                final View mView = getLayoutInflater().inflate(R.layout.fragment_add_item, null);

                final EditText mnameofItem = mView.findViewById(R.id.item_name_input);
                final EditText mquantity = mView.findViewById(R.id.item_quantity_input);


                final ImageButton myBtn = mView.findViewById(R.id.confirm_add_button);
                myBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String nameofItem2 = mnameofItem.getText().toString();
                        String quantity1 = mquantity.getText().toString();
                        String UID = mAuth.getCurrentUser().getUid().toString();
                        //add code to make sure not blank!
                        myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("PackingList").child(tableName).child("List");
                        myRef.child(nameofItem2).setValue(quantity1);


//
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });

    }
}
