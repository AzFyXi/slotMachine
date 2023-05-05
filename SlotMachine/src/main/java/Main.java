import SlotMachine.*;
import User.User;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

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

        try {
            URL url = Main.class.getResource("/images/background_music.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(url.toURI())));

            // play always
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | URISyntaxException e) {
            e.printStackTrace();
        }
        
    }
}