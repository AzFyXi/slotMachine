package SlotMachine;

import javax.swing.*;
import User.User;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
    private static final String FREEBACKGROUND_IMAGE_PATH = "/images/freeBackground.png";
    private static final String ONE_IMAGE_PATH = "/images/15fois2Btn.png";
    private static final String TWO_IMAGE_PATH = "/images/10fois3Btn.png";
    private static final String THREE_IMAGE_PATH = "/images/5fois6Btn.png";
    public static JLabel userMoneyLabel;
    public static JLabel userBetLabel;
    public static JLabel userFreeAttemptLabel;

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

        // View the GIF for each icon
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 3; row++) {
                imageLabels[col][row].setIcon(getNewTransitionGifInstance());
            }
        }
        // Create a Timer to manage the animation without blocking the UI
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

        // Start the Timer
        timer.setRepeats(false);
        timer.start();

    }
    static void displayNewSymbol(JPanel mainPanel, GridBagConstraints constraints, Collection<Column> columns, JLabel[][] imageLabels , ImageIcon[] images) {
        ImageIcon transitionGif = new ImageIcon(SlotMachineGUI.class.getResource(TRANSITION_GIF_PATH));

        // View the GIF for each icon
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 3; row++) {
                imageLabels[col][row].setIcon(getNewTransitionGifInstance());
            }
        }
        // Create a Timer to manage the animation without blocking the UI
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

        timer.setRepeats(false);
        timer.start();

    }

    public void showWinImage() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon winIcon = new ImageIcon(SlotMachineGUI.class.getResource(WIN_IMAGE_PATH));
                JOptionPane.showMessageDialog(null, "", "You Win!", JOptionPane.INFORMATION_MESSAGE, winIcon);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void showLoseImage() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon loseIcon = new ImageIcon(SlotMachineGUI.class.getResource(LOSE_IMAGE_PATH));
                JOptionPane.showMessageDialog(null, "", "You Lose!", JOptionPane.INFORMATION_MESSAGE, loseIcon);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void showFreeAttemptMenu(User mainUser, JFrame frame) {
        // Créez un JPanel pour contenir le menu FreeAttempt
        JPanel freeAttemptPanel = new JPanel() {
            ImageIcon imageIcon = new ImageIcon(SlotMachineGUI.class.getResource(FREEBACKGROUND_IMAGE_PATH));
            Image image = imageIcon.getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        freeAttemptPanel.setLayout(new GridBagLayout());

        // Ajoutez les boutons et configurez les événements de clic
        createFreeAttemptButtons(mainUser, freeAttemptPanel);

        // Affichez le menu FreeAttempt
        frame.setContentPane(freeAttemptPanel);
        frame.revalidate();
    }

    public void createFreeAttemptButtons(User mainUser, JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        ImageIcon oneIcon = new ImageIcon(SlotMachineGUI.class.getResource(ONE_IMAGE_PATH));
        JButton oneButton = new JButton(oneIcon);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(oneButton, constraints);

        ImageIcon twoIcon = new ImageIcon(SlotMachineGUI.class.getResource(TWO_IMAGE_PATH));
        JButton twoButton = new JButton(twoIcon);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(twoButton, constraints);

        ImageIcon threeIcon = new ImageIcon(SlotMachineGUI.class.getResource(THREE_IMAGE_PATH));
        JButton threeButton = new JButton(threeIcon);
        constraints.gridx = 2;
        constraints.gridy = 0;
        panel.add(threeButton, constraints);

        // Ajoutez les ActionListener pour gérer les événements de clic
        oneButton.addActionListener(e -> {
            mainUser.setFreeAttempts(2, 15);
            panel.setVisible(false); // Cachez le menu FreeAttempt
        });

        twoButton.addActionListener(e -> {
            mainUser.setFreeAttempts(3, 10);
            panel.setVisible(false); // Cachez le menu FreeAttempt
        });

        threeButton.addActionListener(e -> {
            mainUser.setFreeAttempts(6, 5);
            panel.setVisible(false); // Cachez le menu FreeAttempt
        });
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
                if(mainUser.useFreeAttempt()) {
                    generateNewSymbol(mainPanel, constraints, columns, imageLabels , images, slotMachine, symbolsJSON);
                    slotMachine.startMachine(mainUser, columns);
                    displayNewSymbol(mainPanel, constraints, columns, imageLabels , images);
                    mainUser.setMoney(mainUser.getMoney());
                    mainUser.setTotalEarn(-1);
                    userBetLabel.setText("" + mainUser.getMoneyBet());
                    userMoneyLabel.setText("" + mainUser.getMoney());
                }
                if(mainUser.getMoneyBet() > 0 && mainUser.getMoneyBet() <= mainUser.getMoney()) {
                    generateNewSymbol(mainPanel, constraints, columns, imageLabels , images, slotMachine, symbolsJSON);
                    slotMachine.startMachine(mainUser, columns);
                    displayNewSymbol(mainPanel, constraints, columns, imageLabels , images);
                    mainUser.setMoney(mainUser.getMoney() - mainUser.getMoneyBet());
                    mainUser.setMoneyBet(0);
                    mainUser.setTotalEarn(-1);
                    userBetLabel.setText("" + mainUser.getMoneyBet());
                    userMoneyLabel.setText("" + mainUser.getMoney());

                   /* if (mainUser.getTotalEarn() > 300) {

                        new Timer(1000, event -> {
                            userMoneyLabel.setText("" + mainUser.getMoney() + "(+" + mainUser.getTotalEarn() + ")");
                        }).start();
                    } else if( mainUser.getMoneyBet() > 0) {
                        new Timer(1000, event -> {
                            userMoneyLabel.setText("" + mainUser.getMoney() + "(-" + mainUser.getMoneyBet() + ")");
                        }).start();

                        new Timer(3000, event -> {
                            userMoneyLabel.setText("" + mainUser.getMoney());
                        }).start();
                        //mainUser.setTotalEarn(-1);
                    } else {
                        userMoneyLabel.setText("" + mainUser.getMoney());
                    }*/

                    mainUser.setMoneyBet(0);
                    mainUser.setTotalEarn(-1);
                    userBetLabel.setText("" + mainUser.getMoneyBet());
                    //userFreeAttemptLabel.setText("" + mainUser.getTotalBet());
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
        userMoneyLabel.setFont(new Font("Impact", Font.ROMAN_BASELINE, 18));
        userMoneyLabel.setForeground(Color.WHITE);
        userMoneyLabel.setText("" + mainUser.getMoney());

        userMoneyPanel.add(userMoneyLabel);
        GridBagConstraints userMoneyPanelConstraints = new GridBagConstraints();
        userMoneyPanelConstraints.gridx = 0;
        userMoneyPanelConstraints.gridy = 5;
        userMoneyPanelConstraints.anchor = GridBagConstraints.NORTHWEST;
        userMoneyPanelConstraints.insets = new Insets(108, 80, 0, 0);
        mainPanel.add(userMoneyPanel, userMoneyPanelConstraints);

        // Add the JLabel to display the user's bet money
        JPanel userBetPanel = new JPanel();
        userBetPanel.setOpaque(false);

        userBetLabel = new JLabel();
        userBetLabel.setFont(new Font("Impact", Font.ROMAN_BASELINE, 18));
        userBetLabel.setForeground(Color.WHITE);
        userBetLabel.setText("" + mainUser.getMoneyBet());

        userBetPanel.add(userBetLabel);
        GridBagConstraints userBetPanelConstraints = new GridBagConstraints();
        userBetPanelConstraints.gridx = 0;
        userBetPanelConstraints.gridy = 5;
        userBetPanelConstraints.anchor = GridBagConstraints.NORTHWEST;
        userBetPanelConstraints.insets = new Insets(47, 43, 0, 0);
        mainPanel.add(userBetPanel, userBetPanelConstraints);

        JPanel userFreeAttemptPanel = new JPanel();
        userFreeAttemptPanel.setOpaque(false);

        //Add the JLabel to display the user's free attempt
        userFreeAttemptLabel = new JLabel();
        userFreeAttemptLabel.setFont(new Font("Impact", Font.ROMAN_BASELINE, 18));
        userFreeAttemptLabel.setForeground(Color.WHITE);
        //userFreeAttemptLabel.setText("" + mainUser.getTotalBet());

        userFreeAttemptPanel.add(userFreeAttemptLabel);
        GridBagConstraints userFreeAttemptPanelConstraints = new GridBagConstraints();
        userFreeAttemptPanelConstraints.gridx = 1;
        userFreeAttemptPanelConstraints.gridy = 5;
        userFreeAttemptPanelConstraints.anchor = GridBagConstraints.NORTHWEST;
        userFreeAttemptPanelConstraints.insets = new Insets(47, 70,0, 0);
        mainPanel.add(userFreeAttemptPanel, userFreeAttemptPanelConstraints);
    }
}
