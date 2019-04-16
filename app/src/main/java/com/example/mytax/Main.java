package com.example.mytax;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Main extends AppCompatActivity {
     private RecyclerView recyclerView;
     private LinearLayoutManager linearLayoutManager;
     private FirebaseRecyclerAdapter adapter;
     private String companyName;
     private String salary;
     private String expectedTax;
     private String actualTax;
     private static final String REQUIRED= "Required input";
     private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_list);
        recyclerView = findViewById(R.id.list);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("salary");
        mDatabase.keepSynced(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRecord();
            }
        });
    }

    public void updateData(){

        AlertDialog.Builder myDialog=new AlertDialog.Builder(this);
        LayoutInflater inflater= LayoutInflater.from(getApplicationContext());
        View myView=inflater.inflate(R.layout.activity_update,null);
        myDialog.setView(myView);

        final AlertDialog dialog=myDialog.create();

        final EditText mCompanyName=myView.findViewById(R.id.company_name);
        final EditText mSalary=myView.findViewById(R.id.salary);
        final EditText mExpectedTax=myView.findViewById(R.id.expected_tax);
        final EditText mActualTax = myView.findViewById(R.id.actual_tax);

        mCompanyName.setText(companyName);
        //mCompanyName.setSelection(companyName.length());

        mSalary.setText(salary);
        //mSalary.setSelection(salary.length());

        mExpectedTax.setText(expectedTax);
        //mExpectedTax.setSelection(expectedTax.length());

        mActualTax.setText(actualTax);
        //mActualTax.setSelection(actualTax.length());



        Button btnDelete=myView.findViewById(R.id.btnDelete);
        Button btnUpdate=myView.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                companyName=mCompanyName.getText().toString().trim();
                salary=mSalary.getText().toString().trim();
                expectedTax=mExpectedTax.getText().toString().trim();
                actualTax=mActualTax.getText().toString().trim();

                Company company = new Company(companyName, salary, expectedTax, actualTax);
                mDatabase.child(company.getCompanyName()).setValue(company);

                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(companyName).removeValue();
                dialog.dismiss();
            }
        });

          dialog.show();
    }

    private void submitRecord() {

        AlertDialog.Builder myDialog=new AlertDialog.Builder(this);
        LayoutInflater inflater= LayoutInflater.from(getApplicationContext());
        View myView=inflater.inflate(R.layout.activity_add,null);
        myDialog.setView(myView);
        final AlertDialog dialog=myDialog.create();

        dialog.setCancelable(false);

        final EditText companyName = myView.findViewById(R.id.company_name);
        final EditText salary = myView.findViewById(R.id.salary);
        final EditText expectedTax =myView.findViewById(R.id.expected_tax);
        final EditText actualTax = myView.findViewById(R.id.actual_tax);

        Button btnCancel=myView.findViewById(R.id.btnCancel);
        Button btnAdd=myView.findViewById(R.id.btnSave);


    btnAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mCompanyName = companyName.getText().toString().trim();
            String mSalary = salary.getText().toString().trim();
            String mExpectedTax = expectedTax.getText().toString().trim();
            String mActualTax = actualTax.getText().toString().trim();

            if (TextUtils.isEmpty(mCompanyName)) {
                companyName.setError(REQUIRED);
                return;
            }
            if (TextUtils.isEmpty(mSalary)) {
                salary.setError(REQUIRED);
                return;
            }
            if (TextUtils.isEmpty(mExpectedTax)) {
                expectedTax.setError(REQUIRED);
                return;
            }
            if (TextUtils.isEmpty(mActualTax)) {
                actualTax.setError(REQUIRED);
                return;
            }

            Company company = new Company(mCompanyName, mSalary, mExpectedTax, mActualTax);
            mDatabase.child(company.getCompanyName()).setValue(company);
            Toast.makeText(getApplicationContext(), "Record added", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
     });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance().getReference().child("salary");

        FirebaseRecyclerOptions<Company> options =
                new FirebaseRecyclerOptions.Builder<Company>()
                        .setQuery(query, new SnapshotParser<Company>() {
                            @NonNull
                            @Override
                            public Company parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Company(snapshot.child("companyName").getValue().toString(),
                                        snapshot.child("salary").getValue().toString(),
                                        snapshot.child("expectedTax").getValue().toString(),
                                        snapshot.child("actualTax").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Company, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(Main.this)
                        .inflate(R.layout.to_be_inflated, parent, false);

                return new ViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(ViewHolder viewHolder, final int position, Company model) {
                viewHolder.setCompanyName("Company: "+model.getCompanyName());
                viewHolder.setSalary("Salary: "+ model.getSalary());
                viewHolder.setActualTax("Actual Tax: " + model.getActualTax());
                viewHolder.setExpectedTax("Expected Tax: " + model.getExpectedTax());

                viewHolder.companyName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                       updateData();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }



    @Override
    protected void onStart() {
        super.onStart();
        fetch();
        adapter.startListening();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView companyName;
        public TextView salary;
        public TextView expectedTax;
        public TextView actualTax;

        public ViewHolder(View itemView) {
            super(itemView);
             companyName= itemView.findViewById(R.id.company_name);
             salary= itemView.findViewById(R.id.salary);
             expectedTax = itemView.findViewById(R.id.expected_tax);
             actualTax = itemView.findViewById(R.id.actual_tax);

        }

        public void setSalary(String string) {
            salary.setText(string);
        }
        public void setExpectedTax(String string) {
           expectedTax.setText(string);
        }
        public void setActualTax(String string) {
            actualTax.setText(string);
        }
        public void setCompanyName(String string){
            companyName.setText(string);
        }
    }


}




