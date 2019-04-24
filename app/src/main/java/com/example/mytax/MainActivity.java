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

//    private TextView mTextMessage;
//
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
//        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.getMenu().getItem(0).setChecked(true);

       // mTextMessage = (TextView) findViewById(R.id.message);

    }




    public void setting (View view){
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void pop (View view){
        startActivity(new Intent(MainActivity.this, PopMain.class));
    }


    public void car(View view) {
        Intent intent = new Intent(MainActivity.this, ManualCarActivity.class);
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
