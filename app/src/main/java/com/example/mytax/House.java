package com.example.mytax;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.widget.FrameLayout;


public class House extends DrawerBarActivity {


    int buy;
    int sell;
    int broker;
    int list;
    int profit;

    EditText buyPrice;
    EditText sellPrice;
    EditText brokerPrice;
    EditText listPrice;
    TextView profitValue;

    Button tbdButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_house, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);


        buyPrice =  findViewById(R.id.bought);
        sellPrice =  findViewById(R.id.sold);
        brokerPrice =  findViewById(R.id.broker);
        profitValue =  findViewById(R.id.profit);
        listPrice = findViewById(R.id.listing);

        tbdButton = findViewById(R.id.Btn_Calculate);

        tbdButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buy = Integer.parseInt(buyPrice.getText().toString());
                sell = Integer.parseInt(sellPrice.getText().toString());
                broker = Integer.parseInt(brokerPrice.getText().toString());
                list = Integer.parseInt(listPrice.getText().toString());
                profit = (sell - (buy + broker + list));
                profitValue.setText(String.valueOf(profit));


            }
        });

   }
}
