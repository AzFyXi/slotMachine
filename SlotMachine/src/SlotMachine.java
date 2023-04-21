import ressources.Config;
import SlotMachine.*;

import java.util.Collection;

public class SlotMachine {
    public static void main(String[] args) {
        Collection<Symbol> symbols = FinalSlotMachine.getSymbolsCollection();
        System.out.println(symbols);
    }
}