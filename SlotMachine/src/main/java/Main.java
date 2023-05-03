

import SlotMachine.*;
import User.User;

import java.util.Collection;

import static SlotMachine.SlotMachine.*;

public class Main {
    public static void main(String[] args) {
        Collection<Symbol> symbols = getSymbolsCollection();

        Collection<Column> columns = createColumnsCollection(3);

        User mainUser = new User("First User", 250000);

        for (Column column: columns){
            column.generateSymbols(symbols, 30);
            System.out.println(column.toString());
        }

        SlotMachineGUI.createAndShowGUI(mainUser, columns);
        
    }
}