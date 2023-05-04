

import SlotMachine.*;
import User.User;

import java.util.Collection;

import static SlotMachine.SlotMachine.*;

public class Main {
    public static void main(String[] args) {
        Collection<Symbol> symbols = getSymbolsCollection();

        Collection<Column> columns = createColumnsCollection(3, symbols);

        User mainUser = new User("First User", 250000);

        /*for (Column column: columns){
            if(column.getNumberColumn() != 5) column.generateSymbols(symbols, 30);
            else column.generateSymbols(symbols, 41);
            //System.out.println(column.toString());
            System.out.println(column.getSymbol(29).getId() + "id Symbole");
        }*/

        SlotMachineGUI.createAndShowGUI(mainUser, columns, symbols);
        
    }
}