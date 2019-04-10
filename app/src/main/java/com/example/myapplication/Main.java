package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Main extends AppCompatActivity {
     private String companyName = "Novare";
     private String expectedTax="30000";
     private String actualTax="2000";
     private String salary="1000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner myCommuneList = (Spinner) findViewById(R.id.dd_commune_list);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1
                , getResources().getStringArray(R.array.Communes));
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        myCommuneList.setAdapter(myAdapter);



        myCommuneList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                        if (i == 1) {

                                                        } else if (i == 2) {

                                                        } else if (i == 3) {

                                                        } else if (i == 4) {
                                                        }
                                                    }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

