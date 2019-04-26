package com.example.mytax;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class AppIntroActivity extends AppCompatActivity {

    //animation
    private RelativeLayout myLayout = null;
    private LinearLayout l1,l2;
    private Animation uptodown,downtoup;

    //Graphs

//    float yValues[] = {3500, 3800, 4500, 4100, 4250};
//    float taxTBP[] = {4000, 3900, 4400, 5000, 4250};
    float salary[] = {20000, 21000, 19000, 18000, 19500};
    String months[] = {"Jan", "Feb", "Mar", "Apr", "May"};

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        //Bar chart setup
//        barChart = findViewById(R.id.barchart);
//        barChart.setDrawBarShadow(false);
//        barChart.setDrawValueAboveBar(true);
//        barChart.setMaxVisibleValueCount(50);
//        barChart.setPinchZoom(false);
//        barChart.setDrawGridBackground(true);
//
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        barEntries.add(new BarEntry(1, 40f));
//        barEntries.add(new BarEntry(2, 44f));
//        barEntries.add(new BarEntry(3, 50f));
//        barEntries.add(new BarEntry(4, 36f));
//
//        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
//        barEntries1.add(new BarEntry(1, 44f));
//        barEntries1.add(new BarEntry(2, 40f));
//        barEntries1.add(new BarEntry(3, 55f));
//        barEntries1.add(new BarEntry(4, 32f));
//
//        BarDataSet barDataSet = new BarDataSet(barEntries, "Tax Payed");
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "Expected Tax");
//        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        BarData data = new BarData(barDataSet, barDataSet1);
//
//        float groupSpace = 0.1f;
//        float barSpace = 0.02f;
//        float barWidh = 0.43f;
//
//        barChart.setData(data);
//
//        data.setBarWidth(barWidh);
//        barChart.groupBars(1,groupSpace, barSpace);
//
//        String months[] = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "June"};
//
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setValueFormatter(new MyXAxisValueFormatter(months));
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setAxisMinimum(1);


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

   //     barChart = (BarChart) findViewById(R.id.chart);
       setupChart();
    }

//    public class MyXAxisValueFormatter implements IAxisValueFormatter
//    {
//        private String[] mValues;
//        public MyXAxisValueFormatter(String[] values) {
//            this.mValues = values;
//        }
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            return mValues[(int)value];
//        }
//    }


    private void setupChart()
    {
        //Populating a list of pie entries

        List<PieEntry> pieEntries = new ArrayList<>();
         for(int i = 0; i < salary.length; i++)
         {
             pieEntries.add(new PieEntry(salary[i], months[i]));
         }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Salary for 2018");
        PieData data = new PieData(dataSet);

        //Display the chart

        PieChart chart = findViewById(R.id.chart);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
        chart.animateY(2100);
        chart.invalidate();
    }

//    public void main(View view)
//    {
//        Intent intent = new Intent(this , MainActivity.class);
//        startActivity(intent);
//    }
}
