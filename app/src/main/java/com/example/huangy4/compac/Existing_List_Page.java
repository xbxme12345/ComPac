package com.example.huangy4.compac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Existing_List_Page extends AppCompatActivity {
    private static final String TAG = "ComPac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering Existing_List_Page");

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

        Button item_list = findViewById(R.id.fake_item_list_button);
        item_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.v(TAG, "fake_item_list_button clicked");
                Intent intent = new Intent(Existing_List_Page.this, Item_List_Page.class);

                startActivity(intent);
            }
        });
    }
}
