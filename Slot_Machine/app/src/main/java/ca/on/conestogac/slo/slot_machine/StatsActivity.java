package ca.on.conestogac.slo.slot_machine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {
    public SQLiteOpenHelper helper;
    private TextView totalCash;
    private  TextView currentCash;
    private TextView reset;
    GameActivity gameActivity = new GameActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SlotMachine slotMachine = (SlotMachine) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats2);
        getSupportActionBar().setTitle("Statistics");

      //write to statistics
        int gett = slotMachine.getTotal();
        totalCash = findViewById(R.id.totalMoney);
        currentCash = findViewById(R.id.currentCash);
        reset = findViewById(R.id.btn_reset);
        totalCash.setText("  " + String.valueOf( gett));
        int cash = GameActivity.getCurrent_status();
        currentCash.setText( String.valueOf(cash));


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SlotMachine) getApplication()).reset();
                System.out.println("Database Reset");
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean ret = true;
        switch(item.getItemId())
        {
            case android.R.id.home:
                //back button
                super.onBackPressed();
                break;
            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }
        return ret;
    }
}