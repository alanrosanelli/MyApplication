package com.example.todeolho.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todeolho.myapplication.classes.Util;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, OnItemSelectedListener{

    AutoCompleteTextView textView=null;
    private ArrayAdapter<String> adapter;

    String item[]={
            "Taquara - RS - 13466","Parobe - RS - 13579","Igrejinha - RS - 15963"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPesquisa = (Button) findViewById(R.id.btnPesquisa);
        Boolean conexao;
        Util util = new Util();

        textView = (AutoCompleteTextView) findViewById(R.id.txtMunicipio);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        conexao = util.VerificaConexaoInternet(getBaseContext());

        textView.setThreshold(1);
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);


        if(conexao == false){

            Toast toast = Toast.makeText(getApplicationContext(),"Verifique sua conexão com a internet",Toast.LENGTH_LONG);
            toast.show();

        }

        btnPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView txtPesquisa = (TextView) findViewById(R.id.txtMunicipio);

                String[] municipio = txtPesquisa.getText().toString().split("-");
                String nomeMunicipio = municipio[0];
                String uf = municipio[1];
                String idMunicipio = municipio[2];

                Bundle bundle = new Bundle();
                bundle.putString("nomeMunicipio", nomeMunicipio);
                bundle.putString("uf", uf);
                bundle.putString("idMunicipio", idMunicipio);

                Intent secondActivity = new Intent(MainActivity.this, ProponenteActivity.class);
                startActivity(secondActivity.putExtras(bundle));
            }
        });
    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
       // AppEventsLogger.activateApp(this);
    }


    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        //AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//        Toast.makeText(getBaseContext(), "Position:"+arg2+" Month:"+arg0.getItemAtPosition(arg2),
//                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
