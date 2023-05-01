import slotmachine.*;
import User.User;
import ressources.*;

import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        //Collection<Symbol> symbols = SlotMachine.getSymbolsCollection();
        //System.out.println(symbols);
        Collection<Column> columns = slotmachine.SlotMachine.createColumnsCollection(3);
        User mainUser = new User("First User", 250000);

    }
}