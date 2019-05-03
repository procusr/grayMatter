package com.example.mytax;
import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.List;

public class AppIntroActivity extends AppCompatActivity {

    //animation
    private RelativeLayout myLayout = null;
    private LinearLayout l1,l2;
    private Animation uptodown,downtoup;
    private DatabaseReference ref ;
    private FirebaseDatabase mDatabase;
    public ArrayList<Integer> array;
    private FirebaseAuth mAuth;
    public ArrayList<Integer> arr;
    //Graphs



   private  Integer salary[] ;




    String months[] = {"Jan", "Feb", "Mar", "Apr", "May"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid=mUser.getUid();
        ref =FirebaseDatabase.getInstance().getReference().child("mainDb").child(uid).child("salary").child("date");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                showDatabase(dataSnapshot);

            }

            private void showDatabase (DataSnapshot dataSnapshot){
                array = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String salary = ds.getKey();
                    array.add(Integer.parseInt(salary));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        arr = new ArrayList<>();
        arr.add(23);
        arr.add(232);
        arr.add(22);

        for(int i=0;i<arr.size();i++){
            salary[i] = arr.get(i);
        }


        //Welcome animation setup
        myLayout = findViewById(R.id.myLayout);
        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AppIntroActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        });


        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        setupChart();

    }

    private void setupChart()
    {
        //Populating a list of pie entries

        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i = 0; i < salary.length; i++)
        {
            pieEntries.add(new PieEntry(salary[i].floatValue(), months[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        PieData data = new PieData(dataSet);
        data.setValueTextSize(25f);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setSliceSpace(7f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueFormatter(new PercentFormatter());


        data.setValueTextColor(Color.WHITE);
        dataSet.setValueLinePart1OffsetPercentage(10.f);
        dataSet.setValueLinePart1Length(0.60f);
        dataSet.setValueLinePart2Length(.1f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setSelectionShift(65);

        //Display the chart

        PieChart chart = findViewById(R.id.chart);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
        chart.animateY(2800);
        chart.invalidate();
        chart.setTransparentCircleColor(Color.RED);
        chart.setMaxAngle(360);
        chart.setDrawCenterText(true);
        chart.setCenterText("Gross Salary \n" + "kr 75.000");
        chart.setHoleRadius(40);
        chart.setTransparentCircleRadius(0);
        chart.getDescription().setText("TaxMe");
        chart.getDescription().setTextColor(Color.BLACK);
        chart.getDescription().setTextSize(12);
        chart.setUsePercentValues(true);




        chart.setEntryLabelColor(Color.WHITE);

        //legend display
        Legend legend = chart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setDrawInside(false);


    }

}
