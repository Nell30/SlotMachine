package ca.on.conestogac.slo.slot_machine;

public enum Symbols {
    LEOPARD(1, "@drawable/leopard"),
    RHINO(1, "@drawable/rhino"),
    LION(5, "@drawable/lion");

    public String imageName;
    public int value;

    Symbols(int value, String imageName){
        this.imageName = imageName;
        this.value = value;
    }

    public String getImageName() {
        return imageName;
    }

    public int getValue() {
        return value;
    }
}
