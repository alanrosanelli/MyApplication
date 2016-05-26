package com.example.todeolho.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.todeolho.myapplication.classes.Convenio;
import com.example.todeolho.myapplication.classes.Proponente;

public class ConvenioDetalhadoActivity extends Activity {

    private Convenio convenio = new Convenio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenio_detalhado);

        TextView txtModalidade = (TextView) findViewById(R.id.txtModalidade);
        TextView txtOrgaoConcedente = (TextView) findViewById(R.id.txtOrgaoConcedente);
        TextView txtJustificativaResumida = (TextView) findViewById(R.id.txtJustificativaResumida);
        TextView txtObjetoResumido = (TextView) findViewById(R.id.txtObjetoResumido);
        TextView txtInicioVigencia = (TextView) findViewById(R.id.txtDataInicioVigencia);
        TextView txtFimVigencia = (TextView) findViewById(R.id.txtDataFimVigencia);
        TextView txtValorGlobal = (TextView) findViewById(R.id.txtValorGlobal);
        TextView txtValorRepasse = (TextView) findViewById(R.id.txtValorRepasse);
        TextView txtValorContraPartida = (TextView) findViewById(R.id.txtValorContraPartida);
        TextView txtDataAssinatura = (TextView) findViewById(R.id.txtDataAssinatura);
        TextView txtDataPublicao = (TextView) findViewById(R.id.txtDataPublicacao);
        TextView txtSituacao = (TextView) findViewById(R.id.txtSituacao);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            convenio = (Convenio)getIntent().getSerializableExtra("convenio");
            txtModalidade.setText(convenio.getModalidade());
            txtOrgaoConcedente.setText(convenio.getOrgao_concedente());
            txtJustificativaResumida.setText(convenio.getJustificativa_resumida());
            txtObjetoResumido.setText(convenio.getObjeto_resumido());
            txtInicioVigencia.setText(convenio.getData_inicio_vigencia().toString());
            txtFimVigencia.setText(convenio.getData_fim_vigencia().toString());
            txtValorGlobal.setText(String.valueOf(convenio.getValor_global()));
            txtValorRepasse.setText(String.valueOf(convenio.getValor_repasse()));
            txtValorContraPartida.setText(String.valueOf(convenio.getValor_contra_partida()));
            //txtDataAssinatura.setText(convenio.getData_assinatura().toString());
            //txtDataPublicao.setText(convenio.getData_publicacao().toString());
            txtSituacao.setText(convenio.getSituacao());
        }

    }
}
