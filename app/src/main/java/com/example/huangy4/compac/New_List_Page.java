package com.example.huangy4.compac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class New_List_Page extends AppCompatActivity {
    private static final String TAG = "ComPac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Entering New_List_Page");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_page);

        ImageButton back_button = findViewById(R.id.new_list_page_back_button);
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
    }
}
