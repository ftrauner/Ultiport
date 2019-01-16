package spengergasse.at.ultiport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import spengergasse.at.ultiport.entities.TransportRequest;

public class PopAbschließen extends Activity {
    Context ctx=this;

    String auftrag, transporteur;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popfinish);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        Intent getMain = getIntent();
        TransportRequest request = getMain.getParcelableExtra("request");

        auftrag = String.valueOf(request.getReqNummer());
        transporteur = String.valueOf(getMain.getIntExtra("userID", 0));

        getWindow().setLayout((int)(width*.4), (int)(height*.2));
    }
    public void abschliessen(View v) {

        BackGround b = new BackGround();
        b.execute(auftrag, transporteur);
    }

    class BackGround extends AsyncTask<String, String, String> {

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
