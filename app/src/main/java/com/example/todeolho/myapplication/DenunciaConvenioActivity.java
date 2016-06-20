package com.example.todeolho.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.todeolho.myapplication.classes.Pessoa;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DenunciaConvenioActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL = "http://citronics.com.br/api/pessoas";

    CallbackManager callbackManager;
    Pessoa pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        facebookSDKInitialize();
        setContentView(R.layout.activity_denuncia_convenio);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","user_about_me");
        getLoginDetails(loginButton);

        Bundle bundle = this.getIntent().getExtras();

        String idConvenio = bundle.getString("id_convenio").trim();

        Button btnSalvaDenuncia = (Button) findViewById(R.id.btnSalvaDenuncia);

        btnSalvaDenuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpAsyncPOST().execute();
            }
        });

    }

    private class HttpAsyncPOST extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return post();
        }

        @Override
        protected void onPostExecute(String result) {
            //editTextNome.setText("");
            imprimirMensagem(result);
        }

    }

    private String post() {
        String mensagem = "";

        TextView txtTitulo = (TextView) findViewById(R.id.txtNome);
        TextView txtDenuncia = (TextView) findViewById(R.id.txtDenuncia);

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);
        try {
            JSONObject json = new JSONObject();
            json.put("nome", "fgfsg");
            json.put("email", "email");

            post.setEntity(new ByteArrayEntity(json.toString().getBytes(
                    "UTF8")));
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpclient.execute(post);
            mensagem = inputStreamToString(response.getEntity().getContent()).toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensagem;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String linha = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((linha = rd.readLine()) != null) {
                total.append(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    private void imprimirMensagem(String mensagem) {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("data",data.toString());
    }

    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    protected void getLoginDetails(LoginButton login_button){

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
//                Intent intent = new Intent(DenunciaConvenioActivity.this,DenunciaConvenioActivity.class);
//                startActivity(intent);

                final ImageView imageFacebook = (ImageView) findViewById(R.id.imagePerfil);
                final TextView txtNome = (TextView) findViewById(R.id.txtNome);


                GraphRequest request = GraphRequest.newMeRequest(
                        login_result.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("TAG", "JSON: " + object);
                                try {
                                    //String foto = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                    String id = object.getString("id");
                                    String foto = "https://graph.facebook.com/"+id+"/picture?height=120&width=120";
                                    String nome = object.getString("name");
                                    String email = object.getString("email");
                                    Glide.with(getBaseContext())
                                    .load(foto)
                                            .centerCrop()
                                            .fitCenter()
                                            .override(300, 100)
                                            .into(imageFacebook); // id do teu imageView.
                                    txtNome.setText(nome);

                                    pessoa.setNome(nome);
                                    pessoa.setEmail(email);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture.width(120).height(120)");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // code for cancellation
            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
