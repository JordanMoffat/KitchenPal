package io.moffat.kitchenpal;

import android.os.AsyncTask;

import com.parse.signpost.http.HttpResponse;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jordan on 09/08/2015.
 */
public class JSONParser {

    public JSONParser() {

    }

    public String getJSON(String url){

        DefaultHttpClient httpClient = new DefaultHttpClient((new BasicHttpParams()));
        HttpPost httppost = new HttpPost(url);


        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;

        try{
            org.apache.http.HttpResponse response = httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();

            String line = null;
            while((line = reader.readLine()) !=null){
                sb.append(line+"\n");

            }
            result = sb.toString();

        } catch(Exception e){
            System.out.println(e);
        }
        finally {
            try{if(inputStream !=null) inputStream.close();}catch (Exception f){}
        }
        return result;
    }

}
