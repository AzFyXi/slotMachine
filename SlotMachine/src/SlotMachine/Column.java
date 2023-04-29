package slotmachine;
import slotmachine.Symbol;
//import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

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
    //Getters and Setters

    public Collection<Symbol> getAllSymbols() {
        return symbols;
    }

    public Symbol getOneSymbols(int position) {
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