package com.example.mytax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main extends AppCompatActivity {
     private EditText mCompanyName ;
     private EditText mExpectedTax;
     private EditText mActualTax;
     private EditText mSalary;
     private Button mSave;

     private static final String REQUIRED= "Required input";
     private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner myCommuneList =  findViewById(R.id.dd_commune_list);
        mCompanyName =findViewById(R.id.PT_Company_name);
        mExpectedTax = findViewById(R.id.PT_Salary);
        mActualTax = findViewById(R.id.TV_Salary_afterTax);
        mSalary = findViewById(R.id.TV_tax);
        mSave = findViewById(R.id.B_Save_db);
        //mDatabase= FirebaseDatabase.getInstance().getReference().child("company");
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
       // submitRecord();
    }
        //


   /* public void updateData(){

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
    }*/

        private void submitRecord () {
            final EditText mCompanyName = findViewById(R.id.PT_Company_name);
            final EditText mSalary = findViewById(R.id.PT_Salary);
            final EditText mExpectedTax = findViewById(R.id.TV_tax);
            final EditText mActualTax = findViewById(R.id.TV_Salary_afterTax);


            final String companyName = mCompanyName.getText().toString().trim();
            final String salary = mSalary.getText().toString().trim();
            final String expectedTax = mExpectedTax.getText().toString().trim();
            final String actualTax = mActualTax.getText().toString().trim();


            if (TextUtils.isEmpty(companyName)) {
                mCompanyName.setError(REQUIRED);
                return;
            }

            if (TextUtils.isEmpty(salary)) {
                mSalary.setError(REQUIRED);
                return;
            }

            if (TextUtils.isEmpty(expectedTax)) {
                mExpectedTax.setError(REQUIRED);
                return;
            }

            if (TextUtils.isEmpty(actualTax)) {
                mActualTax.setError(REQUIRED);
                return;
            }

            Company company = new Company(companyName, Integer.parseInt(salary), Integer.parseInt(expectedTax), Integer.parseInt(actualTax));
            mDatabase.child(company.getCompanyName()).setValue(company);
            Toast.makeText(getApplicationContext(), "Record added", Toast.LENGTH_SHORT).show();
        }

    }




