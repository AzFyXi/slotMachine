package SlotMachine.SpecialSymbols;

import SlotMachine.Symbol;

public class SymbolFree extends Symbol {
    private boolean freeSymbol;

    //Getters and Setters
    public boolean isFreeSymbol() {
        return freeSymbol;
    }

    public void setFreeSymbol(boolean free) {
        this.freeSymbol = free;
    }
}
