package com.example.huangy4.compac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main_Page extends AppCompatActivity {
    private static final String TAG = "ComPac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering Main_Page");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Button newList = findViewById(R.id.new_list_button);
        newList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.v(TAG, "New List Button clicked");
                Intent intent = new Intent(Main_Page.this, New_List_Page.class);

                startActivity(intent);
            }
        });

        Button existingList = findViewById(R.id.existing_list_button);
        existingList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.v(TAG, "Existing List Button clicked");
                Intent intent = new Intent(Main_Page.this, Existing_List_Page.class);

                startActivity(intent);
            }
        });
    }
}
