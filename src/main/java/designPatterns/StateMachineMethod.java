package designPatterns;

interface VendingMachineState{
    void insertCoin(VendingMachine machine);
    void selectProduct(VendingMachine machine);
    void dispense(VendingMachine machine);
}

class NoCoinState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Coin inserted");
        machine.setState(new HasCoinState());
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Insert coin first");
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Insert coin first");
    }
}

class HasCoinState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Coin already inserted");
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Product Selected");
        machine.setState(new DispenseState());
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Select Product first");
    }
}

class DispenseState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Please wait, dispensing");
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Please wait, dispensing");
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Dispensing product");
        machine.setState(new NoCoinState());
    }
}

class VendingMachine{
    private VendingMachineState currentState;

    public VendingMachine(){
        currentState = new NoCoinState();
    }

    public void insertCoin(){
        currentState.insertCoin(this);
    }

    // passing "this" bcoz currentState maybe changed by setState

    public void selectProduct(){
        currentState.selectProduct(this);
    }

    public void dispense(){
        currentState.dispense(this);
    }

    public void setState(VendingMachineState state){
        this.currentState = state;
    }
}

// runner
public class StateMachineMethod {
    public static void main(String args[]){
        VendingMachine machine = new VendingMachine();

        machine.selectProduct(); // "Insert coin first"
        machine.insertCoin(); // "Coin inserted"
        machine.selectProduct(); // "Product selected"
        machine.dispense(); // "Dispensing product"
    }

}
