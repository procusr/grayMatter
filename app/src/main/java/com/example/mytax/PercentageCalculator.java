package com.example.mytax;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mytax.kommun_JSON.JSONDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PercentageCalculator extends AppCompatActivity {

    EditText gross, net;

    Button btn_calc, btn_kommunList;
    double taxAmount;
    double percentage;

    Spinner spinner;

    TextView textViewPercent;

    String jsonURL ="https://api.myjson.com/bins/u3gdk";





    public EditText getGross() {
        return gross;
    }

    public void setGross(EditText gross) {
        this.gross = gross;
    }


    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Button getBtn_calc() {
        return btn_calc;
    }

    public void setBtn_calc(Button btn_calc) {
        this.btn_calc = btn_calc;
    }

    public EditText getNet() {
        return net;
    }

    public void setNet(EditText net) {
        this.net = net;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_calculator);

        gross = (EditText)findViewById(R.id.et_grossSal);
        net = (EditText)findViewById(R.id.et_netSal);

        textViewPercent =(TextView) findViewById(R.id.textView_percent);

        spinner = (Spinner)findViewById(R.id.spinner);

        btn_kommunList = (Button)findViewById(R.id.btn_kommunSpinner);

        btn_kommunList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONDownloader(PercentageCalculator.this,jsonURL,spinner).execute();
            }
        });





        btn_calc = (Button)findViewById(R.id.btn_percentage);

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculatePercentage();
            }
        });

    }



    public void calculatePercentage() {

        Double grossSal = Double.parseDouble(gross.getText().toString());
        Double netSal = Double.parseDouble((net.getText().toString()));

        taxAmount = grossSal-netSal;

        percentage = (taxAmount/grossSal)*100;
        //go to json


        openDialog();
    }

    private void openDialog() {

        String percent = String.valueOf(percentage);
        AlertDialog.Builder builder = new AlertDialog.Builder(PercentageCalculator.this);
        builder.setTitle("Tax Percentage you pay").setMessage(percent + "%")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }


}




