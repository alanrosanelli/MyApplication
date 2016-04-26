package com.example.todeolho.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.todeolho.myapplication.classes.Proponente;
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
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ProponenteActivity extends ActionBarActivity {
    ArrayList<String> proponenteLista = new ArrayList<String>();
    ArrayList<Proponente> proponenteListaObjeto = new ArrayList<Proponente>();

    private String municipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proponente);

        Bundle bundle = this.getIntent().getExtras();
        String idMunicipio = bundle.getString("idMunicipio").trim();
        municipio = bundle.getString("nomeMunicipio").trim();


        TextView txtPesquisa = (TextView) findViewById(R.id.txtMunicipio);

        String restURL = "http://api.convenios.gov.br/siconv/v1/consulta/proponentes.json?id_municipio=" + idMunicipio;
        new RestOperation().execute(restURL);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,proponenteLista);

        ListView lv= (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                Intent secondActivity = new Intent(ProponenteActivity.this, ProponenteDetalhadoActivity.class);

                secondActivity.putExtra("proponente", proponenteListaObjeto.get(posicao));

                startActivity(secondActivity);

            }
        });

    }


    public class RestOperation extends AsyncTask<String ,Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(ProponenteActivity.this);

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

                    JSONArray jsonArray = jsonResponse.optJSONArray("proponentes");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);

                        Proponente proponente =  new Proponente();

                        proponente.setId(child.getString("id"));
                        proponente.setCnpj(child.getString("cnpj"));
                        proponente.setNome(child.getString("nome"));
                        proponente.setMunicipio(municipio);
                        proponente.setEndereco(child.getString("endereco"));
                        proponente.setCep(child.getString("cep"));
                        proponente.setNomeresponsavel(child.getString("nome_responsavel"));
                        proponente.setCpfresponsavel(child.getString("cpf_responsavel"));
                        proponente.setTelefone(child.getString("telefone"));
                        proponente.setInscricaoestadual(child.getString("inscricao_estadual"));
                        proponente.setInscricaomunicipal(child.getString("inscricao_municipal"));
                        proponenteLista.add(proponente.getNome());
                        proponenteListaObjeto.add(proponente);

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
