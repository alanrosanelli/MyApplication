package com.example.todeolho.myapplication.DAO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Alan on 26/04/2016.
 */
public class Json {

    public static String getInputStream(String url, InputStream inputStream) throws IOException {

        String content;

        HttpParams httpParameters=new BasicHttpParams();
        int timeoutConnection=90000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket=90000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Content-Type", "application/json");
        httpget.setHeader("Accept", "application/json");
        HttpResponse httpResponse = httpclient.execute(httpget);
        inputStream = httpResponse.getEntity().getContent();

        content = convertInputStreamToString(inputStream);

        return  content;
    }

    private static  String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));

        int c;
        StringBuilder response=new StringBuilder();

        while ((c=bufferedReader.read()) != -1)
        {
            response.append((char) c);
        }
        String result=response.toString();
        inputStream.close();
        bufferedReader.close();

        return result;
    }


}
