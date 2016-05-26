package com.example.todeolho.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.todeolho.myapplication.classes.Proponente;

public class ProponenteDetalhadoActivity extends Activity  {

    private Proponente proponente = new Proponente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proponente_detalhado);

        TextView txtCnpj = (TextView) findViewById(R.id.txtModalidade);
        TextView txtNome = (TextView) findViewById(R.id.txtNome);
        TextView txtMunicipio = (TextView) findViewById(R.id.txtMunicipio);
        TextView txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        TextView txtCep = (TextView) findViewById(R.id.txtCep);
        TextView txtNomeResponsavel = (TextView) findViewById(R.id.txtNomeResponsavel);
        TextView txtCpfResponsavel = (TextView) findViewById(R.id.txtCpfResponsavel);
        TextView txtTelefone = (TextView) findViewById(R.id.txtTelefone);
        TextView txtInscEstadual = (TextView) findViewById(R.id.txtInscEstadual);
        TextView txtInscMunicipal = (TextView) findViewById(R.id.txtInscMunicipal);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            proponente = (Proponente)getIntent().getSerializableExtra("proponente");
            txtCnpj.setText(proponente.getCnpj());
            txtNome.setText(proponente.getNome());
            txtMunicipio.setText(proponente.getMunicipio());
            txtEndereco.setText(proponente.getEndereco());
            txtCep.setText(proponente.getCep());
            txtNomeResponsavel.setText(proponente.getNomeresponsavel());
            txtCpfResponsavel.setText(proponente.getCpfresponsavel());
            txtTelefone.setText(proponente.getTelefone());
            txtInscEstadual.setText(proponente.getInscricaoestadual());
            txtInscMunicipal.setText(proponente.getInscricaomunicipal());
        }

        Button btnConvenio = (Button) findViewById(R.id.btnConvenio);
        btnConvenio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("id_proponente", proponente.getId());

                Intent secondActivity = new Intent(ProponenteDetalhadoActivity.this, ConvenioActivity.class);
                startActivity(secondActivity.putExtras(bundle));
            }
        });
    }
}
