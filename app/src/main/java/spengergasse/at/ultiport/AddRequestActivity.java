package spengergasse.at.ultiport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import spengergasse.at.ultiport.entities.TransportRequest;

public class AddRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
    }

    public void addReqClick(View view){

        Date date = new Date();
        EditText requestText = this.findViewById(R.id.requestText);
        TransportRequest request = new TransportRequest(requestText.getText().toString(),date);

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("requestObject", request);
        startActivity(intent);
    }
}
