package SlotMachine;
import SlotMachine.Symbol;
import org.json.JSONObject;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;

public class Column {
    private Collection<Symbol> symbols;
    private boolean masterColumn;
    private boolean generated;
    private int linesNumber;

    public Column(Collection<Symbol> symbols, boolean masterColumn, boolean generated, int linesNumber) {
        this.symbols = symbols;
        this.masterColumn = masterColumn;
        this.generated = generated;
        this.linesNumber = linesNumber;
    }


//Getters and Setters

    public void setMasterColumn(boolean masterColumn) {
        this.masterColumn = masterColumn;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public int getLinesNumber() {
        return linesNumber;
    }

    public void setLinesNumber(int linesNumber) {
        this.linesNumber = linesNumber;
    }

//toString

    //Methods
    public boolean isGenerated() {
        return generated;
    }

    public boolean isMasterColumn() {
        return masterColumn;
    }

    @Override
    public String toString() {
        return "Column{" +
                "symbols=" + symbols +
                ", masterColumn=" + masterColumn +
                ", generated=" + generated +
                ", linesNumber=" + linesNumber +
                '}';
    }

    public String getSymbols(){
        return symbols.toString();
    }
}