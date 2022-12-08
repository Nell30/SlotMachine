package ca.on.conestogac.slo.slot_machine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Slot Machine Game");
    }

    public void onClickNewGameButton(View button){
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

    protected void onDestroy(){
        startService(new Intent(getApplicationContext(),NotificationService.class));
        super.onDestroy();
    }

}