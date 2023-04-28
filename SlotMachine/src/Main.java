import SlotMachine.*;

import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        Collection<Symbol> symbols = SlotMachine.getSymbolsCollection();
        System.out.println(symbols);
    }
}