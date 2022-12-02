package ca.on.conestogac.slo.slot_machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Wheel {
    private ArrayList<Symbols> allSymbols;
    private Symbols currentSymbol;

    public Wheel(){
        this.allSymbols = new ArrayList<>();
        generateSymbols();
        this.currentSymbol = getRandomSymbol();
    }
    private void generateSymbols(){
        Collections.addAll(this.allSymbols, Symbols.values());
    }

    public void setCurrentSymbol(Symbols currentSymbol) {
        this.currentSymbol = currentSymbol;
    }

    public Symbols getSymbolAtIndex(int index){
        return this.allSymbols.get(index);
    }

    public Symbols getRandomSymbol(){
        int randomIndex = randomInt(countSymbols());
        return getSymbolAtIndex(randomIndex);
    }

    public int countSymbols(){
        return this.allSymbols.size();
    }

    public int randomInt(int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }

    public Symbols spin(){

            Symbols newSymbol = getRandomSymbol();
            setCurrentSymbol(newSymbol);
            return newSymbol;

    }

}
