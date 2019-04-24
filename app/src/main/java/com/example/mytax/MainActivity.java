package com.example.mytax;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout l1,l2;
    Button welcome;
    Animation uptodown,downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        welcome = findViewById(R.id.welcome);
//
//        setContentView(R.layout.welcome_activity);
//        l1 = findViewById(R.id.l1);
//        l2 = findViewById(R.id.l2);
//        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
//        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
//        l1.setAnimation(uptodown);
//        l2.setAnimation(downtoup);

//        welcome.setAnimation(downtoup);

    }
    public void setting (View view){
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void pop (View view){
        startActivity(new Intent(MainActivity.this, PopMain.class));
    }


    public void car(View view) {
        Intent intent = new Intent(MainActivity.this, Car.class);
    startActivity(intent);}

    public void house(View view) {
        Intent intent =new Intent(MainActivity.this, House.class);
        startActivity(intent);}

    public void summary(View view) {
        Intent intent = new Intent(MainActivity.this, Summary.class);
        startActivity(intent);}

    public void percentage(View view) {
        Intent intent = new Intent(MainActivity.this, Percentage.class);
        startActivity(intent);}

    public void salary(View view) {
        Intent intent = new Intent(MainActivity.this, Salary.class);
        startActivity(intent);}

    public void plus(View view) {
        Intent intent = new Intent(MainActivity.this, Plus.class);
        startActivity(intent);}

    public void main(View view)
    {
        Intent intent = new Intent(MainActivity.this , MainActivity.class);
        startActivity(intent);
    }
    public void login(View view)
    {
        Intent intent = new Intent(MainActivity.this , LoginActivity.class);
        startActivity(intent);
    }
}
