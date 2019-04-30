package spengergasse.at.ultiport;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import spengergasse.at.ultiport.entities.TransportRequest;

public class RequestInfoActivity extends AppCompatActivity {

    Context ctx=this;
    String auftrag, transporteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_info);

        Toolbar requestinfo_toolbar = findViewById(R.id.requestinfo_toolbar);
        setSupportActionBar(requestinfo_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent getMain = getIntent();
        TransportRequest request = getMain.getParcelableExtra("request");

        auftrag = String.valueOf(request.getReqNummer());
        transporteur = String.valueOf(getMain.getIntExtra("userID", 0));
    }

    public void Akzeptieren(View v) {
        BackGround b = new BackGround();
        b.execute(auftrag, transporteur);
    }
    public void Abschliessen(View v) {
        BackGroundFinish b = new BackGroundFinish();
        b.execute(auftrag, transporteur);
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
