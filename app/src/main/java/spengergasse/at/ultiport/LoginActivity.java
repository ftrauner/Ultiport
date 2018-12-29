package spengergasse.at.ultiport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import spengergasse.at.ultiport.restful.ActivityDataSource;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editBenutzer= (EditText) findViewById(R.id.nameText);

        final EditText editPasswort= (EditText) findViewById(R.id.passwortText);

        Button lesenBen = (Button) findViewById(R.id.button);
        lesenBen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActivityDataSource(editBenutzer).execute("someParams");
                new ActivityDataSource(editPasswort).execute("someParams");
            }
        });
    }


    }



