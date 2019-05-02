//Displays the whole summary of the tax activitty

package com.example.mytax;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Summary extends AppCompatActivity {
    private TextView mTotalSalary;
    private TextView mTotalTaxDue;
    private TextView mTotalExtax;
    private TextView mTotalPaid;
    private TextView mCarRebate;
    private TextView mTotalRebate;
    private double sumCar = 0;
    private double sum = 0;
    private double sume = 0;
    private double sump = 0;
    private double sumRebate;
    private double sumDue;
    private double pvalue;
    private double avalue;
    private double evalue;
    private double cvalue;

    private DatabaseReference dbtax;
    private DatabaseReference dbRebate;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);


        mTotalSalary = (TextView) findViewById(R.id.btn_total_salary);
        mTotalTaxDue = (TextView) findViewById(R.id.btn_total_due);
        mTotalExtax = (TextView) findViewById(R.id.btn_total_tax);
        mTotalPaid = (TextView) findViewById(R.id.btn_tax_paid);
        mCarRebate = (TextView) findViewById(R.id.btn_rebate_car);
        mTotalRebate = (TextView) findViewById(R.id.btn_total_rebate);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        String uid = mUser.getUid();

        //1. SELECT * FROM Artists

        dbtax = FirebaseDatabase.getInstance().getReference("mainDb").child(uid).child("salary");
        dbRebate = FirebaseDatabase.getInstance().getReference("mainDb").child(uid).child("cardb");


        dbRebate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                try {
                    for (DataSnapshot dsalary : dataSnapshot.getChildren()) {

                        Map<String, Object> mapcar = (Map<String, Object>) dsalary.getValue();
                        Object car = mapcar.get("amount");
                        cvalue = Double.parseDouble(String.valueOf(car));
                        sumCar += cvalue;

                        mCarRebate.setText(String.valueOf(sumCar));

                        sumRebate = sumCar;
                        mTotalRebate.setText(String.valueOf(sumRebate));

                    }

                } catch (NumberFormatException e) {

                    mCarRebate.setText(String.valueOf(0));
                    mTotalRebate.setText(String.valueOf(0));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


        dbtax.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    for (DataSnapshot dsalary : dataSnapshot.getChildren()) {

                        Map<String, Object> map = (Map<String, Object>) dsalary.getValue();
                        Object salary = map.get("salary");
                        pvalue = Double.parseDouble(String.valueOf(salary));
                        sum += pvalue;

                        mTotalSalary.setText(String.valueOf(sum));

                    }
                }

                catch (NumberFormatException e) {


                    mTotalSalary.setText(String.valueOf(0));
                }


                try{
                    for (DataSnapshot dpaid : dataSnapshot.getChildren()) {

                        Map<String, Object> map_paid = (Map<String, Object>) dpaid.getValue();
                        Object ex_tax = map_paid.get("actualTax");
                        avalue = Double.parseDouble(String.valueOf(ex_tax));
                        sump += avalue;

                        mTotalPaid.setText(String.valueOf(sump));

                    }
                }

                catch (NumberFormatException e) {

                    mTotalPaid.setText(String.valueOf(0));


                }

                try{

                    for (DataSnapshot dpaid : dataSnapshot.getChildren()) {

                        Map<String, Object> mape = (Map<String, Object>) dpaid.getValue();
                        Object ex_tax = mape.get("expectedTax");
                        evalue = Double.parseDouble(String.valueOf(ex_tax));
                        sume += evalue;


                        mTotalExtax.setText(String.valueOf(sume));

                    }



                } catch (NumberFormatException e) {


                    mTotalTaxDue.setText(String.valueOf(0));

                }

                sumDue = sume- sump - sumCar;
                mTotalTaxDue.setText(String.valueOf(sumDue));

            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){


            }


        });


    }

}







