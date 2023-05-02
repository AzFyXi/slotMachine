package main.java.SlotMachine.SpecialSymbols;

import main.java.SlotMachine.Symbol;

public class SymbolSuper extends Symbol {
    private boolean superSymbol;

    //Getters and Setters
    public boolean isSuperSymbol() {
        return superSymbol;
    }
    public void setSuperSymbol(boolean superSymbol) {
        this.superSymbol = superSymbol;
    }
}
