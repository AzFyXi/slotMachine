package main.java.SlotMachine.SpecialSymbols;

import main.java.SlotMachine.Symbol;

public class SymbolBonus extends Symbol {
    private boolean bonusSymbol;

    //Getters and Setters
    public boolean isBonusSymbol() {
        return bonusSymbol;
    }

    public void setBonusSymbol(boolean bonus) {
        this.bonusSymbol = bonus;
    }
}
