package SlotMachine;

import javax.swing.*;
import User.User;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlotMachineGUI {
    private static final String SYMBOLS_JSON_PATH = "/symbols.json";
    private static final String TRANSITION_GIF_PATH = "/images/spinAnim3.gif";
    private static final int ANIMATION_DURATION = 1000; // 1000 ms (1 second)
    private static final String SLOT_MACHINE_IMAGE_PATH = "/images/slotMachine.png";
    private static final String SPIN_IMAGE_PATH = "/images/spin.png";
    private static final String WIN_IMAGE_PATH = "/images/win.png";
    private static final String LOSE_IMAGE_PATH = "/images/lose.png";
    private static final String LESS_IMAGE_PATH = "/images/less.png";
    private static final String MORE_IMAGE_PATH = "/images/more.png";
    private JLabel resultImageLabel;

    public static JLabel userMoneyLabel;
    public static JLabel userBetLabel;
    public static JLabel userTotalBetLabel;

    public static JSONArray readSymbolsJSON() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(SlotMachineGUI.class.getResourceAsStream(SYMBOLS_JSON_PATH)))) {
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

    public static void addClickEffect(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                label.setLocation(label.getX(), label.getY() + 4);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                label.setLocation(label.getX(), label.getY() - 4);
            }
        });
    }

    public static void createAndShowGUI(User mainUser, Collection<Column> columns, Collection<Symbol> symbolsJSON) {
        // Load symbols from the symbols.json file
        JSONArray symbols = readSymbolsJSON();
        ImageIcon[] images = loadImages(90, 90, symbols);
        SlotMachine slotMachine = new SlotMachine(columns, 5);

        // Creating the main window
        JFrame frame = new JFrame("Slot Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 750);

        // Adding the main panel with a background image
        JPanel mainPanel = new JPanel() {
            ImageIcon imageIcon = new ImageIcon(SlotMachineGUI.class.getResource(SLOT_MACHINE_IMAGE_PATH));
            Image image = imageIcon.getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);

        createLabelToDisplayUserMoney(mainUser, mainPanel);

        // Create a 2D array of JLabel to store the images
        JLabel[][] imageLabels = new JLabel[5][3];
        GridBagConstraints constraints = new GridBagConstraints();

        createAllSymbol(mainPanel, constraints, imageLabels, images);

        createAllButtonWithImages(mainUser, mainPanel, constraints, columns, imageLabels, images, slotMachine,  symbolsJSON);

        // Displaying the window
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static ImageIcon getNewTransitionGifInstance() {
        ImageIcon transitionGif = new ImageIcon(SlotMachineGUI.class.getResource(TRANSITION_GIF_PATH));
        return transitionGif;
    }
    static void generateNewSymbol(JPanel mainPanel, GridBagConstraints constraints, Collection<Column> columns, JLabel[][] imageLabels , ImageIcon[] images, SlotMachine slotMachine, Collection<Symbol> symbolsJSON) {
        ImageIcon transitionGif = new ImageIcon(SlotMachineGUI.class.getResource(TRANSITION_GIF_PATH));

        // Affichez le GIF pour chaque icône
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 3; row++) {
                imageLabels[col][row].setIcon(getNewTransitionGifInstance());
            }
        }
        // Créez un Timer pour gérer l'animation sans bloquer l'interface utilisateur
        Timer timer = new Timer(ANIMATION_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAllSymbols(mainPanel, imageLabels);

                for (Column column : columns) {
                    Collection<Symbol> symbols = slotMachine.generateSymbols(symbolsJSON, column.getLinesNumber());
                    column.clearSymbols();
                    column.setSymbols(symbols);

                    int positionOfSymbol = column.getLinesNumber() - column.getPrintNumberLine();
                    int col = column.getNumberColumn() - 1;

                    for (int row = 0; row < column.getPrintNumberLine(); row++) {
                        ImageIcon imageIcon = images[column.getSymbol(positionOfSymbol - 1 + row).getId() - 1];
                        imageLabels[col][row] = new JLabel(imageIcon);

                        displayGeneratedSymbol(col, row, constraints);

                        mainPanel.add(imageLabels[col][row], constraints);
                    }
                }

                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        // Démarrez le Timer
        timer.setRepeats(false);
        timer.start();

    }
    static void displayNewSymbol(JPanel mainPanel, GridBagConstraints constraints, Collection<Column> columns, JLabel[][] imageLabels , ImageIcon[] images) {
        ImageIcon transitionGif = new ImageIcon(SlotMachineGUI.class.getResource(TRANSITION_GIF_PATH));

        // Affichez le GIF pour chaque icône
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 3; row++) {
                imageLabels[col][row].setIcon(getNewTransitionGifInstance());
            }
        }
        // Créez un Timer pour gérer l'animation sans bloquer l'interface utilisateur
        Timer timer = new Timer(ANIMATION_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAllSymbols(mainPanel, imageLabels);

                for (Column column : columns) {
                    int col = column.getNumberColumn()-1;

                    for (int row = 0; row < column.getPrintNumberLine(); row++) {
                        int positionOfSymbol = column.getLinesNumber() - column.getPrintNumberLine();
                        ImageIcon imageIcon = images[column.getSymbol(positionOfSymbol + row).getId()-1];
                        imageLabels[col][row] = new JLabel(imageIcon);

                        displayGeneratedSymbol(col, row, constraints);

                        mainPanel.add(imageLabels[col][row], constraints);
                    }
                }

                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        // Démarrez le Timer
        timer.setRepeats(false);
        timer.start();

    }

    public void showWinImage() {
        ImageIcon winIcon = new ImageIcon(SlotMachineGUI.class.getResource(WIN_IMAGE_PATH));
        JOptionPane.showMessageDialog(null, "", "You Win!", JOptionPane.INFORMATION_MESSAGE, winIcon);
    }

    public void showLoseImage() {
        ImageIcon loseIcon = new ImageIcon(SlotMachineGUI.class.getResource(LOSE_IMAGE_PATH));
        JOptionPane.showMessageDialog(null, "", "You Lose!", JOptionPane.INFORMATION_MESSAGE, loseIcon);
    }

    private void showImage(String imagePath, String title) {
        ImageIcon imageIcon = new ImageIcon(SlotMachineGUI.class.getResource(imagePath));
        final JOptionPane optionPane = new JOptionPane("", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, imageIcon);
        final JDialog dialog = optionPane.createDialog(null, title);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
    }

    public static void createAllSymbol(JPanel mainPanel , GridBagConstraints constraints, JLabel[][] imageLabels , ImageIcon[] images) {
        Random random = new Random();

        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 3; row++) {
                ImageIcon imageIcon = images[random.nextInt(images.length)];
                imageLabels[col][row] = new JLabel(imageIcon);

                displayGeneratedSymbol(col, row, constraints);

                mainPanel.add(imageLabels[col][row], constraints);
            }
        }
    }
    public static void removeAllSymbols(JPanel mainPanel, JLabel[][] imageLabels) {
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 3; row++) {
                mainPanel.remove(imageLabels[col][row]);
            }
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void displayGeneratedSymbol(int col, int row, GridBagConstraints constraints) {
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
    }
    public static void createAllButtonWithImages(User mainUser,JPanel mainPanel, GridBagConstraints constraints, Collection<Column> columns, JLabel[][] imageLabels , ImageIcon[] images, SlotMachine slotMachine, Collection<Symbol> symbolsJSON) {
        // Create buttons with images
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridwidth = 2;
        buttonPanel.setOpaque(false);

        // Spin button
        ImageIcon spinIcon = new ImageIcon(SlotMachineGUI.class.getResource(SPIN_IMAGE_PATH));
        JLabel spinLabel = new JLabel(spinIcon);
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.insets = new Insets(0, 13, 20, 0);
        mainPanel.add(spinLabel, constraints);
        addClickEffect(spinLabel);

        spinLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mainUser.useFreeAttempts(); //Checking if the user has free attempts
                if(mainUser.getMoneyBet() > 0 && mainUser.getMoneyBet() <= mainUser.getMoney()) {
                    generateNewSymbol(mainPanel, constraints, columns, imageLabels , images, slotMachine, symbolsJSON);
                    //while(slotMachine.startMachine(mainUser, columns)) {
                    slotMachine.startMachine(mainUser, columns);

                    //displayNewSymbol(mainPanel, constraints, columns, imageLabels , images);

                    mainUser.setMoney(mainUser.getMoney() - mainUser.getMoneyBet());
                    mainUser.totalBetMonney(mainUser.getMoneyBet());
                    mainUser.setMoneyBet(0);
                    userBetLabel.setText("" + mainUser.getMoneyBet());
                    userTotalBetLabel.setText("" + mainUser.getTotalBet());
                    userMoneyLabel.setText("" + mainUser.getMoney());
                } else if (mainUser.getMoneyBet() == 0){
                    userBetLabel.setText("inf to 0");
                    new Timer(2000, event -> {
                        userBetLabel.setText("" + mainUser.getMoneyBet());
                    }).start();

                } else if (mainUser.getMoneyBet() > mainUser.getMoney()){
                    userMoneyLabel.setText("inf to bet");
                    new Timer(2000, event -> {
                        userMoneyLabel.setText("" + mainUser.getMoney());
                    }).start();

                }

                }
        });

        // Less bet button
        ImageIcon lessIcon = new ImageIcon(SlotMachineGUI.class.getResource(LESS_IMAGE_PATH));
        JLabel lessBetLabel = new JLabel(lessIcon);
        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.insets = new Insets(0, 55, 51, 0);
        mainPanel.add(lessBetLabel, constraints);
        addClickEffect(lessBetLabel);
        lessBetLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainUser.betLessMoney();
                userBetLabel.setText("" + mainUser.getMoneyBet());
            }
        });
        // More bet button
        ImageIcon moreIcon = new ImageIcon(SlotMachineGUI.class.getResource(MORE_IMAGE_PATH));
        JLabel moreBetLabel = new JLabel(moreIcon);
        constraints.gridx = 4;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.insets = new Insets(0, 25, 51, 0);
        mainPanel.add(moreBetLabel, constraints);
        addClickEffect(moreBetLabel);

        moreBetLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainUser.betMoreMoney();
                userBetLabel.setText("" + mainUser.getMoneyBet());
            }
        });

        // Adding the buttons panel to the main panel
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(buttonPanel, constraints);
    }

    // Adding the action to the Spin button (now Spin label)
    public static void createLabelToDisplayUserMoney(User mainUser, JPanel mainPanel) {
        // Add the JLabel to display the user's money
        JPanel userMoneyPanel = new JPanel();
        userMoneyPanel.setOpaque(false);

        userMoneyLabel = new JLabel();
        userMoneyLabel.setFont(new Font("Arial", Font.BOLD, 22));
        userMoneyLabel.setForeground(Color.WHITE);
        userMoneyLabel.setText("" + mainUser.getMoney());

        userMoneyPanel.add(userMoneyLabel);
        GridBagConstraints userMoneyPanelConstraints = new GridBagConstraints();
        userMoneyPanelConstraints.gridx = 0;
        userMoneyPanelConstraints.gridy = 5;
        userMoneyPanelConstraints.anchor = GridBagConstraints.NORTHWEST;
        userMoneyPanelConstraints.insets = new Insets(110, 25, 0, 0);
        mainPanel.add(userMoneyPanel, userMoneyPanelConstraints);

        // Add the JLabel to display the user's bet money
        JPanel userBetPanel = new JPanel();
        userBetPanel.setOpaque(false);

        userBetLabel = new JLabel();
        userBetLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userBetLabel.setForeground(Color.WHITE);
        userBetLabel.setText("" + mainUser.getMoneyBet());

        userBetPanel.add(userBetLabel);
        GridBagConstraints userBetPanelConstraints = new GridBagConstraints();
        userBetPanelConstraints.gridx = 0;
        userBetPanelConstraints.gridy = 5;
        userBetPanelConstraints.anchor = GridBagConstraints.NORTHWEST;
        userBetPanelConstraints.insets = new Insets(48, 43, 0, 0);
        mainPanel.add(userBetPanel, userBetPanelConstraints);

        JPanel userTotalBetPanel = new JPanel();
        userTotalBetPanel.setOpaque(false);

        //Add the JLabel to display the user's money total bet
        userTotalBetLabel = new JLabel();
        userTotalBetLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userTotalBetLabel.setForeground(Color.WHITE);
        userTotalBetLabel.setText("" + mainUser.getTotalBet());

        userTotalBetPanel.add(userTotalBetLabel);
        GridBagConstraints userTotalBetPanelConstraints = new GridBagConstraints();
        userTotalBetPanelConstraints.gridx = 1;
        userTotalBetPanelConstraints.gridy = 5;
        userTotalBetPanelConstraints.anchor = GridBagConstraints.NORTHWEST;
        userTotalBetPanelConstraints.insets = new Insets(48, 51,0, 0);
        mainPanel.add(userTotalBetPanel, userTotalBetPanelConstraints);
    }
}
