package com.example.todeolho.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getData = (Button) findViewById(R.id.getservicedata);
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String restURL = "http://dadosabertos.rio.rj.gov.br/apiTransporte/apresentacao/rest/index.cfm/onibus";
                new RestOperation().execute(restURL);
            }
        });
    }

    public class RestOperation extends AsyncTask<String ,Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        String data;
        TextView serverDataReceived = (TextView) findViewById(R.id.serverDataReceived);
        TextView showParsedJSON = (TextView) findViewById(R.id.showParsedJson);
        EditText userinput = (EditText) findViewById((R.id.userinput));


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setTitle("Please wait...");
            progressDialog.show();

            try {
                data += "&" + URLEncoder.encode("data","UTF-8") + "=" + userinput.getText();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            BufferedReader br = null;

            //URL url;
            try{
                //url = new URL(params[0]);
                String url = "http://api.convenios.gov.br/siconv/v1/consulta/municipios.json";

                HttpParams httpParameters=new BasicHttpParams();
                int timeoutConnection=10000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                int timeoutSocket=10000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                InputStream inputStream=null;

                HttpClient httpclient=new DefaultHttpClient(httpParameters);

                // Cria o GET e define a URL
                HttpGet httpget=new HttpGet(url);
                httpget.setHeader("Content-Type", "application/json");
                httpget.setHeader("Accept", "application/json");
                HttpResponse httpResponse = httpclient.execute(httpget);
                inputStream = httpResponse.getEntity().getContent();

                String resultado="";
                resultado=convertInputStreamToString(br,inputStream);
                content = resultado;

               /* URLConnection connection = url.openConnection();
                connection.setDoOutput(true);

                OutputStreamWriter outputStreamWr = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWr.write(data);
                outputStreamWr.flush();

                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = br.readLine())!=null){

                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }*/

               // content = sb.toString();

            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                /*try{
                    br.close();

                }catch (IOException e){
                    e.printStackTrace();
                }*/
            }
            return null;
        }

        private  String convertInputStreamToString(BufferedReader bufferedReader,InputStream inputStream) throws IOException
        {
             bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));

            int c;
            StringBuilder response=new StringBuilder();

            // Lê do buffer pegando caracter a caracter. Como particularmente este
            // fluxo consumido não possui fim de linha
            // com newline (\n), o método readLine() não funciona.
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
                serverDataReceived.setText("Error " + error);
            } else {
                serverDataReceived.setText(content);

                String output = "";
                JSONObject jsonResponse;

                try {
                    jsonResponse = new JSONObject(content);

                    JSONArray jsonArray = jsonResponse.optJSONArray("municipios");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);

                        String name = child.getString("nome");

                        output = "Nome=" + name;
                    }

                    showParsedJSON.setText(output);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
