package ressources;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

import java.io.File;
import java.util.Map;

import SlotMachine.Symbol;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Config {

    public static void init() {

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
            JSONObject jsonObj = new JSONObject((Map) symbol);

            Symbol symbolObject = getSymbolObjectFromJSON(jsonObj);

            symbols.add(symbolObject);
        }
        return symbols;
    }

    public static Symbol getSymbolObjectFromJSON(JSONObject jsonObj){
        int id = ((Long) jsonObj.get("id")).intValue(); // Convertir Long en int
        String emoji = (String) jsonObj.get("emoji");
        String name = (String) jsonObj.get("name");

        Symbol symbolObject = new Symbol(id, emoji, name);
        symbolObject.setImage(emoji);
        symbolObject.setAlreadyWinned(false);
        symbolObject.setName(name);

        return symbolObject;
    }

}
