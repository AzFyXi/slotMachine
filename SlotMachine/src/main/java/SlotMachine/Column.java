package SlotMachine;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
        this.symbols = new ArrayList<>();
        this.numberColumn = numberColumn;
        this.linesNumber = linesNumber;
        this.generated = generated;
        this.printNumberLine = printNumberLine;
    }


    //Getters and Setters
    public Collection<Symbol> getSymbol() {
        return symbols;
    }

    public Symbol getSymbol(int position) {
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

    @Override
    public String toString() {
        return "Column{" +
                "symbols=" + symbols.toString() +
                ", numberColumn=" + numberColumn +
                ", linesNumber=" + linesNumber +
                ", generated=" + generated +
                ", printNumberLine=" + printNumberLine +
                '}';
    }

    //Methods

    public boolean isMasterColumn() {
            return numberColumn == 1 ;
    }

    public Symbol[] getSymbolsArray() {
        Symbol[] arraySymbols = new Symbol[symbols.size()];
        symbols.toArray(arraySymbols);
        return arraySymbols;
    }
    public void clearSymbols() {
        this.symbols.clear();
    }
    public Collection<Symbol> generateSymbols(Collection<Symbol> symbols, int symbolsNumber) {
        Collection<Symbol> generatedSymbols = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < symbolsNumber; i++) {
            int randomNumber = random.nextInt(symbols.size());
            Symbol randomSymbol = symbols.stream().skip(randomNumber).findFirst().orElse(null);
            generatedSymbols.add(randomSymbol);
        }

        return generatedSymbols;
    }

    /*public void generateSymbols(Collection<Symbol> symbols, int symbolsNumber) {
        if (symbols instanceof List) {
            List<Symbol> symbolsList = (List<Symbol>) symbols;
            Random random = new Random();

            for (int i = 0; i < symbolsNumber; i++) {
                int randomNumber = random.nextInt(symbolsList.size());
                Symbol randomSymbol = symbolsList.get(randomNumber);
                this.symbols.add(randomSymbol);
            }
        } else {
            System.out.println("The collection does not support index-based access");
        }
    }*/



    public void generateSymbols(ImageIcon[] images, int symbolsNumber) {
        /*this.symbols.clear();
        Random random = new Random();
        List<Symbol> symbolsList = (List<Symbol>) symbols;

        for (int i = 0; i < symbolsNumber; i++) {
            int randomNumber = random.nextInt(images.length);
            //ImageIcon randomImage = images[randomNumber];
            Symbol randomSymbol = symbolsList.get(randomNumber);
            this.symbols.add(randomSymbol);
        }*/
    }

}