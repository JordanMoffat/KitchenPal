package io.moffat.kitchenpal;

import com.parse.signpost.http.HttpResponse;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
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

    public JSONParser(){

    }



    public JSONObject getJSON(String url){

        JSONParser parsenr = new JSONParser();
        JSONObject code = new JSONObject();




        return code;
    }

}
