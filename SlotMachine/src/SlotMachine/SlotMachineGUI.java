package SlotMachine;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SlotMachineGUI {
    private static final String[] IMAGE_FILES = {
            "bell.png", "cherry.png", "lemon.png", "plum.png", "redseven.png", "watermelon.png"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static ImageIcon[] loadImages(int width, int height) {
        ImageIcon[] images = new ImageIcon[IMAGE_FILES.length];
        for (int i = 0; i < IMAGE_FILES.length; i++) {
            ImageIcon originalImage = new ImageIcon("/home/lucie/Bureau/PROJECT/SlotMachine/SlotMachine/src/ressources/assets/images/" + IMAGE_FILES[i]);
            Image resizedImage = originalImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            images[i] = new ImageIcon(resizedImage);
        }
        return images;
    }

    private static void createAndShowGUI() {
        // Chargez les images avec les dimensions souhaitées
        int imageWidth = 105;
        int imageHeight = 105;
        ImageIcon[] images = loadImages(imageWidth, imageHeight);

        // Création de la fenêtre principale
        JFrame frame = new JFrame("Slot Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 700);

        // Ajout du panneau principal avec un fond d'image
        JPanel mainPanel = new JPanel() {
            ImageIcon imageIcon = new ImageIcon("/home/lucie/Bureau/PROJECT/SlotMachine/SlotMachine/src/ressources/assets/images/slotMachine.jpg");
            Image image = imageIcon.getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);

        // Créez un tableau 2D de JLabel pour stocker les images
        JLabel[][] imageLabels = new JLabel[5][3];
        GridBagConstraints constraints = new GridBagConstraints();
        Random random = new Random();

        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 3; row++) {
                ImageIcon icon = images[random.nextInt(images.length)];
                imageLabels[col][row] = new JLabel(icon);
        
                constraints.gridx = col;
                constraints.gridy = row;
        
                // Alignement indépendant pour chaque ligne
                if (row == 0) {
                    constraints.anchor = GridBagConstraints.LINE_START; // Aligner au début de la ligne
                    constraints.insets = new Insets(60, 30, 30, 10); // Ajouter une marge supérieure de 60 pixels, une marge gauche de 30 pixels, une marge droite de 10 pixels, et une marge inférieure de 30 pixels
                } else if (row == 1) {
                    constraints.anchor = GridBagConstraints.CENTER; // Aligner au centre
                    constraints.insets = new Insets(20, 20, 20, 10); // Ajouter une marge supérieure de 20 pixels, une marge gauche de 20 pixels, une marge droite de 10 pixels, et une marge inférieure de 20 pixels
                } else {
                    constraints.anchor = GridBagConstraints.LINE_END; // Aligner à la fin de la ligne
                    constraints.insets = new Insets(0, 10, 10, 10); // Ajouter une marge gauche de 10 pixels, une marge droite de 10 pixels, et une marge inférieure de 10 pixels
                }
                constraints.gridy += 1; // Décaler la position y de 1 pour chaque ligne
        
                // Ajuster la marge droite pour la dernière colonne
                if (col == 4) {
                    constraints.insets.right = 15; // Ajouter une marge droite de 15 pixels
                } else {
                    constraints.insets.right = 0; // Pas de marge droite pour les autres colonnes
                }
        
                // Ajuster la marge gauche pour chaque colonne
                if (col == 0) {
                    constraints.insets.left = 30; // Ajouter une marge gauche de 30 pixels pour la première colonne
                } else if (col == 1) {
                    constraints.insets.left = 20; // Ajouter une marge gauche de 20 pixels pour la deuxième colonne
                } else if (col == 2) {
                    constraints.insets.left = 10; // Ajouter une marge gauche de 10 pixels pour la troisième colonne
                } else if (col == 3) {
                    constraints.insets.left = 20; // Ajouter une marge gauche de 20 pixels pour la quatrième colonne
                } else {
                    constraints.insets.left = 30; // Ajouter une marge gauche de 30 pixels pour la cinquième colonne
                }
        
                mainPanel.add(imageLabels[col][row], constraints);
            }
        }
        
               
        

        // Affichage de la fenêtre
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
