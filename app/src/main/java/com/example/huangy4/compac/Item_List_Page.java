package com.example.huangy4.compac;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Item_List_Page extends AppCompatActivity {
    private static final String TAG = "ComPac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering Item_List_Page");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);

        ImageButton back_button = findViewById(R.id.item_list_page_back_button);
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
                transaction.replace(R.id.item_list_page, add_item_frag);
                transaction.commit();
            }
        });

    }
}
