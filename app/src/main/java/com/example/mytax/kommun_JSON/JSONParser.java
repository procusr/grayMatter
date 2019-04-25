package com.example.mytax.kommun_JSON;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * 1.Receive downloaded Json
 * 2.Parse Json n fill ArrayList
 * 3.Bind ArrayList to Spinner
 */

public class JSONParser extends AsyncTask<Void, Void, Boolean> {

    Context c;
    String jsonData;
    Spinner spinner;

    ArrayList<String> municipality = new ArrayList<>();

    public JSONParser(Context c, String jsonData, Spinner spinner) {
        this.c = c;
        this.jsonData = jsonData;
        this.spinner = spinner;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return this.parse();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);

        if(isParsed)
        {
          //Bind
            ArrayAdapter<String> adapter = new ArrayAdapter<>(c,android.R.layout.simple_list_item_1, municipality);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    //int position = Integer.parseInt(municipality.get(i));

                    /*
                    JSONArray jsonArray = new JSONArray(jsonData);
                    JSONObject jsonObject;

                    jsonObject = jsonArray.getJSONObject(i);
                    String tax = jsonObject.getString("LocalTaxRate");

                  put tax in a textView after converting into int

                     */

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else{
            Toast.makeText(c, "Unable to parse JSON", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean parse()
    {
        try
        {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONObject jsonObject;

            municipality.clear();

            for (int i=0; i<jsonArray.length();i++)
            {
                jsonObject = jsonArray.getJSONObject(i);

                String mName = jsonObject.getString("Municipality");
                municipality.add(mName);
            }

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }
}
