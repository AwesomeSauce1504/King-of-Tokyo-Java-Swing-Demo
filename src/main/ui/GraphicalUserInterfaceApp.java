package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents a GUI version of the King Of Tokyo Game
// Designed mostly based on SimpleDrawingPlayer
public class GraphicalUserInterfaceApp extends JFrame {
    private GameManager gm;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/savefile.json";

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    // MODIFIES: this
    // EFFECTS: creates a new instance of the game with a title. Sets up fields and loads the start screen
    public GraphicalUserInterfaceApp() {
        super("King Of Tokyo");
        initializeGraphics();
        initializeFields();
        startScreen();
    }

    // MODIFIES: this
    // EFFECTS: initializes an empty GameManager and a JsonReader and JsonWriter pointing to a save file
    private void initializeFields() {
        gm = new GameManager();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // EFFECTS: sets up the screen frame layout
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: creates the start screen with image, new game button, and load game button
    private void startScreen() {
        JPanel startScreenPanel = new JPanel();
        startScreenPanel.setLayout(new GridLayout(2, 1));

        startScreenPanel.add(createNewGameButton());
        startScreenPanel.add(createLoadButton());

        add(createStartScreenImagePanel(), BorderLayout.CENTER);

        add(startScreenPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // Designed based on TrafficLightGUI
    // EFFECTS: returns the start screen image as a formatted JPanel
    private JPanel createStartScreenImagePanel() {
        ImageIcon imageIcon = new ImageIcon("./data/KingOfTokyoCropped.jpg");
        JLabel imageLabel = new JLabel();
        JPanel imagePanel = new JPanel();
        imagePanel.add(imageLabel);

        Image img = imageIcon.getImage();
        Image imgScaled = img.getScaledInstance(WIDTH, HEIGHT - 90, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScaled);

        imageLabel.setIcon(scaledIcon);

        return imagePanel;
    }

    // EFFECTS: returns a button that allows for new games to be created
    private JButton createNewGameButton() {
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpNewGame();
            }
        });

        return newGameButton;
    }

    // created based on AlarmController
    // MODIFIES: this
    // EFFECTS: asks for the number of players for a new game (must be integer greater than 1).
    //          Adds that many players to gm and runs the first player's turn
    private void setUpNewGame() {
        int result = 0;
        try {
            String input = JOptionPane.showInputDialog(null,
                    "How many players in game (must be an integer greater than 1)",
                    "Choose the number of players",
                    JOptionPane.QUESTION_MESSAGE);
            result = Integer.parseInt(input);
            if (result > 1) {
                gm.addNPlayers(result);
                runTurn();
            }
        } catch (NumberFormatException e) {
            // do nothing
        }
    }

