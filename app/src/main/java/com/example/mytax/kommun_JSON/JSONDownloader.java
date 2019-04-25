package com.example.mytax.kommun_JSON;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 *
 * 1.Connect to network
 * 2.Download data in background
 * 3.Parse data to JSONParser
 */
public class JSONDownloader extends AsyncTask<Void,Void,String> {

    Context c;
    String jsonURL;
    Spinner spinner;

    public JSONDownloader(Context c, String jsonURL, Spinner spinner) {
        this.c = c;
        this.jsonURL = jsonURL;
        this.spinner = spinner;
    }



    @Override
    protected String doInBackground(Void... voids) {
        return this.download();
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        if(jsonData.startsWith("Error")){
            Toast.makeText(c,jsonData,Toast.LENGTH_SHORT).show();
        }
        else{
            //Parse
            new JSONParser(c,jsonData,spinner).execute();
        }
    }

    private String download()
    {
        Object connection= Connector.connect(jsonURL);
        if(connection.toString().startsWith("Error"))
        {
            return connection.toString();
        }
        try
        {
            HttpURLConnection con = (HttpURLConnection) connection;
            if(con.getResponseCode()==con.HTTP_OK)
            {

                //Get input from Stream
                InputStream inputStream = new BufferedInputStream(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));

                String line;
                StringBuffer jsonData = new StringBuffer();

                //Read
                while((line=bufferedReader.readLine()) != null)
                {
                    jsonData.append(line+"\n");
                }

                //close resourses
                bufferedReader.close();
                inputStream.close();


                //return JSON
                return jsonData.toString();

            }else{
                return "Error"+con.getResponseMessage();

            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error"+e.getMessage();
        }
    }
}


