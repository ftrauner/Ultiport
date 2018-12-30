package spengergasse.at.ultiport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import spengergasse.at.ultiport.adapter.RequestsAdapter;
import spengergasse.at.ultiport.entities.TransportRequest;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvRequests = findViewById(R.id.requestList);

        Intent intent = getIntent();
        TransportRequest request = intent.getParcelableExtra("requestObject");
        TransportRequest.requests.add(request);

        RequestsAdapter requestsAdapter = new RequestsAdapter(TransportRequest.requests);
        rvRequests.setAdapter(requestsAdapter);
        rvRequests.setLayoutManager(new LinearLayoutManager(this));



    }

    public void createReqClick(View view){
        Intent intent = new Intent(this,AddRequestActivity.class);
        startActivity(intent);
    }
}
