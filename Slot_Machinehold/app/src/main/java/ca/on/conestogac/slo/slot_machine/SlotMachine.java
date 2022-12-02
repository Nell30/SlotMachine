package ca.on.conestogac.slo.slot_machine;

import java.util.ArrayList;

public class SlotMachine {
    private int numberOfWheels;
    private ArrayList<Wheel> slots;
    private int playerFunds;

    public SlotMachine(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
        this.slots = new ArrayList<>();
        this.playerFunds = 0;
        generateWheels();
    }

    private void generateWheels(){
        for(int i = 0; i <numberOfWheels; i++){
            Wheel wheel = new Wheel();
            this.slots.add(wheel);
        }
    }

    public ArrayList<Symbols> spin(){

        ArrayList<Symbols> line = new ArrayList<>();
        for(Wheel wheel : slots){
            line.add(wheel.spin());
        }
        return line;
    }
    public String getSymbolImage(Symbols symbol){
        return symbol.getImageName();
    }

    public ArrayList<String> getLineImages(ArrayList<Symbols> line) {
        ArrayList<String> images = new ArrayList<>();
        for (Symbols symbol : line) {
            String image = getSymbolImage(symbol);
            images.add(image);
        }
        return images;
    }

    public int getWinValue(ArrayList<Symbols> line){
        return line.get(0).getValue();
    }

    public boolean checkWin(ArrayList<Symbols> line){
        int counter = 0;
        for(Symbols symbol : line){
            if(symbol.equals(line.get(0))){
                counter++;
            }
        }
        return counter == line.size();
    }
}
