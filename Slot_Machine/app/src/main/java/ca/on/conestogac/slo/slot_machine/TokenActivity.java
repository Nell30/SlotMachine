package ca.on.conestogac.slo.slot_machine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TokenActivity extends AppCompatActivity {

    private int currentTokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if(i.hasExtra("currentTokens")){
            currentTokens = extras.getInt("currentTokens");
        } else {
            currentTokens = 0;
        }

    }

    public void btnAddFiveToken(View button){
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("playerTokens", 5 + currentTokens);
        startActivity(i);
    }

    public void btnAddTenToken(View button){
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("playerTokens", 10 + currentTokens);
        startActivity(i);
    }
}