package ca.on.conestogac.slo.slot_machine;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SlotMachine extends Application {
    //declare variables
    private static final String Db_Name = "db_slotMachine";
    private static final  int Db_Version = 1;
    public SQLiteOpenHelper helper;
    private int numberOfWheels;
    private ArrayList<Wheel> slots;
    private int playerFunds;

    //oncreate function
    @Override
    public void onCreate() {

        helper = new SQLiteOpenHelper(this,Db_Name,null,Db_Version){
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                //create the database
                String CreateTableStatement = "CREATE TABLE IF NOT EXISTS tbl_stats (time INTEGER, cash_price INTEGER)";
                sqLiteDatabase.execSQL(CreateTableStatement);
            }
            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        super.onCreate();
    }
    public SlotMachine(){

    }
    public SlotMachine(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
        this.slots = new ArrayList<>();
        this.playerFunds = 0;
        generateWheels();
    }

    //add statistics
    public void addStatus(){
        SQLiteDatabase database = helper.getReadableDatabase();
        //default status values = 0
        int stat = 0;
        //get values from game activity
        stat = GameActivity.getUser_status();
        //insert the values and the timestamp to database
        String insertInto = "INSERT INTO tbl_stats (time, cash_price)" +
                " VALUES (" + Math.round(System.currentTimeMillis() / 1000)  +", "+ stat + ")" ;
        database.execSQL(insertInto);
    }
     public int getTotal() {
        int total;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(cash_price) FROM tbl_stats", null);
        cursor.moveToFirst();
        total = cursor.getInt(0);
        cursor.close();
        return(total);
    }
    public void reset() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_stats");
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
