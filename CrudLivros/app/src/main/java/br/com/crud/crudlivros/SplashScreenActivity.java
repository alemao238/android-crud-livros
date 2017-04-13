package br.com.crud.crudlivros;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.crud.crudlivros.dao.UsuarioDAO;
import br.com.crud.crudlivros.model.Usuario;

public class SplashScreenActivity extends AppCompatActivity {

    // tempo de duração que a splashscreen ficará visível na tela
    private final int SPLASH_DISPLAY_LENGTH = 3500;

    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // método para realizar a animação
        carregar();

        // método para carregar os usuários
        new BuscaDados().execute("http://www.mocky.io/v2/58b9b1740f0000b614f09d2f");
    }

    // animação
    private void carregar() {

        ivLogo = (ImageView) findViewById(R.id.ivLogo);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);
        anim.reset();

        if (ivLogo != null) {
            ivLogo.clearAnimation();
            ivLogo.startAnimation(anim);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    // buscar os dados de usuario
    private class BuscaDados extends AsyncTask<String, Void, String> {

        ProgressDialog pdLoading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading = new ProgressDialog(SplashScreenActivity.this);
            pdLoading.setMessage("Carregando os dados de usuário.");
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(15000);
                conn.setConnectTimeout(10000);

                conn.setRequestMethod("GET");
                conn.setDoOutput(true);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    InputStream is = conn.getInputStream();
                    BufferedReader buffer =
                            new BufferedReader(
                                    (new InputStreamReader(is)));

                    StringBuilder result = new StringBuilder();
                    String linha;

                    // enquanto receber a leitura de linha
                    while ((linha = buffer.readLine()) != null) {
                        result.append(linha);
                    }

                    // fechar a conexão
                    conn.disconnect();

                    return result.toString();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(SplashScreenActivity.this);
            if (s == null) {
                Toast.makeText(SplashScreenActivity.this, "Não foi possível carregar os dados de usuário.", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject json = new JSONObject(s);
                    Usuario usuario = new Usuario();
                    usuario.setLogin(json.getString("usuario"));
                    usuario.setSenha(json.getString("senha"));

                    usuarioDAO.add(usuario);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            pdLoading.dismiss();
        }
    }

}
