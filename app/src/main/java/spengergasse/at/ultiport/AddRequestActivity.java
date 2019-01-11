package spengergasse.at.ultiport;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
import java.util.ArrayList;
import java.util.Date;

import spengergasse.at.ultiport.entities.TransportRequest;

public class AddRequestActivity extends AppCompatActivity {

    String GEBAUEDEGRUPPE;

    ArrayList<String> oeBez = new ArrayList<>();
    ArrayList<String>oeRaum =  new ArrayList<>();

    ArrayAdapter<String> adapterSpinner;
    ArrayAdapter<String> adapterRaum;
    Spinner sp;
    Spinner sp2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        Spinner spinnerA = findViewById(R.id.requestArt);
        sp = (Spinner)findViewById(R.id.requestStartOE);
        sp2 = (Spinner)findViewById(R.id.requestStartRaum);

        adapterSpinner=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,oeBez);
        sp.setAdapter(adapterSpinner);

        adapterRaum=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,oeRaum);
        sp2.setAdapter(adapterRaum);

        ArrayAdapter<CharSequence> adapterA = ArrayAdapter.createFromResource(this,
                R.array.ArtDesTransports,R.layout.support_simple_spinner_dropdown_item);
        adapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerA.setAdapter(adapterA);



        Toolbar administration_toolbar = findViewById(R.id.add_request_toolbar);
        setSupportActionBar(administration_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

        Date date = new Date();
        EditText requestText = this.findViewById(R.id.requestText);
        TransportRequest request = new TransportRequest(null, null, null, null, date, requestText.getText().toString());

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("requestObject", request);
        startActivity(intent);
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
