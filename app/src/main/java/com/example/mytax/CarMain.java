package com.example.mytax;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

//import static com.example.myapplication.R.id.date;

import static com.example.mytax.R.id.btn_gps;
import static com.example.mytax.R.id.text_view_endDate;


public class CarMain extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private String distance;
    private String startDate;
    private String endDate;
    private String gpsDistance;
    private String origin;
    private String destination;
    private String purpose;
    private String amount;
    private Toolbar toolbar;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public DatePickerDialog.OnDateSetListener mDateSetListener;
    public DatePickerDialog.OnDateSetListener eDateSetListener;
    private Double k;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_rec_list);


        recyclerView = findViewById(R.id.list);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("mainDb");
        mDatabase.keepSynced(true);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Car Info");

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);

        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "No data Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRecord();
            }
        });
    }
    public void updateData() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View myView = inflater.inflate(R.layout.activity_car_update, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();

        final EditText mdistance = myView.findViewById(R.id.update_distance);
        final EditText mstartDate = myView.findViewById(R.id.update_start_date);
        final EditText mendDate = myView.findViewById(R.id.update_end_date);
        final EditText morigin = myView.findViewById(R.id.update_origin);
        final EditText mdestination = myView.findViewById(R.id.update_destination);
        final EditText mpurpose = myView.findViewById(R.id.update_purpose);
        final EditText mamount = myView.findViewById(R.id.update_amount);
        mamount.setKeyListener(null);

        mdistance.setText(distance);
        mdistance.setSelection(distance.length());

        mstartDate.setText(startDate);
        mstartDate.setSelection(startDate.length());

        mendDate.setText(endDate);
        mendDate.setSelection(endDate.length());

        morigin.setText(origin);
        morigin.setSelection(origin.length());

        mdestination.setText(destination);
        mdestination.setSelection(destination.length());

        mpurpose.setText(purpose);
        mpurpose.setSelection(purpose.length());

        mamount.setText(amount);
        mamount.setSelection(amount.length());

        Button btnDelete = myView.findViewById(R.id.btnDelete);
        Button btnUpdate = myView.findViewById(R.id.btnUpdate);

        mdistance.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {


            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {

                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
                if((mdistance.getText().toString()).isEmpty()){

                    mdistance.setError("Empty");
                }
                else if ( Double.parseDouble(mdistance.getText().toString())< 5 || mdistance.getText().toString().isEmpty()
                        ||mstartDate.getText().toString().isEmpty()) {
                    mdistance.setError("Please provide all the inputs or your distance is less than 5 Km");
                    //Toast.makeText(getApplicationContext(), "Please provide all the inputs or your distance is less than 5 Km", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    k = Double.parseDouble(mdistance.getText().toString());
                    Double d = k * 1.85;
                    String mam = String.format("%.2f", d);
                    mamount.setText(" " + mam + "  kr");

                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                distance = mdistance.getText().toString().trim();
                startDate = mstartDate.getText().toString().trim();
                endDate= mendDate.getText().toString().trim();
                origin = morigin.getText().toString().trim();
                destination = mdestination.getText().toString().trim();
                purpose = mpurpose.getText().toString().trim();
                amount = mamount.getText().toString().trim();
                Car car = new Car(distance, startDate, endDate, origin,destination, purpose, amount);
                mDatabase.child("cardb").child(car.getStartDate()).setValue(car);
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("cardb").child(startDate).removeValue();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void submitRecord() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View myView = inflater.inflate(R.layout.activity_car_add, null);
        myDialog.setView(myView);
        final AlertDialog dialog = myDialog.create();

        dialog.setCancelable(false);

        final EditText distance = myView.findViewById(R.id.edit_text_distance);
        final TextView startDate = myView.findViewById(R.id.text_view_startDate);
        final TextView endDate = myView.findViewById(text_view_endDate);
        final EditText origin = myView.findViewById(R.id.edit_text_origin);
        final EditText destination = myView.findViewById(R.id.edit_text_destination);
        final EditText purpose = myView.findViewById(R.id.edit_text_purpose);
        final EditText amount = myView.findViewById(R.id.edit_text_amount);
        final Button gps =myView.findViewById(R.id.btn_gps);
        amount.setKeyListener(null);
        final Button btnCancel = myView.findViewById(R.id.btnCancel);
        final Button btnAdd = myView.findViewById(R.id.btnSave);


        distance.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {

            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {

                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
                if((distance.getText().toString()).isEmpty()){

                    distance.setError("Empty");
                }
               else if ( Double.parseDouble(distance.getText().toString())< 5 || distance.getText().toString().isEmpty()
                        ||startDate.getText().toString().isEmpty()) {
                   distance.setError("Please provide all the inputs or your distance is less than 5 Km");

                    return;
                }else{
                    k = Double.parseDouble(distance.getText().toString());
                    Double d = k * 1.85;
                    String mam = String.format("%.2f", d);
                    amount.setText(" " + mam );
                }
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog2 = new DatePickerDialog(
                        CarMain.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();


            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + " " + day + " " + year;
                startDate.setText(date);
            }
        };
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog1 = new DatePickerDialog(
                        CarMain.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        eDateSetListener, year, month, day);

                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();
            }
        });

        eDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + " " + day + " " + year;
                endDate.setText(date);
            }
        };

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AutoCarActivity.class);
                startActivity(intent);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String mDistance = distance.getText().toString().trim();
                String mStartDate = startDate.getText().toString().trim();
                String mEndDate = endDate.getText().toString().trim();
                String mOrgin = origin.getText().toString().trim();
                String mDestination = destination.getText().toString().trim();
                String mPurpose = purpose.getText().toString().trim();
                String mAmount = amount.getText().toString().trim();

                if(mDistance.isEmpty()|| mStartDate.startsWith("S")||mEndDate.startsWith("E")){
                    Toast.makeText(getApplicationContext(), "Please provide all the inputs", Toast.LENGTH_SHORT).show();
                    return;

                }

                LocalDate s = LocalDate.parse(mStartDate, DateTimeFormatter.ofPattern("M d yyyy"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                String sDate = s.format(formatter);

                 LocalDate e = LocalDate.parse(mEndDate, DateTimeFormatter.ofPattern("M d yyyy"));
                 String eDate = e.format(formatter);

                 Car car = new Car(mDistance, sDate, eDate,  mOrgin, mDestination, mPurpose, mAmount);
                 mDatabase.child("cardb").child(car.getStartDate()).setValue(car);
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
        Query query = FirebaseDatabase.getInstance().getReference().child("mainDb").child("cardb");

        FirebaseRecyclerOptions<Car> options =
                new FirebaseRecyclerOptions.Builder<Car>()
                        .setQuery(query, new SnapshotParser<Car>() {
                            @NonNull
                            @Override
                            public Car parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Car(snapshot.child("distance").getValue().toString(),
                                        snapshot.child("startDate").getValue().toString(),
                                        snapshot.child("endDate").getValue().toString(),
                                        snapshot.child("origin").getValue().toString(),
                                        snapshot.child("destination").getValue().toString(),
                                        snapshot.child("purpose").getValue().toString(),
                                        snapshot.child("amount").getValue().toString());
                            }
                        })
                        .build();


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                Toast.makeText(getApplicationContext(), "Deleted ", Toast.LENGTH_SHORT).show();
                final int position = viewHolder.getAdapterPosition();
                adapter.getRef(position).removeValue();
            }
        };

        ItemTouchHelper it = new ItemTouchHelper(simpleItemTouchCallback);
        it.attachToRecyclerView(recyclerView);


        adapter = new FirebaseRecyclerAdapter<Car, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.car_item, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ViewHolder viewHolder, final int position, final Car model) {
                viewHolder.setDistance(model.getDistance());
                viewHolder.setStartDate(model.getStartDate());
                viewHolder.setEndDate (model.getEndDate());
                viewHolder.setOrigin(model.getOrigin());
                viewHolder.setDestination(model.getDestination());
                viewHolder.setPurpose(model.getPurpose());
                viewHolder.setAmount(model.getAmount() + " kr" );


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        distance = model.getDistance();
                        startDate = model.getStartDate();
                        endDate = model.getEndDate();
                        gpsDistance = model.getGpsDistance();
                        origin=model.getOrigin();
                        destination =model.getDestination();
                        purpose=model.getPurpose();
                        amount=model.getAmount();
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

    public void goAuto(View view) {
        Intent intent = new Intent(this,AutoCarActivity.class);
        startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView distance;
        public TextView startDate;
        public TextView endDate;
        public TextView origin;
        public TextView destination;
        public TextView purpose;
        public TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDistance(String string) {
            distance = mView.findViewById(R.id.text_view_distance);
            distance.setText(string);
        }

        public void setStartDate(String string) {
            startDate = mView.findViewById(R.id.text_view_startDate);
            startDate.setText(string);
        }
        public void setEndDate(String string) {
            endDate = mView.findViewById(text_view_endDate);
           endDate.setText(string);
        }

        public void setOrigin(String string) {
            origin = mView.findViewById(R.id.text_view_origin);
            origin.setText(string);
        }

        public void setDestination(String string) {
            destination = mView.findViewById(R.id.text_view_destination);
            destination.setText(string);
        }

        public void setPurpose(String string) {
            purpose = mView.findViewById(R.id.text_view_purpose);
            purpose.setText(string);
        }
        public void setAmount (String string) {
            amount = mView.findViewById(R.id.text_view_amount);
            amount.setText(string);
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }
}
