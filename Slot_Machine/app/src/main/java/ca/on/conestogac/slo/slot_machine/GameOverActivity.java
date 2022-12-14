package ca.on.conestogac.slo.slot_machine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class GameOverActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //get the shared preferences
        if((sharedPref.getBoolean("dark_theme", false)) == false){
            setTheme(R.style.Theme_Slot_Machine);
        }
        else{
            setTheme(R.style.Theme_dark);
        }
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