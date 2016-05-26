package com.example.todeolho.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.todeolho.myapplication.DAO.Json;
import com.example.todeolho.myapplication.classes.Convenio;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConvenioActivity extends Activity {

    private ArrayList<String> convenioLista = new ArrayList<String>();
    private ArrayList<Convenio> convenioListaObjeto = new ArrayList<Convenio>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenio);

        Bundle bundle = this.getIntent().getExtras();

        String idProponente = bundle.getString("id_proponente").trim();

        String restURL = "http://api.convenios.gov.br/siconv/v1/consulta/convenios.json?id_proponente=" + idProponente;
        new RestOperation().execute(restURL);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,convenioLista);

        ListView lv= (ListView) findViewById(R.id.listConvenio);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                Intent secondActivity = new Intent(ConvenioActivity.this, ConvenioDetalhadoActivity.class);

                secondActivity.putExtra("convenio", convenioListaObjeto.get(posicao));

                startActivity(secondActivity);

            }
        });
    }

    public class RestOperation extends AsyncTask<String ,Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(ConvenioActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setTitle("Por Favor espere...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {

            InputStream inputStream = null;
            String url;
            try{

                url = params[0];
                content = Json.getInputStream(url, inputStream);

            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
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

                        String href = child.getString("href");
                        String[] hrefSplit = href.split("convenio/");
                        String id = hrefSplit[1];

                        convenio.setId(id);
                        convenio.setModalidade(child.getString("modalidade"));
                        convenio.setOrgao_concedente(child.getString("orgao_concedente"));
                        convenio.setJustificativa_resumida(child.getString("justificativa_resumida"));
                        convenio.setObjeto_resumido(child.getString("objeto_resumido"));

                        String data = child.getString("data_inicio_vigencia");
                        Date dataInicioVigencia = null;
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        dataInicioVigencia = (java.util.Date) formatter.parse(data);
                        convenio.setData_inicio_vigencia(dataInicioVigencia);


                        Date dataFimVigencia = null;
                        String date = child.getString("data_fim_vigencia");
                        dataFimVigencia = (java.util.Date) formatter.parse(date);
                        convenio.setData_fim_vigencia(dataFimVigencia);

                        convenio.setValor_global(Double.parseDouble(child.getString("valor_global")));
                        convenio.setValor_repasse(Double.parseDouble(child.getString("valor_repasse")));
                        convenio.setValor_contra_partida(Double.parseDouble(child.getString("valor_contra_partida")));

                        Date dataAssinatura = null;
                        String dateAssinatura = child.getString("data_assinatura");


                        if( dataAssinatura != null)
                        {
                            dataAssinatura = (java.util.Date) formatter.parse(dateAssinatura);
                            convenio.setData_assinatura(dataAssinatura);
                        }



                        Date dataPublicacao = null;
                        String datePublicacao = child.getString("data_publicacao");
                        if( dataPublicacao != null)
                        {
                            dataPublicacao = (java.util.Date) formatter.parse(datePublicacao);
                            convenio.setData_publicacao(dataPublicacao);
                        }


                        convenio.setSituacao(child.getString("situacao"));
                        convenio.setSituacao(child.getString("proponente"));

                        convenioLista.add(convenio.getObjeto_resumido());

                        convenioListaObjeto.add(convenio);
                    }

                    ListView lv= (ListView) findViewById(R.id.listConvenio);
                    lv.invalidateViews();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
