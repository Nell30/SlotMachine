package ca.on.conestogac.slo.slot_machine;

public enum Symbols {
    LEOPARD("@drawable/leopard"),
    RHINO("@drawable/rhino"),
    LION("@drawable/lion");

    public String imageName;
    public int value;

    Symbols(String imageName){
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public int getValue() {
        return value;
    }
}
