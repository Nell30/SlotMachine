package ca.on.conestogac.slo.slot_machine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class TokenActivity extends AppCompatActivity {
    private int currentTokens;
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
        setContentView(R.layout.activity_token);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if(i.hasExtra("currentTokens")){
            currentTokens = extras.getInt("currentTokens");
        } else {
            currentTokens = 0;
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean ret = true;

        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();

                break;
            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }
        return ret;
    }
}