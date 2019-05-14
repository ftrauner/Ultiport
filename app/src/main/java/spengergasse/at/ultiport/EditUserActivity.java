package spengergasse.at.ultiport;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spengergasse.at.ultiport.entities.User;
import spengergasse.at.ultiport.service.UserWebService;

public class EditUserActivity extends AppCompatActivity {

    User user;
    EditText  name, password, vorname, benutzername;
    TextView id;
    Spinner gruppe;
    String Id,Name, Password, Vorname, Benutzername, Gruppe;
    String userID;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        userID =  intent.getStringExtra("selectedID");

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


        id = findViewById(R.id.edit_benutzer_id);
        name = findViewById(R.id.edit_benutzer_nachname);
        password = findViewById(R.id.edit_benutzer_passwort);
        vorname = findViewById(R.id.edit_benutzer_vorname);
        benutzername = findViewById(R.id.edit_benutzername);
        gruppe = findViewById(R.id.edit_benutzer_gruppe);

        GetUser();
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

    private void GetUser(){
        UserWebService webService = UserWebService.retrofit.create(UserWebService.class);
        Call<User> call = webService.user(userID);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                user = response.body();
                DisplayData();
                System.out.println(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void DisplayData(){
        if(user!=null){
            id.setText(user.getId());
            name.setText(user.getNachname());
            vorname.setText(user.getVorname());
            benutzername.setText(user.getUsername());

            password.setText(user.getPasswort());

            switch ((user.getGruppe())){
                case "1":{
                    gruppe.setSelection(0);
                    break;
                }
                case "2":{
                    gruppe.setSelection(1);
                    break;
                }
                case "3":{
                    gruppe.setSelection(2);
                    break;
                }
            }

        }
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
                s=getString(R.string.edit_user_success);
            }
            Snackbar.make(findViewById(R.id.edit_user_layout),s,Snackbar.LENGTH_SHORT).show();
        }
    }
}