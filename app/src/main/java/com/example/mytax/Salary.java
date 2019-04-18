package com.example.mytax;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Salary extends AppCompatActivity {
    private static final String TAG = "Salary";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView list;
    private TextView result;
    private EditText salary;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        mDisplayDate = (TextView)findViewById(R.id.textView);
        list = (TextView)findViewById(R.id.textView2);
        spinner = (Spinner) findViewById(R.id.spinner);
        result = (TextView)findViewById(R.id.result);
        salary = (EditText)findViewById(R.id.salary);
        final TypedArray procent=getResources().obtainTypedArray(R.array.percentage);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.commune,
                android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                list.setText(procent.getString(position));
                salary.getText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Salary.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month =month+1;
                Log.d(TAG,"onDateSet: mm/dd/yyy: " + month +"/"+ day + "/" + year);
                String date = month +"/"+ day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }
    public void count(View view){
        if (list!=null && !(list.getText().toString().isEmpty()) && !(salary.getText().toString().isEmpty())){
            double num1 = Double.parseDouble(list.getText().toString());
            double num2 = Double.parseDouble(salary.getText().toString());
            double num3;
            num3 = (num1*num2)/100;
            result.setText(""+num3);
        }

    }
}
