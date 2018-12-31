package spengergasse.at.ultiport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import spengergasse.at.ultiport.adapter.RequestsAdapter;
import spengergasse.at.ultiport.entities.TransportRequest;

public class MainActivity extends AppCompatActivity {

    private static String userGruppe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);


        //Requestlistte setzen
        RecyclerView rvRequests = findViewById(R.id.requestList);

        //Intent von der AddRequestActivity holen
        Intent intent = getIntent();
        //Übergebenen Request als neuen Request speichern
        TransportRequest request = intent.getParcelableExtra("requestObject");
        //Neuen Request zu statischer RequestListe der Request-Klasse hinzufügen
        TransportRequest.requests.add(request);

        RequestsAdapter requestsAdapter = new RequestsAdapter(TransportRequest.requests);
        rvRequests.setAdapter(requestsAdapter);
        rvRequests.setLayoutManager(new LinearLayoutManager(this));

        //Intent der Login-Klasse holen
        Intent intentLogIn = getIntent();
        //Übergebene Nutzergruppe als String speichern
        //Setze static Parameter zur Überprüfung der Berechtigung
        userGruppe = intentLogIn.getStringExtra("userGruppe");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //
        switch (item.getItemId()) {
            case R.id.action_add_request: {
                if (userGruppe.equals("1") || userGruppe.equals("2")) {
                    Intent intent = new Intent(this, AddRequestActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(R.id.main_layout), R.string.main_keine_berechtigung, Snackbar.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.action_administration: {
                if (userGruppe.equals("1")) {
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
