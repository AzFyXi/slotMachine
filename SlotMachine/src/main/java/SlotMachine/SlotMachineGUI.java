package SlotMachine;

import javax.swing.*;
import User.User;
import java.awt.*;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.InputStreamReader;

public class SlotMachineGUI {

    public static JLabel userMoneyLabel;

    public static JSONArray readSymbolsJSON() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(SlotMachineGUI.class.getResourceAsStream("/Ressources/symbols.json")))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(content.toString());
        return jsonObject.getJSONArray("symbols");
    }

    public static ImageIcon[] loadImages(int width, int height, JSONArray symbols) {
        ImageIcon[] images = new ImageIcon[symbols.length()];
    
        for (int i = 0; i < symbols.length(); i++) {
            String imageUrl = symbols.getJSONObject(i).getString("image_url");
            ImageIcon imageIcon = new ImageIcon(SlotMachineGUI.class.getResource(imageUrl));
            Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            images[i] = new ImageIcon(image);
        }
    
        return images;
    }

    public static void updateUserMoneyDisplay(User user) {
        if (userMoneyLabel != null) {
            userMoneyLabel.setText("Money: " + user.getMoney());
        }
    }

    public static void createAndShowGUI() {
        // Load symbols from the symbols.json file
        JSONArray symbols = readSymbolsJSON();
        ImageIcon[] images = loadImages(90, 90, symbols);

        // Creating the main window
        JFrame frame = new JFrame("Slot Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 750);

        // Adding the main panel with a background image
        JPanel mainPanel = new JPanel() {
            ImageIcon imageIcon = new ImageIcon(SlotMachineGUI.class.getResource("/Ressources/assets/images/slotMachine.png"));
            Image image = imageIcon.getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);

        // Add the JLabel to display the user's money
        userMoneyLabel = new JLabel();
        userMoneyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userMoneyLabel.setForeground(Color.WHITE);
        GridBagConstraints userMoneyLabelConstraints = new GridBagConstraints();
        userMoneyLabelConstraints.gridx = 0;
        userMoneyLabelConstraints.gridy = 1;
        userMoneyLabelConstraints.anchor = GridBagConstraints.NORTHWEST;
        userMoneyLabelConstraints.insets = new Insets(0, 20, 20, 0);
        mainPanel.add(userMoneyLabel, userMoneyLabelConstraints);

        // Create a 2D array of JLabel to store the images
        JLabel[][] imageLabels = new JLabel[5][3];
        GridBagConstraints constraints = new GridBagConstraints();
        Random random = new Random();

        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 3; row++) {
                ImageIcon imageIcon = images[random.nextInt(images.length)];
                imageLabels[col][row] = new JLabel(imageIcon);

                constraints.gridx = col;
                constraints.gridy = row;

                if (row == 0) {
                    constraints.anchor = GridBagConstraints.CENTER;
                    constraints.insets = new Insets(70, 30, 20, 30); // top, left, bottom, right
                } else if (row == 1) {
                    constraints.anchor = GridBagConstraints.CENTER;
                    constraints.insets = new Insets(20, 30, 20, 30);
                } else {
                    constraints.anchor = GridBagConstraints.CENTER;
                    constraints.insets = new Insets(20, 30, 20, 30);
                }
                constraints.gridy += 1;

                if (col == 4) {
                    constraints.insets.right = 35;
                } else {
                    constraints.insets.right = 35;
                }

                if (col == 0) {
                    constraints.insets.left = 30;
                } else if (col == 1) {
                    constraints.insets.left = 30;
                } else if (col == 2) {
                    constraints.insets.left = 30;
                } else if (col == 3) {
                    constraints.insets.left = 30;
                } else {
                    constraints.insets.left = 30;
                }

                mainPanel.add(imageLabels[col][row], constraints);
            }
        }

        // Create buttons with images
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridwidth = 2;
        buttonPanel.setOpaque(false);
        
        // Spin button
        JButton spinButton = createButtonWithImage("/Ressources/assets/images/spin.png");
        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = 2;
        buttonConstraints.weightx = 0.0;
        buttonConstraints.insets = new Insets(30, 0, 0, 0);
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(spinButton, buttonConstraints);
        
        // Auto Spin button
        /*JButton autoSpinButton = createButtonWithImage("src/main/java/Ressources/assets/images/autoSpin.png");
        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = 2;
        buttonConstraints.weightx = 0.0;
        buttonConstraints.insets = new Insets(600, 200, 0, 0);
        buttonConstraints.anchor = GridBagConstraints.LINE_END;
        buttonPanel.add(autoSpinButton, buttonConstraints);
        
        // Max Bet button
        JButton maxBetButton = createButtonWithImage("src/main/java/Ressources/assets/images/maxBet.png");
        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = 2;
        buttonConstraints.weightx = 0.0;
        buttonConstraints.insets = new Insets(600, 100, 0, 0);
        buttonConstraints.anchor = GridBagConstraints.LINE_END;
        buttonPanel.add(maxBetButton, buttonConstraints);*/
        
        // Adding the buttons panel to the main panel
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(buttonPanel, constraints);

        // Adding the action to the Spin button
spinButton.addActionListener(e -> {
    // Creating the animation transition
    int initialWidth = mainPanel.getWidth();
    int initialHeight = mainPanel.getHeight();
    int finalWidth = (int) (initialWidth * 0.8);
    int finalHeight = (int) (initialHeight * 0.8);
    int deltaX = (initialWidth - finalWidth) / 2;
    int deltaY = (initialHeight - finalHeight) / 2;
    int duration = 2000; // Transition time in milliseconds
    int delay = 20; // Delay between each frame of the transition in milliseconds

    Timer timer = new Timer(delay, null);
    timer.addActionListener(new ActionListener() {
        long startTime = -1;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (startTime == -1) {
                startTime = System.currentTimeMillis();
            }

            float progress = Math.min(1f, (System.currentTimeMillis() - startTime) / (float) duration);
            int newWidth = (int) (initialWidth - (initialWidth - finalWidth) * progress);
            int newHeight = (int) (initialHeight - (initialHeight - finalHeight) * progress);
            mainPanel.setPreferredSize(new Dimension(newWidth, newHeight));
            mainPanel.revalidate();
            mainPanel.repaint();

            if (progress == 1f) {
                timer.stop();
                // Modify the other components of the user interface according to the transition
            }
        }
    });

    // Start the animation transition
    timer.start();
});


        // Displaying the window
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static JButton createButtonWithImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(SlotMachineGUI.class.getResource(imagePath));
        JButton button = new JButton(imageIcon);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        return button;
    }
}

