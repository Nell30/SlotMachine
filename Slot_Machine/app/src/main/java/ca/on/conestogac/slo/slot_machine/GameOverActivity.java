package ca.on.conestogac.slo.slot_machine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }
    public void onAddTokenClicked(View button){
        Intent i = new Intent(this, TokenActivity.class);
        startActivity(i);
    }

    public void onHomeClicked(View button){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}