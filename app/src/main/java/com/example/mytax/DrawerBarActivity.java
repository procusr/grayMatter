package com.example.mytax;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class DrawerBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
       /* if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SalaryFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_salary);
        }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.nav_salary:

                Intent intent = new Intent(this, Salary.class);
                startActivity(intent);
                finish();

              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                      new SalaryFragment()).commit();
              break;
*/
            case R.id.nav_car:
                Intent intent = new Intent(this, AutoCarActivity.class);
                startActivity(intent);
                finish();
                break;

          /*  case R.id.nav_house:
                Intent intent = new Intent(this, House.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_percentage_calculator:
                gIntent intent = new Intent(this, PrecentageCalculator.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_setting:
                Intent intent = new Intent(this, Setting.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_summary:
                Intent intent = new Intent(this, Summary.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_share:
                Intent intent = new Intent(this, Share.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_send:
                Intent intent = new Intent(this Send.class);
                startActivity(intent);
                finish();*/

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            super.onBackPressed();
        }
    }
}

