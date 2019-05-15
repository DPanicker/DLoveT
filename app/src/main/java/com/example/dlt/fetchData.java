package com.example.dlt;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class fetchData extends AsyncTask<Void,Void,Void> {
    String data ="";
    static String  dataParsed = "" ;
    String videoId ="";


    @Override
    public Void doInBackground(Void... voids) {
        try { //   UCRzO050f2DOQ0iZsylVH4dw
            URL url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId="PASTE YOUR CHANNEL ID HERE"&eventType=live&type=video&key=AIzaSyCoFnySF8hOI0Z1pBqlqG0Lc0Ed-guRU80");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONObject someObject = new JSONObject(data);
            JSONArray jArr = someObject.getJSONArray("items");
            for(int i =0; i < jArr.length(); i++)
            {
                // getting object from items array
                JSONObject itemObj = jArr.getJSONObject(i);

                // getting id object from item object
                JSONObject idObj = itemObj.getJSONObject("id");

                // getting videoId from idObject
                dataParsed = idObj.getString("videoId");
                System.out.print(dataParsed);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getData(){

        videoId = dataParsed;
        return  videoId;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
