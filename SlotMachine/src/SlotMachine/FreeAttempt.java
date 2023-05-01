package slotmachine;

public class FreeAttempt {
    private int multiplier;
    private int rowRemaining;

    //Constructor
    public FreeAttempt(int multiplier, int rowRemaining) {
        this.multiplier = multiplier;
        this.rowRemaining = rowRemaining;
    }

    //Getters and Setters
    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getRowRemaining() {
        return rowRemaining;
    }

    public void setRowRemaining(int rowRemaining) {
        this.rowRemaining = rowRemaining;
    }

    //Method
    public boolean isRemainFreeAttempt(){
        return rowRemaining > 0;
    }

    public void useRow() {
        this.rowRemaining--;
    }
}