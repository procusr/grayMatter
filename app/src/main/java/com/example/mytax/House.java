package com.example.mytax;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView profitValue;

       Button tbdButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

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

                showToast(String.valueOf("Profit to declare \n" + profit));

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

