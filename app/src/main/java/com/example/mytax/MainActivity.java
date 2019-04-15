package com.example.mytax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void df (View view){
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }
    public void test (View view){
        Intent intent = new Intent(MainActivity.this, House.class);
        startActivity(intent);
    }



}