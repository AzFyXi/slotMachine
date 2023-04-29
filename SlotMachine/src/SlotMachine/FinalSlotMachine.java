package slotmachine;
import slotmachine.Column;

import java.util.*;

public class FinalSlotMachine {
    private Collection<Column> columns;
    private int numberColumns;

    public FinalSlotMachine(Collection<Column> columns, int numberColumns) {
        this.columns = columns;
        this.numberColumns = numberColumns;
    }

    public Collection<Column> getColumns() {
        return columns;
    }

    public void setColumns(Collection<Column> columns) {
        this.columns = columns;
    }

    public int getNumberColumns() {
        return numberColumns;
    }

    public void setNumberColumns(int numberColumns) {
        this.numberColumns = numberColumns;
    }


    public static Column[] getArrayColumsAndSymbol(Collection<Column> columns) {
        Column[] arrayColumns = columns.toArray(new Column[columns.size()]);
        for (Column col : arrayColumns) {
            List<Symbol> symbolList = new ArrayList<>(col.getAllSymbols());
            col.setSymbols(symbolList);
        }
        return arrayColumns;
    }

    public void printColumsConsole() {
        Column[] arrayColumns = getArrayColumsAndSymbol(columns);
        for(Column col : arrayColumns) {
            for(int i = col.getLinesNumber(); i > col.getLinesNumber()-col.getPrintNumberLine() ; i-- ) {
                System.out.print(col.getOneSymbols(i));
            }
        }
    }

    public void findWinningSymbol() {

    }
}
