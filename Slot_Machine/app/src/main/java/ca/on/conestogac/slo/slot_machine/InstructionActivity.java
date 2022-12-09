package ca.on.conestogac.slo.slot_machine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;

public class InstructionActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //get the shared preferences
        if((sharedPref.getBoolean("dark_theme", false)) == false){
            setTheme(R.style.Theme_Slot_Machine);
        }
        else{
            setTheme(R.style.Theme_dark);
        }
    }
}