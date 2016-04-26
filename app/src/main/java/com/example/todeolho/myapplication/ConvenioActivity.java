package com.example.todeolho.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ConvenioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenio);

        Bundle bundle = this.getIntent().getExtras();

        String idMunicipio = bundle.getString("id_proponente").trim();

    }
}
