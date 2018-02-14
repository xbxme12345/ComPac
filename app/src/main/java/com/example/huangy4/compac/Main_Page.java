package com.example.huangy4.compac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);

        Button newList = (Button)findViewById(R.id.new_btn);
        Button existingList = (Button)findViewById(R.id.existing_btn);

        newList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Main_Page.this, New_List.class);
                startActivity(intent);
            }
        });

        existingList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Main_Page.this, Existing_List.class);
                startActivity(intent);
            }
        });
    }
}
