package main.java.SlotMachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Column {
    private Collection<Symbol> symbols;
    private int numberColumn;
    private int linesNumber;
    private boolean generated;
    private int printNumberLine;

    //Constructor
    public Column(Collection<Symbol> symbols, int numberColumn, int linesNumber, boolean generated, int printNumberLine) {
        this.symbols = symbols;
        this.numberColumn = numberColumn;
        this.linesNumber = linesNumber;
        this.generated = generated;
        this.printNumberLine = printNumberLine;
    }
    public Column(int numberColumn, int linesNumber, boolean generated, int printNumberLine) {
        this.symbols = null;
        this.numberColumn = numberColumn;
        this.linesNumber = linesNumber;
        this.generated = generated;
        this.printNumberLine = printNumberLine;
    }
    //Getters and Setters

    public Collection<Symbol> getSymbols() {
        return symbols;
    }

    public Symbol getSymbols(int position) {
        List<Symbol> symbolsList = new ArrayList<>(symbols);

        if (position >= 0 && position < symbolsList.size()) {
            return symbolsList.get(position);
        } else {
            return null;
        }
    }

    public int getNumberColumn() {
        return numberColumn;
    }

    public int getLinesNumber() {
        return linesNumber;
    }

    public boolean isGenerated() {
        return generated;
    }

    public int getPrintNumberLine() {
        return printNumberLine;
    }

    public void setSymbols(Collection<Symbol> symbols) {
        this.symbols = symbols;
    }

    public void setNumberColumn(int numberColumn) {
        this.numberColumn = numberColumn;
    }

    public void setLinesNumber(int linesNumber) {
        this.linesNumber = linesNumber;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public void setPrintNumberLine(int printNumberLine) {
        this.printNumberLine = printNumberLine;
    }

    //toString

    @Override
    public String toString() {
        return "Column{" +
                "symbols=" + symbols +
                ", numberColumn=" + numberColumn +
                ", linesNumber=" + linesNumber +
                ", generated=" + generated +
                ", printNumberLine=" + printNumberLine +
                '}';
    }


    //Methods

    public boolean isMasterColumn() {
            return numberColumn ==1 ;
    }
    public Symbol[] getSymbolsArray() {
        Symbol[] arraySymbols = new Symbol[symbols.size()];
        symbols.toArray(arraySymbols);
        return arraySymbols;
    }

    public void generateSymbols() {
        if(isMasterColumn()) {
            for(int i = 0; i < linesNumber; i++) {

            }
        }
    }
}