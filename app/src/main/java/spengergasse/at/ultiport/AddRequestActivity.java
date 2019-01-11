package spengergasse.at.ultiport;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import spengergasse.at.ultiport.entities.TransportRequest;

public class AddRequestActivity extends AppCompatActivity {

    Context ctx = this;

    String GEBAUEDEGRUPPE;

    ArrayList<String> oeBez = new ArrayList<>();
    ArrayList<String>oeRaum =  new ArrayList<>();

    ArrayAdapter<String> adapterSpinner;
    ArrayAdapter<String> adapterRaum;
    Spinner startortOE;
    Spinner startraum;
    Spinner zielOE;
    Spinner zielraum;

    String StartortOE, Startraum, ZielOE, Zielraum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        Spinner spinnerA = findViewById(R.id.requestArt);
        startortOE = (Spinner)findViewById(R.id.requestStartOE);
        startraum = (Spinner)findViewById(R.id.requestStartRaum);
        zielOE = (Spinner)findViewById(R.id.requestZielOE);
        zielraum = (Spinner)findViewById(R.id.requestZielRaum);

        adapterSpinner=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,oeBez);
        startortOE.setAdapter(adapterSpinner);


        adapterRaum=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,oeRaum);
        startraum.setAdapter(adapterRaum);
        zielraum.setAdapter(adapterRaum);
        zielOE.setAdapter(adapterSpinner);

        ArrayAdapter<CharSequence> adapterA = ArrayAdapter.createFromResource(this,
                R.array.ArtDesTransports,R.layout.support_simple_spinner_dropdown_item);
        adapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerA.setAdapter(adapterA);



        Toolbar administration_toolbar = findViewById(R.id.add_request_toolbar);
        setSupportActionBar(administration_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
/*
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/

    }

    public void onStart(){
        super.onStart();
        BackTask bt=new BackTask();
        BackTask2 bt2 = new BackTask2();
        bt.execute();
        bt2.execute();
    }


    private class BackTask extends AsyncTask<Void, Void, Void> {
        ArrayList<String> SpinnerList;
        ArrayList<String> SpinnerRoomList;

        protected void onPreExecute() {
            super.onPreExecute();
            SpinnerList  = new ArrayList<>();
            SpinnerRoomList = new ArrayList<>();
        }

        protected Void doInBackground(Void... params) {
            InputStream is = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://ultiport.htl5.org/GetOE.php");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // Get our response as a String.
                is = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                is.close();
                //result=sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // parse json data
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    // add interviewee name to arraylist
                    GEBAUEDEGRUPPE = jsonObject.getString("oe_bez");
                    SpinnerList.add(jsonObject.getString("oe_bez"));
                    //SpinnerRoomList.add(jsonObject.getString("reqStartRaum"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }



        protected void onPostExecute(Void result) {
            oeBez.addAll(SpinnerList);
            oeRaum.addAll(SpinnerRoomList);
            adapterSpinner.notifyDataSetChanged();
            //adapterRaum.notifyDataSetChanged();
        }
    }

    private class BackTask2 extends AsyncTask<Void, Void, Void> {
        ArrayList<String> SpinnerRoomList;

        protected void onPreExecute() {
            super.onPreExecute();
            SpinnerRoomList = new ArrayList<>();
        }

        protected Void doInBackground(Void... params) {
            InputStream is = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://ultiport.htl5.org/GetSpinnerData.php");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // Get our response as a String.
                is = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                is.close();
                //result=sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // parse json data
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    // add interviewee name to arraylist
                    //if(GEBAUEDEGRUPPE.equals(jsonObject.getString(""))
                    SpinnerRoomList.add(jsonObject.getString("r_id"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }



        protected void onPostExecute(Void result) {
            oeRaum.addAll(SpinnerRoomList);
            adapterRaum.notifyDataSetChanged();
        }
    }



    public void addReqClick(View view){
        Zielraum = zielraum.getSelectedItem().toString();
        BackGround b = new BackGround();
        b.execute();
        Date date = new Date();
        EditText requestText = this.findViewById(R.id.requestText);
        TransportRequest request = new TransportRequest(null, null, null, null, date, requestText.getText().toString());

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("requestObject", request);
        startActivity(intent);
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
                s="Data saved successfully.";
            }
            Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
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
