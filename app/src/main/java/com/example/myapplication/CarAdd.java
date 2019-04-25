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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import static com.example.myapplication.R.id.save_car;
import static com.example.myapplication.R.id.text_view_endDate;

public class CarAdd extends AppCompatActivity {
    private EditText editTextdistance;
    private TextView textViewstartDate;
    private TextView textViewendDate;
    private Switch   switchgpsDistance;
    private EditText editTextorigin;
    private EditText editTextdestination;
    private EditText editTextpurpose;
    private EditText editTextamount;



private static final String TAG = "CarAdd";

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener eDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Car Info");



        editTextdistance = findViewById(R.id.edit_text_distance);
        textViewstartDate = findViewById(R.id.text_view_startDate);
        textViewendDate = findViewById(text_view_endDate);
        switchgpsDistance = findViewById(R.id.switch_gpsDistance);
        editTextorigin = findViewById(R.id.edit_text_origin);
        editTextdestination = findViewById(R.id.edit_text_destination);
        editTextpurpose = findViewById(R.id.edit_text_purpose);
        editTextamount = findViewById(R.id.edit_text_amount);


        editTextdistance.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {

            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
                if (!validateDistance()) {
                    return;
                }
                String edistance = editTextdistance.getText().toString();

                double k = Double.parseDouble(edistance);
                editTextamount.setText(" " + k * 1.85 + ".kr");

            }


        });


        textViewstartDate.setOnClickListener(new View.OnClickListener() {
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

        textViewendDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CarAdd.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        eDateSetListener, year, month, day);

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
                textViewstartDate.setText(date);

            }
        };
        eDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                textViewendDate.setText(date);

            }


        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_car_menu, menu);
        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_car:
                saveCar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validateDistance() {
        if (editTextdistance.getText().toString().isEmpty()) {

            editTextdistance.setError("Filed can not be Empty");
            return false;
        } else if (Double.parseDouble(editTextdistance.getText().toString()) < 5) {

            editTextdistance.setError("Distance entered should be more than 5 killometers");
            return false;
        } else if (textViewstartDate.getText().toString().isEmpty()) {

            textViewstartDate.setError("Filed can not be Empty");
            return false;
        } else {

            editTextdistance.setError(null);
            return true;
        }


    }


   public void saveCar(){
       if(!validateDistance()){
           return;
      }
      String distance = editTextdistance.getText().toString();
       String startDate= textViewstartDate.getText().toString();
       String endDate= textViewendDate.getText().toString();
       String  origin = editTextorigin.getText().toString();
       String destination = editTextdestination.getText().toString();
       String purpose = editTextpurpose.getText().toString();
       String amount = editTextamount.getText().toString();

      // CollectionReference carrRef = FirebaseFirestore.getInstance().
               //collection("CarInfo");

      // carrRef.add(new CarInfo(distance, startDate, endDate, origin, destination, purpose, amount));

       Toast.makeText(this , " Saved", Toast.LENGTH_SHORT).show();
       finish();
       /*double k = Double.parseDouble(edistance);
      out.setText(" " +k*1.85+ ".kr");*/

    }

}
