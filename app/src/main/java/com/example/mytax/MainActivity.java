package com.example.mytax;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    public void setting (View view){
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void pop (View view){
        startActivity(new Intent(MainActivity.this, PopMain.class));
    }


    public void Btn_car(View view) { Intent intent = new Intent(MainActivity.this, car.class);
    startActivity(intent);}

    public void house(View view) {Intent intent =new Intent(MainActivity.this, house.class);
        startActivity(intent);}

    public void summary(View view) {Intent intent = new Intent(MainActivity.this, summary.class);
        startActivity(intent);}

    public void percentage(View view) {Intent intent = new Intent(MainActivity.this, percentage.class);
        startActivity(intent);}

    public void salary(View view) {Intent intent = new Intent(MainActivity.this, Salary.class);
        startActivity(intent);}

    public void plus(View view) {Intent intent = new Intent(MainActivity.this, plus.class);
        startActivity(intent);}


}
