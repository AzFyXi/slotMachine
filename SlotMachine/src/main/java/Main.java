

import SlotMachine.*;
import User.User;

import java.util.Collection;

import static SlotMachine.SlotMachine.*;

public class Main {
    public static void main(String[] args) {
        Collection<Symbol> symbols = getSymbolsCollection();
        System.out.println(symbols);
        Collection<Column> columns = createColumnsCollection(3);
        User mainUser = new User("First User", 250000);


        SlotMachineGUI.createAndShowGUI();
        
    }
}