package spengergasse.at.ultiport;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText name, password;
    String Name, Password;
    Context ctx = this;
    String NAME = null, GRUPPE = null, VORNAME = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.nameText);
        password = findViewById(R.id.passwortText);
    }

    public void main_login(View v) {
        Name = name.getText().toString();
        Password = password.getText().toString();
        BackGround b = new BackGround();
        b.execute(Name, Password);
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String name = params[0];
            String password = params[1];

            StringBuilder data = new StringBuilder();
            int tmp;

            try {

                URL url = new URL("http://ultiport.htl5.org/login.php");

                String urlParams = "e_benutzername=" + name + "&e_passwort=" + password;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data.append((char) tmp);
                }
                is.close();
                httpURLConnection.disconnect();

                return data.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {

            String err = null;
            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                NAME = user_data.getString("e_name");
                GRUPPE = user_data.getString("e_g_gruppe");
                VORNAME = user_data.getString("e_vorname");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent ma = new Intent(ctx, MainActivity.class);
            //Intent admin = new Intent(ctx,)

            //Bedingung: Erhaltenes JSON-Objekt ist nicht leer
            if (!s.equals("{\"user_data\":[]}")) {
                //Übergebe Nutzergruppe
                ma.putExtra("userGruppe",GRUPPE);
                //Starte MainActivity
                startActivity(ma);
            /*
            1...Admin
            2...Requester
            3...Transporteur

            if (GRUPPE.equals("1")){

            }

            else if(GRUPPE.equals("2")){
                startActivity(ara);
            }

            else if(GRUPPE.equals("3")){
                startActivity(ma);
            }
        */
            } else {
                //Nachricht wenn Eingabedaten falsch
                Snackbar.make(findViewById(R.id.login_layout), R.string.login_falsch, Snackbar.LENGTH_SHORT).show();
            }

        }
    }
}









