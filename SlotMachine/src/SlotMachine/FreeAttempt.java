package slotmachine;

public class FreeAttempt {
    private int multiplier;
    private int rowRemaining;

    public boolean isRemainFreeAttempt(){
        return rowRemaining > 0;
    }
}