package spengergasse.at.ultiport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import spengergasse.at.ultiport.adapter.RecyclerItemClickListener;
import spengergasse.at.ultiport.adapter.UserAdapter;
import spengergasse.at.ultiport.entities.User;


public class AdministrationActivity extends AppCompatActivity {

    private static final String URL_PRODUCTS = "http://ultiport.htl5.org/RetrieveUserData.php";

    Context ctx;

    RecyclerView bView;
    private List<User> userList;
    private RecyclerView recyclerView;
    private UserAdapter uAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        Toolbar administration_toolbar = findViewById(R.id.administration_toolbar);
        setSupportActionBar(administration_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        bView = findViewById(R.id.userList);
        bView.setHasFixedSize(true);
        bView.setLayoutManager(new LinearLayoutManager(this));


        userList = new ArrayList<>();

        getUserData();

        bView.addOnItemTouchListener(new RecyclerItemClickListener(this, bView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
            @Override
            public void onLongItemClick(View view, int position) {
                PopupMenu popupMenu = new PopupMenu(AdministrationActivity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.user_popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.item_edit_user: {
                                Intent intent = new Intent(AdministrationActivity.this, EditUserActivity.class);
                                startActivity(intent);
                                break;
                            }
                            case R.id.item_delete_user: {
                                Intent intent = new Intent(AdministrationActivity.this, DeleteUserActivity.class);
                                startActivity(intent);
                                break;
                            }
                        }
                        return false;
                    }
                });
            }
        }));

        //UserAdapter mAdapter = new UserAdapter()
        //UserAdapter userAdapter = new UserAdapter(***userliste***);
        //bView.setAdapter(userAdapter);
        //bView.setLayoutManager(new LinearLayoutManager(this));
    }



    private void getUserData(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject user = array.getJSONObject(i);

                                //adding the product to product list
                                userList.add(new User(
                                        user.getString("e_vorname"),
                                        user.getString("e_name"),
                                        user.getString("e_g_gruppe"),
                                        user.getString("e_id")));
                            }

                            //creating adapter object and setting it to recyclerview
                            UserAdapter adapter = new UserAdapter(AdministrationActivity.this,userList);
                            TextView textView = findViewById(R.id.user_gruppe);
                            bView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
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
            /*
            //Benutzer bearbeiten wird geklickt
            case R.id.action_edit_user: {
                Intent intent = new Intent(this, EditUserActivity.class);
                startActivity(intent);
                break;
            }
            //Benutzer löschen wird geklickt
            case R.id.action_delete_user: {
                Intent intent = new Intent(this, DeleteUserActivity.class);
                startActivity(intent);
                break;
            }
            */
            //Zurück-Pfeil wird geklickt
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.user_aktualisieren:{
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
            }
        }
        return true;
    }


}
