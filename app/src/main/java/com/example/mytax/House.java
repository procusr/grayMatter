package com.example.mytax;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class House extends AppCompatActivity {


    int buy;
    int sell;
    int broker;
    int list;
    int profit;

    EditText buyPrice;
    EditText sellPrice;
    EditText brokerPrice;
    EditText listPrice;
    EditText profitValue;

    Button tbdButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        buyPrice = (EditText) findViewById(R.id.bought);
        sellPrice = (EditText) findViewById(R.id.sold);
        brokerPrice = (EditText) findViewById(R.id.broker);
        listPrice  =  (EditText) findViewById(R.id.listing);
        profitValue = (EditText) findViewById(R.id.profit);

        tbdButton = (Button) findViewById(R.id.Btn_Submit);
        tbdButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                buy = Integer.valueOf(buyPrice.getText().toString());
                sell = Integer.valueOf(sellPrice.getText().toString());
                broker = Integer.valueOf(brokerPrice.getText().toString());
                list = Integer.valueOf(listPrice.getText().toString());
                profit = Integer.valueOf(profitValue.getText().toString());

                //showToast(String.valueOf(buy));
                //showToast(String.valueOf(sell));
                //showToast(String.valueOf(broker));
                //showToast(String.valueOf(list));
                showToast(String.valueOf(profit));

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }
    private void showToast(String text)
    {
        Toast.makeText(House.this, text, Toast.LENGTH_SHORT).show();
    }


}
