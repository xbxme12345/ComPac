package com.example.huangy4.compac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Existing_List_Page extends AppCompatActivity {
    private static final String TAG = "ComPac";

    FirebaseDatabase database;
    DatabaseReference myRef;

    private ListView listview;
    private ArrayList<String> entries;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering Existing_List_Page");

        mAuth.addAuthStateListener(authStateListener);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_list_page);

        ImageButton back_button = findViewById(R.id.existing_list_page_back_button);
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.v(TAG, "main_button Button clicked");
                Intent intent = new Intent(Existing_List_Page.this, Main_Page.class);
                startActivity(intent);
            }
        });

        ImageButton newList = findViewById(R.id.new_list_button);
        newList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.v(TAG, "new_list_button clicked");
                Intent intent = new Intent(Existing_List_Page.this, New_List_Page.class);

                startActivity(intent);
            }
        });

        listview = findViewById(R.id.newListView);
        String UID = mAuth.getUid();
        myRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("PackingList");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                entries = new ArrayList<>();
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while(iterator.hasNext())
                {
                    try
                    {
                        String value = iterator.next().getKey().toString();
                        entries.add(value);
                    }
                    catch (DatabaseException e)
                    {
                        Log.v(TAG, "preferences=" + e);

                    }
                }
                ArrayAdapter<String> arrayAdapter;
                arrayAdapter = new ArrayAdapter<String>(Existing_List_Page.this, android.R.layout.simple_list_item_1, entries);
                listview.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
