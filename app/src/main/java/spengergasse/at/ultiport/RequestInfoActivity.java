package spengergasse.at.ultiport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
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
import spengergasse.at.ultiport.entities.TransportRequest;
import spengergasse.at.ultiport.service.RequestWebService;

public class RequestInfoActivity extends AppCompatActivity {

    Context ctx=this;
    String auftragID, transporteurID;
    static TransportRequest transportRequest;
    static TransportRequest request_main;
    ImageView request_info_art;
    TextView request_info_start, request_info_ende, request_info_beschreibung, request_info_anf_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_info);
        SharedPreferences prefs = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        request_info_art = findViewById(R.id.request_info_bild);
        request_info_start = findViewById(R.id.request_info_start);
        request_info_ende = findViewById(R.id.request_info_ende);
        request_info_beschreibung = findViewById(R.id.request_info_beschreibung);
        request_info_anf_name = findViewById(R.id.request_info_anf_name);

        Toolbar requestInfo_toolbar = findViewById(R.id.requestinfo_toolbar);
        setSupportActionBar(requestInfo_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent getMain = getIntent();
        request_main = getMain.getParcelableExtra("request");

        auftragID = String.valueOf(request_main.getReqNummer());
        transporteurID = String.valueOf(getMain.getIntExtra("userID", prefs.getInt("userID",0)));

        GetRequest();
    }

    private void GetRequest(){
        RequestWebService webService = RequestWebService.retrofit.create(RequestWebService.class);
        Call<TransportRequest> call = webService.request(request_main.getReqNummer());
        call.enqueue(new Callback<TransportRequest>() {
            @Override
            public void onResponse(Call<TransportRequest> call, Response<TransportRequest> response) {
                transportRequest = response.body();
                DisplayData();
                System.out.println(response.code());
            }

            @Override
            public void onFailure(Call<TransportRequest> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void DisplayData(){
        if(transportRequest!=null){
            switch(transportRequest.getReqArt()){
                //Rollstuhl
                case "1": {
                    request_info_art.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_patient));
                    break;
                }
                //Laborprobe
                case "2": {
                    request_info_art.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_laborprobe));
                    break;
                }
                //Bett
                case "3": {
                    request_info_art.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_bett));
                    break;
                }
            }
            String start = transportRequest.getReqStartOE()+transportRequest.getReqStartRaum();
            String ende = transportRequest.getReqEndOE()+transportRequest.getReqEndRaum();
            request_info_start.setText(start);
            request_info_ende.setText(ende);
            request_info_beschreibung.setText(transportRequest.getReqBeschr());
        }
        else{
            System.out.println("äääähhhhh");
        }
    }

    public void Akzeptieren(View v) {
        BackGround b = new BackGround();
        b.execute(auftragID, transporteurID);
    }
    public void Abschliessen(View v) {
        BackGroundFinish b = new BackGroundFinish();
        b.execute(auftragID, transporteurID);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String auftragid = params[0];
            String transid = params[1];

            int tmp;

            try {
                URL url = new URL("http://ultiport.htl5.org/AuftragAccept.php");
                String urlParams = "a_nummer="+auftragid+"&a_e_trans="+transid;
                String data="";

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

    class BackGroundFinish extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String auftragid = params[0];
            String transid = params[1];

            int tmp;

            try {
                URL url = new URL("http://ultiport.htl5.org/AuftragFinish.php");
                String urlParams = "a_nummer="+auftragid+"&a_e_trans="+transid;
                String data="";

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
