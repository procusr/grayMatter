package com.example.mytax;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;



public class Inflater {

    ImageButton btnImg;
    private Toolbar toolbar;

    public void infoCar( Context context) {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View myView = inflater.inflate(R.layout.car_info, null);
        myDialog.setView(myView);
        final AlertDialog dialog = myDialog.create();
        toolbar=myView.findViewById(R.id.toolbar_close);
        btnImg =  myView.findViewById(R.id.close);

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public static void infoSalary(Context context){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View myView = inflater.inflate(R.layout.salary_info, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.show();
    }

    public static void infoHouse(Context context){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View myView = inflater.inflate(R.layout.house_info, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.show();

    }

}
