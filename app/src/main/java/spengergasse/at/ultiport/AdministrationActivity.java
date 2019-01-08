package spengergasse.at.ultiport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class AdministrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        Toolbar administration_toolbar = findViewById(R.id.administration_toolbar);
        setSupportActionBar(administration_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView bView = findViewById(R.id.userList);
        //TODO: Hier Liste von Usern von DB einfügen
        //UserAdapter userAdapter = new UserAdapter(***userliste***);
        //bView.setAdapter(userAdapter);
        //bView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Methode zum Erstellen des Menüs wie definiert in res/menu/toolbar_administration.xml
     * @param menu Menü
     * @return wahr
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_administration, menu);
        return true;
    }

    /**
     * Methode für Menüpunkte
     * @param item Geklickter Menüpunkt
     * @return wahr
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //Benutzer hinzufügen wird geklickt
            case R.id.action_add_user: {
                Intent intent = new Intent(this, AddUserActivity.class);
                startActivity(intent);
                break;
            }
            //Benutzer bearbeiten wird geklickt
            case R.id.action_edit_user: {
                Intent intent = new Intent(this, EditUserActivity.class);
                startActivity(intent);
                break;
            }
            //Benutzer löschen wird geklickt
            case R.id.action_delete_user: {
                //TODO: Zu entfernenden Benutzer aus Liste wählen, DELETE beim Server
                break;
            }
            //Zurück-Pfeil wird geklickt
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return true;
    }
}
