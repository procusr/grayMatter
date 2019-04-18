package com.example.myapplication;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class CarAdd extends AppCompatActivity {
private TextView distance ;
private TextView out;
private static final String TAG = "CarAdd";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView eDisplayDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add);
        distance = findViewById(R.id.text_input_distance);
        out = findViewById(R.id.Amount);
        mDisplayDate = findViewById(R.id.tvDate);
        eDisplayDate= findViewById(R.id.endDate);

        distance.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {

            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
                if(!validateDistance()){
                    return;
                }
                String edistance = distance.getText().toString();

                double k = Double.parseDouble(edistance);
                out.setText(" " +k*1.85+ ".kr");

            }


        });



        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CarAdd.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        eDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CarAdd.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });





        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
                eDisplayDate.setText(date);
            }
            };

        }




      private boolean validateDistance() {
          if (distance.getText().toString().isEmpty()) {
              System.out.println("qw");
              distance.setError("Filed can not be Empty");
              return false;
          } else if (Double.parseDouble(distance.getText().toString()) < 5) {
              System.out.println("qqqq");
              distance.setError("Distance entered should be more than 5 killometers");
              return false;
          } else {
              System.out.println("xxxx");
              distance.setError(null);
              return true;

          }

      }

   /*public void saveInput(View v){
       if(!validateDistance()){
           return;
      }
      String edistance = distance.getText().toString();

       double k = Double.parseDouble(edistance);
      out.setText(" " +k*1.85+ ".kr");

    }*/

}
