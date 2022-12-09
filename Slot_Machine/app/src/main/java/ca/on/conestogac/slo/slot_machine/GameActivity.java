package ca.on.conestogac.slo.slot_machine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView cash, token;
    private ImageView symbol1, symbol2, symbol3;
    private ImageButton spinButton;
    private AnimationDrawable animation1, animation2, animation3;
    private ImageView animation1Image, animation2Image, animation3Image;
    public Symbols symbols;
    public int cash_won = 0;
    public static int user_status = 0;
    public static int scash_won = 0;
    private int user_won = 0;
    private SlotMachine slotMachine;
    private ImageView winnerImage;
    private SlotMachine database;
    private SharedPref pref;
    private Integer startToken;
    private MediaPlayer winSound;
    private int playerTokens;
    private MediaPlayer rattleSound;
    private MediaPlayer loseSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setTitle("Slot Machine Game");

        cash = findViewById(R.id.playerFundsText);
        cash.setText("0"); //set the default cash amount
        symbol1 = findViewById(R.id.imageSymbol1);
        symbol2 = findViewById(R.id.imageSymbol2);
        symbol3 = findViewById(R.id.imageSymbol3);
        spinButton = findViewById(R.id.spinButton);
        slotMachine = new SlotMachine(3);
        animation1Image = findViewById(R.id.animation1);
        animation1Image.setBackgroundResource(R.drawable.wheel1animation);
        animation1 = (AnimationDrawable) animation1Image.getBackground();
        winnerImage = findViewById(R.id.winnerImage);
        animation2Image = findViewById(R.id.animation2);
        animation2Image.setBackgroundResource(R.drawable.wheel2animation);
        animation2 = (AnimationDrawable) animation2Image.getBackground();

        animation3Image = findViewById(R.id.animation3);
        animation3Image.setBackgroundResource(R.drawable.wheel3animation);
        animation3 = (AnimationDrawable) animation3Image.getBackground();
        database = ((SlotMachine) getApplication());
        pref = new SharedPref(this);
        token = findViewById(R.id.playerTokenText);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        startToken = extras.getInt("playerTokens");
        slotMachine.setPlayerTokens(startToken);
        Integer playerToken = slotMachine.checkPlayerTokens();

        token.setText(playerToken.toString());
        winSound = MediaPlayer.create(getApplicationContext(), R.raw.winsound);
        rattleSound = MediaPlayer.create(getApplicationContext(), R.raw.rattle);
        loseSound = MediaPlayer.create(getApplicationContext(), R.raw.lose);
    }
    //option menu at the top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    //option menu selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean ret = true;
        switch(item.getItemId())
        {
            case R.id.settings:
                //settings item
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.statistics:
                //statistics item
                startActivity(new Intent(getApplicationContext(), StatsActivity.class));
                break;
            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }
        return ret;
    }

    //clicking the spin button
    public void onSpinButtonClicked(View button){
        if(checkPlayerBust()){
            gameOver();
        }else {
            spin();
            startSlotAnimations();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    clearSlotAnimations();
                }
            }, 1300);
            //add to database
            database.addStatus();
        }
    }

    public ArrayList<Symbols> spin(){
        final ArrayList<Symbols> newLine = slotMachine.spin();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updatePlayerToken();
                user_won = 0;
                updateCurrentLine(newLine);
                //if the user won run the win() function
                if (slotMachine.checkWin(newLine)) {
                    win();
                     user_won = 100;
                }else {
                    loseSound.start();
                }
                //set static status to 100 or 0 after every game
                user_status = user_won;
            }
        }, 1300);
        return newLine;
    }

    //class to get user Status for this session
    public static int getUser_status(){
        return user_status;
    }

    //get users current cash
    public static  int getCurrent_status(){return scash_won;}

    //function for round win
    public void win(){
        //show the winner image
        winnerImage.setVisibility(View.VISIBLE);
        spinButton.setVisibility(View.INVISIBLE);
        winSound.start();
        //add 100$ when the user win
        cash_won = cash_won + 100;
        cash.setText(Integer.toString(cash_won));
        updatePlayerToken();
        if(pref.getBoolean("save_game")){
            int cash = pref.getInt("cash_won");
            cash += 100;
            pref.setInt("cash_won", cash);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                winnerImage.setVisibility(View.INVISIBLE);
                spinButton.setVisibility(View.VISIBLE);
                slotMachine.plusPlayerTokens(+3);
                updatePlayerToken();
            }
        }, 2000);
        scash_won = cash_won;
    }
    public void plusPlayerTokens(int amount){
        this.playerTokens += amount;
    }

    public void updatePlayerToken(){
        Integer newPlayerTokens = slotMachine.checkPlayerTokens();
        token.setText(newPlayerTokens.toString());
    }

    public boolean checkPlayerBust(){
        int token = slotMachine.checkPlayerTokens();
        return token <= 0;
    }

    public void gameOver(){
            Intent i = new Intent(this, GameOverActivity.class);
            startActivity(i);
    }

    //randomize the symbol images
    public void updateCurrentLine(ArrayList<Symbols> newLine){
        ArrayList<String> lineImages = slotMachine.getLineImages(newLine);
        String image1 = lineImages.get(0);
        String image2 = lineImages.get(1);
        String image3 = lineImages.get(2);
        int idImage1 = getResources().getIdentifier(image1, "drawable", getPackageName());
        int idImage2 = getResources().getIdentifier(image2, "drawable", getPackageName());
        int idImage3 = getResources().getIdentifier(image3, "drawable", getPackageName());
        symbol1.setImageResource(idImage1);
        symbol2.setImageResource(idImage2);
        symbol3.setImageResource(idImage3);
    }

    public void startSlotAnimations(){
        rattleSound.start();
        startAnimation1();
        startAnimation2();
        startAnimation3();
        spinButton.setVisibility(View.INVISIBLE);
    }

    public void clearSlotAnimations(){
        clearAnimation1View();
        clearAnimation2View();
        clearAnimation3View();
        spinButton.setVisibility(View.VISIBLE);
    }

    public void startAnimation1(){
            animation1Image.setVisibility(View.VISIBLE);
            animation1.start();
    }

    public void startAnimation2(){
            animation2Image.setVisibility(View.VISIBLE);
            animation2.start();
    }

    public void startAnimation3(){
            animation3Image.setVisibility(View.VISIBLE);
            animation3.start();
    }

    public void clearAnimation1View(){
        animation1Image.setVisibility(View.INVISIBLE);
    }
    public void clearAnimation2View(){
        animation2Image.setVisibility(View.INVISIBLE);
    }
    public void clearAnimation3View(){
        animation3Image.setVisibility(View.INVISIBLE);
    }


}