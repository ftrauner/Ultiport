package spengergasse.at.ultiport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import spengergasse.at.ultiport.adapter.RecyclerItemClickListener;
import spengergasse.at.ultiport.adapter.RequestsAdapter;
import spengergasse.at.ultiport.database.DatabaseHandler;
import spengergasse.at.ultiport.entities.TransportRequest;
import spengergasse.at.ultiport.service.RequestWebService;

public class MainActivity extends AppCompatActivity {




    private static String userGruppe = null;
    public static int userID;

    List<TransportRequest> mRequestList;
    RecyclerView mRecyclerView;
    RequestsAdapter mRequestAdapter;
    String CHANNEL_ID = "ABC123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        Toolbar main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);

        //Intent der Login-Klasse holen
        Intent intentLogIn = getIntent();
        //Übergebene Nutzergruppe als String speichern
        //Setze static Parameter zur Überprüfung der Berechtigung
        userGruppe = prefs.getString("userGruppe", LoginActivity.GRUPPE);
        userID = intentLogIn.getIntExtra("userID", prefs.getInt("userID",0));

        RequestsAdapter adapter = new RequestsAdapter();

        mRecyclerView = findViewById(R.id.requestList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(userGruppe.equals("3")  ){
                            if(mRequestList.get(position).getReqStatus().equals("1") && mRequestList.get(position).getReqTransporteur()==0) {
                                Intent intent = new Intent(MainActivity.this, RequestInfoActivity.class);
                                intent.putExtra("request", mRequestList.get(position));
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                            }
                            else{
                                Snackbar.make(findViewById(R.id.main_layout), R.string.main_in_progress, Snackbar.LENGTH_SHORT).show();
                            }
                            if(mRequestList.get(position).getReqStatus().equals("2") && mRequestList.get(position).getReqTransporteur()==userID) {
                                Intent intent = new Intent(MainActivity.this, RequestInfoActivity.class);
                                intent.putExtra("request", mRequestList.get(position));
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                            }
                            else{
                                Snackbar.make(findViewById(R.id.main_layout), R.string.main_in_progress, Snackbar.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Snackbar.make(findViewById(R.id.main_layout), R.string.main_keine_berechtigung, Snackbar.LENGTH_SHORT).show();
                        }
                    }


                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


        //Intent intent = new Intent(this, RequestWebService.class);
        //startService(intent);

        RequestData();
    }
/*
    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
*/
    private void RequestData() {

        RequestWebService webService = RequestWebService.retrofit.create(RequestWebService.class);
        Call<TransportRequest[]> call = webService.requests();

        call.enqueue(new Callback<TransportRequest[]>() {
            @Override
            public void onResponse(Call<TransportRequest[]> call, retrofit2.Response<TransportRequest[]> response) {
                TransportRequest[] transportRequests = response.body();
                mRequestList = Arrays.asList(transportRequests);
                DisplayData();
                System.out.println(response.code());

            }

            @Override
            public void onFailure(Call<TransportRequest[]> call, Throwable t) {
                System.out.println("GEHT NICHT!");
                System.out.println(t.getMessage());
            }
        });
    }

    private void DisplayData() {
        if (mRequestList != null) {
            mRequestAdapter = new RequestsAdapter(this, mRequestList);
            mRecyclerView.setAdapter(mRequestAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Berechtigungen werden überprüft
        switch (item.getItemId()) {
            case R.id.action_add_request: {
                if (LoginActivity.GRUPPE.equals("1") || LoginActivity.GRUPPE.equals("2")) {
                    Intent intent = new Intent(this, AddRequestActivity.class);
                    intent.putExtra("userID", userID);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(findViewById(R.id.main_layout), R.string.main_keine_berechtigung, Snackbar.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.action_administration: {
                if (LoginActivity.GRUPPE.equals("1")) {
                    Intent intent = new Intent(this, AdministrationActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(R.id.main_layout), R.string.main_keine_berechtigung, Snackbar.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.auftrag_akt: {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

        }
        return true;
    }


/*
    public void sendToken(View view) {


        final String token = SharedPreference.getInstance(this).getDeviceToken();

        if (token == null) {
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER_DEVICE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                return params;
            }
        };
        FcmVolley.getInstance(this).addToRequestQueue(stringRequest);
    }
    */
}
