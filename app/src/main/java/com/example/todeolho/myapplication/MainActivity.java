package com.example.todeolho.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.todeolho.myapplication.classes.Convenio;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    ArrayList<String> convenioList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getData = (Button) findViewById(R.id.getservicedata);


        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userinput = (EditText) findViewById((R.id.userinput));
                String restURL = "http://api.convenios.gov.br/siconv/v1/consulta/convenios.json?" +"uf=" + userinput.getText();
                new RestOperation().execute(restURL);


            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,convenioList);

        ListView lv= (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);


    }

    public class RestOperation extends AsyncTask<String ,Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setTitle("Por Favor espere...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {

            InputStream inputStream = null;

            try{

                String url = params[0];

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

            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private  String convertInputStreamToString(InputStream inputStream) throws IOException
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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            if(error!=null){

            } else {


                String output = "";
                JSONObject jsonResponse;

                try {
                    jsonResponse = new JSONObject(content);

                    JSONArray jsonArray = jsonResponse.optJSONArray("convenios");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);

                        Convenio convenio =  new Convenio();
                        convenio.setModalidade(child.getString("modalidade"));
                        convenioList.add(convenio.getModalidade());


                    }


                    ListView lv= (ListView) findViewById(R.id.list);
                    lv.invalidateViews();
                    //showParsedJSON.setText(output);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
