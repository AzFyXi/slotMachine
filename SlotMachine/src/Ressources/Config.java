package ressources;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

import SlotMachine.Column;
import SlotMachine.Symbol;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

public class Config {

/*    public static void init() {
        JSONObject parsedSymbols = parseSymbols();
        assert parsedSymbols != null;

        Collection<Symbol> symbols = createSymbolsCollection(parsedSymbols);
        System.out.println(symbols);
    }

    public static JSONObject parseSymbols(){

        JSONParser parser = new JSONParser();

        try {
            // Read the JSON file
            Object symbols = parser.parse(new FileReader("C:\\Users\\rmassiet\\Desktop\\cours\\ESGI2\\introductionJava\\SlotMachine\\SlotMachine\\src\\ressources\\symbols.json"));

            // Return the parsed JSON
            return (JSONObject) symbols;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<Symbol> createSymbolsCollection(JSONObject parsedSymbols){
        Collection<Symbol> symbols = new ArrayList<>();
        for (Object symbol : (Collection<Object>) parsedSymbols.get("symbols")) {
            System.out.println(symbol);

        }

        return symbols;

    }*/
}
