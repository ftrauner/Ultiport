package spengergasse.at.ultiport;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class EditUserActivity extends AppCompatActivity {

    EditText  id, name, password, vorname, benutzername;
    Spinner gruppe;
    String Id,Name, Password, Vorname, Benutzername, Gruppe;
    //int Id;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Toolbar deleteUserToolbar = findViewById(R.id.edit_user_toolbar);
        setSupportActionBar(deleteUserToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Spinner spinner = findViewById(R.id.edit_benutzer_gruppe);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        id = (EditText) findViewById(R.id.edit_benutzer_id);
        name = (EditText) findViewById(R.id.edit_benutzer_nachname);
        password = (EditText) findViewById(R.id.edit_benutzer_passwort);
        vorname = (EditText) findViewById(R.id.edit_benutzer_vorname);
        benutzername = (EditText) findViewById(R.id.edit_benutzername);
        gruppe = (Spinner) findViewById(R.id.edit_benutzer_gruppe);
    }

    public void edit_edit(View v){
        Id = id.getText().toString();
        Name = name.getText().toString();
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
        b.execute(Id, Name, Vorname,Benutzername,Password,Gruppe);
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String name = params[1];
            String vorname = params[2];
            String benutzername = params[3];
            String password = params[4];
            String gruppe = params[5];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://ultiport.htl5.org/EditUser.php");
                String urlParams = "e_name="+name+"&e_vorname="+vorname+"&e_benutzername="+benutzername+"&e_passwort="+password+"&e_g_gruppe="+gruppe+"&e_id="+id;

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
                s="Data updated successfully.";
            }
            Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
        }
    }
}