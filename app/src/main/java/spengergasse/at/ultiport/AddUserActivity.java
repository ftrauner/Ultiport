package spengergasse.at.ultiport;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddUserActivity extends AppCompatActivity  {

    EditText  name, password, vorname, benutzername;
    Spinner gruppe;
    String Name, Password, Vorname, Benutzername, Gruppe;
    //int Id;
    Context ctx=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Spinner spinner = findViewById(R.id.benutzer_gruppe);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //id = (EditText) findViewById(R.id.benutzer_id);
        name = (EditText) findViewById(R.id.benutzer_nachname);
        password = (EditText) findViewById(R.id.benutzer_passwort);
        vorname = (EditText) findViewById(R.id.benutzer_vorname);
        benutzername = (EditText) findViewById(R.id.benutzer_benutzername);
        gruppe = (Spinner) findViewById(R.id.benutzer_gruppe);

        Toolbar administration_toolbar = findViewById(R.id.add_user_toolbar);
        setSupportActionBar(administration_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    public void register_register(View v){
        Name = name.getText().toString();
        //Id = Integer.parseInt(id.getText().toString());
        //Id = id.getText().toString();mat1
        Password = password.getText().toString();
        Vorname = vorname.getText().toString();
        Benutzername = benutzername.getText().toString();
        if(gruppe.getSelectedItem().toString().equals("Admin")){
            Gruppe = "1";
        }

        if(gruppe.getSelectedItem().toString().equals("Requester")){
            Gruppe = "2";
        }

        if(gruppe.getSelectedItem().toString().equals("Transporteur")){
            Gruppe = "3";
        }
        BackGround b = new BackGround();
        b.execute(Name, Vorname,Benutzername,Password,Gruppe);
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            //String id = params[0];
            String name = params[0];
            String vorname = params[1];
            String benutzername = params[2];
            String password = params[3];
            String gruppe = params[4];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://ultiport.htl5.org/register.php");
                String urlParams = "e_name="+name+"&e_vorname="+vorname+"&e_benutzername="+benutzername+"&e_passwort="+password+"&e_g_gruppe="+gruppe;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }
                is.close();
                httpURLConnection.disconnect();

                return data;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("")){
                s=getString(R.string.add_user_success);
            }
            Snackbar.make(findViewById(R.id.add_user_layout),s,Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //Zurück-Pfeil wird geklickt
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return true;
    }
}
