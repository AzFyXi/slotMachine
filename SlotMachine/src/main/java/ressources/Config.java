package ressources;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Map;

import SlotMachine.Symbol;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Config {

    public static void init() {

        JSONObject parsedSymbols = parseSymbols();
        assert parsedSymbols != null;

        Collection<Symbol> symbols = createSymbolsCollection(parsedSymbols);
        System.out.println(symbols);
    }

    public static JSONObject parseSymbols() {
        JSONParser parser = new JSONParser();

        try {
            // Read the JSON file
            InputStream is = Config.class.getResourceAsStream("/symbols.json");
            if (is != null) {
                Object symbols = parser.parse(new InputStreamReader(is, StandardCharsets.UTF_8));
                // Return the parsed JSON
                return (JSONObject) symbols;
            } else {
                System.out.println("Input stream is null.");
            }
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
        String image_url = (String) jsonObj.get("image_url");
        String name = (String) jsonObj.get("name");

        Symbol symbolObject = new Symbol(id, image_url, name);
        symbolObject.setImage(image_url);
        symbolObject.setAlreadyWon(false);
        symbolObject.setName(name);

        return symbolObject;
    }

}