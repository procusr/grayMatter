package com.example.mytax.kommun_JSON;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * 1.Establishes Connection
 * 2.Set connection properties
 *
 */

public class Connector {

    public static Object connect(String jsonURL)
    {

        try
        {
            URL url = new URL(jsonURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

           //set connection props

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);


            return connection;
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
            return "Error" + e.getMessage();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error" + e.getMessage();

        }

    }

}
