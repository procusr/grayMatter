package com.example.mytax;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
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
//import static com.example.mytax.R.id.switch_gpsDistance;
import static com.example.mytax.R.id.text_view_endDate;


public class CarMain extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = AutoCarActivity.class.getSimpleName();
    TextView textView;
    Button btnStartUpdates;
    Button btnStopUpdates;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private SettingsClient mSettingsClient;
    private LocationCallback mLocationCallback;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mCurrentLocation;
    Context context;
    private Switch switch1;
    private TextView traker;
    final int REQUEST_CHECK_SETTINGS = 1;
    final int REQUEST_LOCATION = 2;
    public Boolean locUpdates;
    public Boolean useGPS;  // pref: use_device_location
    //private Boolean hasLocPermissions;
    SharedPreferences preferences;
    static Double lat1 = null;
    static Double lon1 = null;
    static Double lat2 = null;
    static Double lon2 = null;
    static Double distance = 0.0;
    static int status = 0;
    private TextView distance_tracker;
    private Boolean mRequestingLocationUpdates;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private String distance1;
    private String startDate;
    private String endDate;
    private String gpsDistance;
    private String origin;
    private String destination;
    private String purpose;
    private String amount;
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
        final EditText mgpsDistance= myView.findViewById(R.id.update_gps_distance);
        final EditText morigin = myView.findViewById(R.id.update_origin);
        final EditText mdestination = myView.findViewById(R.id.update_destination);
        final EditText mpurpose = myView.findViewById(R.id.update_purpose);
        final EditText mamount = myView.findViewById(R.id.update_amount);


        mdistance.setText(distance1);
        mdistance.setSelection(distance1.length());


        mstartDate.setText(startDate);
        mstartDate.setSelection(startDate.length());

        mendDate.setText(endDate);
        mendDate.setSelection(endDate.length());

        mgpsDistance.setText(gpsDistance);
        mgpsDistance.setSelection(gpsDistance.length());

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

            public void setting (View view){
                Intent intent = new Intent(CarMain.this, SettingActivity.class);
                startActivity(intent);
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

                distance1 = mdistance.getText().toString().trim();
                startDate = mstartDate.getText().toString().trim();
                endDate= mendDate.getText().toString().trim();
                gpsDistance = mgpsDistance.getText().toString().trim();
                origin = morigin.getText().toString().trim();
                destination = mdestination.getText().toString().trim();
                purpose = mpurpose.getText().toString().trim();
                amount = mamount.getText().toString().trim();



                Car car = new Car(distance1, startDate, endDate,gpsDistance, origin,destination, purpose, amount);
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
        textView = (TextView) findViewById(R.id.distance);

        switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(this);

        traker = findViewById(R.id.TV_traker);

        distance_tracker = findViewById(R.id.distance_tracker);
        mRequestingLocationUpdates = false;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        useGPS = preferences.getBoolean("use_device_location", false);
        locUpdates = false;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        loginToFirebase();
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
        loginToFirebase();

        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {

                    try {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                distance_tracker.setText(String.valueOf(distance));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();


        final AlertDialog dialog = myDialog.create();


        dialog.setCancelable(false);

        final EditText distance = myView.findViewById(R.id.edit_text_distance);
        final TextView startDate = myView.findViewById(R.id.text_view_startDate);
        final TextView endDate = myView.findViewById(text_view_endDate);
        //final Switch gpsSwitch = myView.findViewById(switch_gpsDistance);
        final EditText gpsDistance = myView.findViewById(R.id.edit_text_gps);
        final EditText origin = myView.findViewById(R.id.edit_text_origin);
        final EditText destination = myView.findViewById(R.id.edit_text_destination);
        final EditText purpose = myView.findViewById(R.id.edit_text_purpose);
        final EditText amount = myView.findViewById(R.id.edit_text_amount);
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
                    amount.setText(" " + mam + "  kr");
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



                DatePickerDialog dialog = new DatePickerDialog(
                        CarMain.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


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

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(
                        CarMain.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        eDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String mDistance = distance.getText().toString().trim();
                String mStartDate = startDate.getText().toString().trim();
                String mEndDate = endDate.getText().toString().trim();
                String mGpsdistance = gpsDistance.getText().toString().trim();
                String mOrgin = origin.getText().toString().trim();
                String mDestination = destination.getText().toString().trim();
                String mPurpose = purpose.getText().toString().trim();
                String mAmount = amount.getText().toString().trim();

                if(mDistance.isEmpty()|| mStartDate.startsWith("S")||mEndDate.startsWith("E")){
                    Toast.makeText(getApplicationContext(), "Please provide all the inputs", Toast.LENGTH_SHORT).show();
                    return;

                }

                LocalDate s = LocalDate.parse(mStartDate, DateTimeFormatter.ofPattern("M dd yyyy"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                String sDate = s.format(formatter);

                 LocalDate e = LocalDate.parse(mEndDate, DateTimeFormatter.ofPattern("M dd yyyy"));
                 String eDate = e.format(formatter);

                 Car car = new Car(mDistance, sDate, eDate, mGpsdistance, mOrgin, mDestination, mPurpose, mAmount);
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
                                        snapshot.child("gpsDistance").getValue().toString(),
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
                viewHolder.setDistance("Distance       :" + model.getDistance());
                viewHolder.setStartDate("Start Date    :" + model.getStartDate());
                viewHolder.setEndDate ("End Date         :   " + model.getEndDate());
                viewHolder.setGpsDistance("Gps Distance :   " +model.getGpsDistance());
                viewHolder.setOrigin("Origin           :   " +model.getOrigin());
                viewHolder.setDestination("Destination  :   " +model.getDestination());
                viewHolder.setPurpose("Purpose          :   " +model.getPurpose());
                viewHolder.setAmount("Amount            :   " +model.getAmount());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        distance1 = model.getDistance();
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



    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView distance;
        public TextView startDate;
        public TextView  endDate;
        public TextView gpsDistance;
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

        public void setGpsDistance(String string) {
            gpsDistance = mView.findViewById(R.id.text_view_gpsDistance);
            gpsDistance.setText(string);
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
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            updateUI();
        }
    }


    // Start Fused Location services
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                updateLocationUI();
            }
        };
    }


    private void buildLocationSettingsRequest() {
        // get current locations settings of user's device
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void updateLocationUI() {

        final String path = getString(R.string.firebase_path);
        if (mCurrentLocation != null) {

            if (status == 0) {
                lat1 = mCurrentLocation.getLatitude();
                lon1 = mCurrentLocation.getLongitude();
            } else if ((status % 2) != 0) {
                lat2 = mCurrentLocation.getLatitude();
                lon2 = mCurrentLocation.getLongitude();
                distance += distanceBetweenTwoPoint(lat1, lon1, lat2, lon2);
            } else if ((status % 2) == 0) {
                lat1 = mCurrentLocation.getLatitude();
                lon1 = mCurrentLocation.getLongitude();
                distance += distanceBetweenTwoPoint(lat2, lon2, lat1, lon1);
            }
            status++;
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
            ref.setValue(distance);


            if (mCurrentLocation != null) {
                Log.d(TAG, "location update " + mCurrentLocation);

            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        updateUI();
                        break;
                }
                break;
        }
    }


    /**
     * Handles the Start Updates button and requests start of location updates. Does nothing if
     * updates have already been requested.
     */
    public void startUpdatesButtonHandler(View view) {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            //setButtonsEnabledState();
            startLocationUpdates();
        }
    }

    /**
     * Handles the Stop Updates button, and requests removal of location updates.
     */

    public void stopUpdatesButtonHandler(View view) {
        stopLocationUpdates();
    }

    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
     * runtime permission has been granted.
     */
    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(CarMain.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(CarMain.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                        updateUI();
                    }
                });
    }


    /**
     * Updates all UI fields.
     */
    private void updateUI() {
        //  setButtonsEnabledState();
        updateLocationUI();
    }


    private void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }

        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;

                    }
                });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mRequestingLocationUpdates && checkPermissions()) {
//            startLocationUpdates();
//        } else if (!checkPermissions()) {
//            requestPermissions();
//        }
//
//        updateUI();
//    }

    @Override
    protected void onPause() {
        super.onPause();

        stopLocationUpdates();
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {

    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(CarMain.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(CarMain.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mRequestingLocationUpdates) {
                    Log.i(TAG, "Permission granted, updates requested, starting location updates");
                    startLocationUpdates();
                }
            } else {

                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }


    // Set new latitude and longitude based on location results
    public void setLatLong(Location location) {
        double lastLat = location.getLatitude();
        double lastLong = location.getLongitude();
        if (status == 0) {
            lat1 = location.getLatitude();
            lon1 = location.getLongitude();
        } else if ((status % 2) != 0) {
            lat2 = location.getLatitude();
            lon2 = location.getLongitude();
            distance += distanceBetweenTwoPoint(lat1, lon1, lat2, lon2);
        } else if ((status % 2) == 0) {
            lat1 = location.getLatitude();
            lon1 = location.getLongitude();
            distance += distanceBetweenTwoPoint(lat2, lon2, lat1, lon1);

            String totalDistance = new Double(distance).toString();
            textView.setText(totalDistance);
        }
        status++;
        final String path = getString(R.string.firebase_path);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ref.setValue(distance);


        if (location != null) {
            Log.d(TAG, "location update " + location);
            //ref.setValue(location);
        }

        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("last_gps_lat", String.valueOf(lastLat));
        edit.putString("last_gps_long", String.valueOf(lastLong));
        //edit.putString("Distance", String.valueOf(distance));
        edit.apply();
    }

    double distanceBetweenTwoPoint(double srcLat, double srcLng, double desLat, double desLng) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(desLat - srcLat);
        double dLng = Math.toRadians(desLng - srcLng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(srcLat))
                * Math.cos(Math.toRadians(desLat)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        double meterConversion = 1609;

        return (int) (dist * meterConversion);
    }


    private void loginToFirebase() {
        // Functionality coming next step

        String email = "dishabahre@gmail.com";
        String password = "dishabahre09";
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "firebase auth success");

                    createLocationCallback();

                } else {
                    Log.d(TAG, "firebase auth failed");
                }
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (switch1.isChecked()) {
            mRequestingLocationUpdates = true;
            startLocationUpdates();
            traker.setText("Stop");
        } else {
            stopLocationUpdates();
            traker.setText("Start");
            System.out.println(distance);

        }
    }



}