    // EFFECTS: returns a JButton that loads the saved game.
    private JButton createLoadButton() {
        JButton loadButton = new JButton("Load Saved Game");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSavedGame();
            }
        });
        return loadButton;
    }

    // Modeled based on JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads game (the gameManager) from save file
    private void loadSavedGame() {
        try {
            gm = jsonReader.read();
            runTurn();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: rolls dice and updates the screen
    private void runTurn() {
        getContentPane().removeAll();
        dicePhase();
        drawTurn();
    }

    // MODIFIES: this
    // EFFECTS: rolls a number of dice equal to the current player's dice amount and adds energy to that
    //          player based on how many energy they rolled.
    private void dicePhase() {
        gm.resetValues();
        gm.getAllDice().rollAllDice();
        gm.resolveEnergy();
    }

    // EFFECTS: draws the main game area with player statuses, dice rolled, and shop. Adds a next player
    //          button to  the right of the screen, a save button at the top of the screen, and an exit
    //          button at the bottom of the screen.
    private void drawTurn() {
        getContentPane().removeAll();
        drawGameArea();
        createNextPlayerButton();
        createSaveButton();
        createExitButton();

        validate();
        repaint();
    }

    // EFFECTS: creates the main game area with players, dice rolled, and the cardshop.
    private void drawGameArea() {
        JPanel gameArea = new JPanel();
        gameArea.setLayout(new GridLayout(3, 1));
        gameArea.setSize(new Dimension(0, 0));
        add(gameArea, BorderLayout.CENTER);

        setUpPlayerArea(gameArea);
        setUpDiceArea(gameArea);
        setUpCardArea(gameArea);
    }

    // EFFECTS: creates the player area with their current statuses
    private void setUpPlayerArea(JPanel gameArea) {
        JPanel playerArea = new JPanel();
        JScrollPane playerAreaScrollPane = new JScrollPane();

        playerArea.setLayout(new GridLayout(1, gm.getNumPlayers()));
        playerArea.setSize(new Dimension(0, 0));

        for (int i = 0; i < gm.getNumPlayers(); i++) {
            Player player = gm.getPlayersInGame().get(i);
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setText(createPlayerInfoText(player));
            textArea.setLineWrap(true);
            playerArea.add(textArea);
        }

        playerAreaScrollPane.setViewportView(playerArea);

        gameArea.add(playerAreaScrollPane);
    }

    // EFFECTS: returns information about the consumed player as a string
    private String createPlayerInfoText(Player player) {
        String result = "";
        if (player == gm.getCurrentPlayer()) {
            result += "--- CURRENT PLAYER ---\n";
        }
        result += "Player " + String.valueOf(player.getPlayerNumber() + 1) + " \n"
                + "Health : " + player.getHealth() + " \n"
                + "VPs: " + player.getVictoryPoints() + " \n"
                + "Energy: " + player.getEnergy() + " \n"
                + "Cards: \n";

        for (Card card : player.getOwnedCards()) {
            result += "- " + card.getName() + ": " + card.getEffectsText() + "\n";
        }
        return result;
    }

    // EFFECTS: creates the area to display all the dice rolls
    private void setUpDiceArea(JPanel gameArea) {
        JPanel diceArea = new JPanel();
        diceArea.setLayout(new GridLayout(1, gm.getCurrentPlayer().getDiceAmount()));

        for (int i = 0; i < gm.getAllDice().getDiceList().size(); i++) {
            Die die = gm.getAllDice().getDiceList().get(i);
            JPanel diePanel = new JPanel();
            diePanel.setLayout(new GridBagLayout());
            diePanel.add(new JLabel(die.textValueOfDiceResult()));
            diePanel.setBorder(BorderFactory.createLineBorder(Color.black));
            diceArea.add(diePanel);
        }

        gameArea.add(diceArea);
    }

    // EFFECTS: creates the area for the shop, as well as a button to reroll and a button to clear all
    //          cards owned by all players
    private void setUpCardArea(JPanel gameArea) {
        JPanel cardArea = new JPanel();
        cardArea.setLayout(new GridLayout(1, Shop.SHOP_SIZE + 2));
        for (int i = 0; i < Shop.SHOP_SIZE; i++) {
            createButtonForCard(i, cardArea);
        }
        createButtonForReroll(cardArea);
        createButtonForClear(cardArea);

        gameArea.add(cardArea);
    }

    // EFFECTS: creates a button that clears all cards owned by all players
    private void createButtonForClear(JPanel cardArea) {
        JButton clearButton = new JButton("CLEAR PLAYER CARDS");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Player player : gm.getPlayersInGame()) {
                    player.getOwnedCards().clear();
                }
                drawTurn();
            }
        });

        cardArea.add(clearButton);
    }

    // EFFECTS: creates a button that rerolls the shop
    private void createButtonForReroll(JPanel cardArea) {
        JButton rerollButton = new JButton("REROLL");
        rerollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryToReroll();
            }
        });

        cardArea.add(rerollButton);
    }

    // MODIFIES: this
    // EFFECTS: rerolls the shop if the current player has enough energy to pay the reroll cost
    private void tryToReroll() {
        if (gm.getCurrentPlayer().getEnergy() >= Shop.REROLL_COST) {
            gm.getCurrentPlayer().changeEnergy(-2);
            gm.getCardShop().reroll();
            drawTurn();
        }
    }

    // EFFECTS: creates a button that allows players to purchase a card
    private void createButtonForCard(int i, JPanel cardArea) {
        JScrollPane cardScrollPane = new JScrollPane();
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        JTextArea cardTextArea = new JTextArea();
        cardTextArea.setEditable(false);
        cardTextArea.setText(createCardText(gm.getCardShop().getAvailableCards().get(i), i));
        cardTextArea.setLineWrap(true);

        cardPanel.add(cardTextArea, BorderLayout.CENTER);

        JButton cardButton = new JButton("BUY CARD");
        cardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryToBuyCard(i);
            }
        });
        cardPanel.add(cardButton, BorderLayout.SOUTH);

        cardScrollPane.setViewportView(cardPanel);

        cardArea.add(cardScrollPane);
    }

    // EFFECTS: returns information about a card as a string
    private String createCardText(Card card, int i) {
        String result = "";
        result += "Card " + String.valueOf(i) + ": " + card.getName() + " \n";
        result += "Cost: " + card.getCost() + " \n";
        if (card.getIsKeep()) {
            result += "Type: KEEP \n";
        } else {
            result += "Type: DISCARD \n";
        }
        result += "Effects: " + card.getEffectsText() + "\n";

        return result;
    }

    // REQUIRES: i < Shop.SHOP_SIZE
    // MODIFIES: this
    // EFFECTS: buys card i in the shop if the current player can afford it
    private void tryToBuyCard(int i) {
        if (gm.getCurrentPlayer().canAfford(gm.getCardShop().getAvailableCards().get(i))) {
            gm.getCurrentPlayer().changeEnergy(-gm.getCardShop().getAvailableCards().get(i).getCost());
            gm.getCurrentPlayer().addCard(gm.getCardShop().buyCard(i));
        }

        drawTurn();
    }

    // EFFECTS: creates a button that runs the next player's turn
    private void createNextPlayerButton() {
        JButton nextPlayerButton = new JButton("Next Player");
        nextPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.setUpNextPlayer();
                runTurn();
            }
        });
        add(nextPlayerButton, BorderLayout.EAST);
    }

    // EFFECTS: creates a button that saves the game
    private void createSaveButton() {
        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
        add(saveButton, BorderLayout.NORTH);
    }

    // Modeled based on JsonSerializationDemo
    // EFFECTS: saves the game to a file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(gm);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: creates a button that closes the GUI
    private void createExitButton() {
        JButton exitGameButton = new JButton("Exit Game");
        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitGameButton, BorderLayout.SOUTH);
    }

    // EFFECTS: runs the game application
    public static void main(String[] args) {
        new GraphicalUserInterfaceApp();
    }

}
