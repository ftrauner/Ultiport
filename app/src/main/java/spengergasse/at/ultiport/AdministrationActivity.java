package spengergasse.at.ultiport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AdministrationActivity extends AppCompatActivity {

    private RecyclerView bView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrate);
        bView = findViewById(R.id.userList);
    }
    public void onEditUserClick(View v) {
        Intent intent = new Intent(this,EditUserActivity.class);
        startActivity(intent);
    }
    public void onAddUserClick(View v) {
        Intent intent = new Intent(this,AddUserActivity.class);
        startActivity(intent);
    }

}
