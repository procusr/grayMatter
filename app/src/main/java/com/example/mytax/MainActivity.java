package com.example.mytax;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
public class MainActivity extends DrawerBarActivity {



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


    public void car(View view) {
        Intent intent = new Intent(MainActivity.this, CarMain.class);
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


}
