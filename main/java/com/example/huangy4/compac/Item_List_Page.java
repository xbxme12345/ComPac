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
import android.widget.ArrayAdapter;
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
    private String end_date;
    private String start_date;
    private String destination;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private ListView listview;
    private ListView listview2;

    private ArrayList<String> items;
    private ArrayList<String> quantities;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering Item_List_Page");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);

        Bundle bundle = this.getIntent().getExtras();
        String tableName = bundle.getString("tableName");

        String UID = mAuth.getCurrentUser().getUid();

        listview = findViewById(R.id.newListView);
        listview2 = findViewById(R.id.newListView2);
        myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("PackingList").child(tableName).child("List");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                items = new ArrayList<>();
                quantities = new ArrayList<>();
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while(iterator.hasNext())
                {
                    try
                    {
                        DataSnapshot s = iterator.next();
                        String namevalue = s.getKey().toString();
                        String quantityvalue = s.getValue().toString();
                        items.add(namevalue);
                        quantities.add(quantityvalue);
                    }
                    catch (DatabaseException e)
                    {
                        Log.v(TAG, "preferences=" + e);

                    }
                }

                ArrayAdapter<String> arrayAdapter;
                arrayAdapter = new ArrayAdapter<String>(Item_List_Page.this, android.R.layout.simple_list_item_1, items);
                listview.setAdapter(arrayAdapter);

                ArrayAdapter<String> arrayAdapter2;
                arrayAdapter2 = new ArrayAdapter<String>(Item_List_Page.this, android.R.layout.simple_list_item_1, quantities);
                listview2.setAdapter(arrayAdapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









        TextView description_textV = findViewById(R.id.description_value);
        description_textV.setText(destination);

        TextView start_date_textV = findViewById(R.id.start_date_value);
        start_date_textV.setText(start_date);

        TextView end_date_textV = findViewById(R.id.end_date_value);
        end_date_textV.setText(end_date);



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
                transaction.add(R.id.item_list_page, add_item_frag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}
