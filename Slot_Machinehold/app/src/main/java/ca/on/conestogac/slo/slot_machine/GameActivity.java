package ca.on.conestogac.slo.slot_machine;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ImageView symbol1, symbol2, symbol3;
    private ImageButton spinButton;
    private AnimationDrawable animation1, animation2, animation3;
    private ImageView animation1Image, animation2Image, animation3Image;
    public Symbols symbols;
    private SlotMachine slotMachine;
    private ImageView winnerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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
    }

    public void onSpinButtonClicked(View button){
        spin();
        startSlotAnimations();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                clearSlotAnimations();
            }
        }, 1300);
    }

    public ArrayList<Symbols> spin(){
        final ArrayList<Symbols> newLine = slotMachine.spin();
        final int value = slotMachine.getWinValue(newLine);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updateCurrentLine(newLine);
                if (slotMachine.checkWin(newLine)) {
                    win();
                }
            }
        }, 1300);
        return newLine;
    }

    public void win(){
        winnerImage.setVisibility(View.VISIBLE);
        spinButton.setVisibility(View.INVISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                winnerImage.setVisibility(View.INVISIBLE);
                spinButton.setVisibility(View.VISIBLE);
            }
        }, 2000);
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