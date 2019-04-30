package com.example.mytax;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

import java.util.Calendar;

public class MainActivity extends DrawerBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 25);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);


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
