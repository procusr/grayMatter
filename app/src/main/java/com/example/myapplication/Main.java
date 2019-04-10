package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main extends AppCompatActivity {
     private String companyName = "Novare";
     private String expectedTax="30000";
     private String actualTax="2000";
     private String salary="1000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveData();
    }

    public void saveData(){

        final EditText mCompanyName=findViewById(R.id.PT_Company_name);
        final EditText mSalary=findViewById(R.id.PT_Salary);
        final EditText mExpectedTax=findViewById(R.id.TV_tax);
        final EditText mActualTax = findViewById(R.id.TV_Salary_afterTax);

        mCompanyName.setText(companyName);
        mCompanyName.setSelection(companyName.length());

        mSalary.setText(salary);
        mSalary.setSelection(salary.length());

        mExpectedTax.setText(expectedTax);
        mExpectedTax.setSelection(expectedTax.length());

        mActualTax.setText(actualTax);
        mActualTax.setSelection(actualTax.length());


        Button save = findViewById(R.id.B_Save_db);

       save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyName=mCompanyName.getText().toString().trim();
                salary=mSalary.getText().toString().trim();
                expectedTax= mExpectedTax.getText().toString().trim();
                actualTax = mActualTax.getText().toString().trim();
                Company company =new Company(companyName,Integer.parseInt(salary),Integer.parseInt(expectedTax),Integer.parseInt(actualTax));
                //TODO Database reference

            }
        });



    }
}

