package spengergasse.at.ultiport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spengergasse.at.ultiport.adapter.RecyclerItemClickListener;
import spengergasse.at.ultiport.adapter.RequestsAdapter;
import spengergasse.at.ultiport.database.DatabaseHandler;
import spengergasse.at.ultiport.entities.TransportRequest;
import spengergasse.at.ultiport.service.RequestWebService;

public class MainActivity extends AppCompatActivity {




    private static String userGruppe = null;
    int userID;
    List<TransportRequest> mRequestList;
    RecyclerView mRecyclerView;
    RequestsAdapter mRequestAdapter;
    String CHANNEL_ID = "ABC123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);

        //Intent der Login-Klasse holen
        Intent intentLogIn = getIntent();
        //Übergebene Nutzergruppe als String speichern
        //Setze static Parameter zur Überprüfung der Berechtigung
        userGruppe = LoginActivity.GRUPPE;

        userID = intentLogIn.getIntExtra("userID", 0);

        RequestsAdapter adapter = new RequestsAdapter();
        mRecyclerView = findViewById(R.id.requestList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Context context = getApplicationContext();
                        CharSequence text = "Hello toast!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast.makeText(context, text, duration).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        //Intent intent = new Intent(this, RequestWebService.class);
        //startService(intent);

        RequestData();
    }

    private void RequestData() {

        RequestWebService webService = RequestWebService.retrofit.create(RequestWebService.class);
        Call<TransportRequest[]> call = webService.requests();

        call.enqueue(new Callback<TransportRequest[]>() {
            @Override
            public void onResponse(Call<TransportRequest[]> call, Response<TransportRequest[]> response) {
                TransportRequest[] transportRequests = response.body();
                mRequestList = Arrays.asList(transportRequests);
                DisplayData();
                System.out.println(response.code());

            }

            @Override
            public void onFailure(Call<TransportRequest[]> call, Throwable t) {
                System.out.println("GEHT NICHT!");
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
        }
        return true;
    }

}
