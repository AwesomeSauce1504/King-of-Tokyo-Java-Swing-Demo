package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main game application
public class Game {
    private Deck powerCardDeck; // deck
    private Shop cardShop; // shop
    // Tokyo board
    private List<Player> playersInGame; // Players
    private Scanner input;
    private int numPlayers;
    private int currentPlayerNumber;
    private Player currentPlayer;
    private int rerollsUsed;
    private int numberOfOnes;
    private int numberOfTwos;
    private int numberOfThrees;
    private int numberOfAttacks;
    private int numberOfHeals;
    private int numberOfEnergies;
    private DieCollection allDice;
    private boolean gameIsRunning;
    public static final int MAX_REROLLS = 3;

    public Game() {
        runGame();
    }

    private void initBoard() {
        // initialize deck, shop, tokyo
        this.powerCardDeck = new Deck();
        this.cardShop = new Shop(powerCardDeck);
        this.playersInGame = new ArrayList<Player>();
        this.currentPlayerNumber = 0;
        this.rerollsUsed = 0;
        this.allDice = new DieCollection(0);
        // !!! Tokyo
    }

    private void runGame() {
        gameIsRunning = true; // !!!
        String strCommand;
        initBoard();
        initPlayers();

        // some while loop to keep the game loop going
        while (gameIsRunning) {
            resetValues();
            System.out.println("It is Player " + (currentPlayerNumber + 1) + "'s turn!");
            // check for eliminations
            diceRollPhase();
            resolveDice();
            // checkForEliminations();
            // checkEnterTokyo();
            // enter tokyo if applicable
            // check for eliminations
            displayShop();
            askIfPlayerWantsToShop();
            // shop
            // check for eliminations
            // end and loop
            strCommand = input.next();
            processStringCommand(strCommand);


            // gameIsRunning = false; // TEMP FOR TESTING
            if (gameIsOver()) {
                gameIsRunning = false;
            }
            setUpNextPlayer();

        }
    }

    private void setUpNextPlayer() {
        if (currentPlayerNumber < playersInGame.size() - 1) {
            currentPlayerNumber++;
            currentPlayer = playersInGame.get(currentPlayerNumber);
        } else {
            currentPlayerNumber = 0;
            currentPlayer = playersInGame.get(0);
        }
    }

    private void askIfPlayerWantsToShop() {
        System.out.println("Would you like to buy any power cards? Enter y/n");
        if (yesNoInput()) {
            System.out.println("Let's go shopping!");
            openShop();
        } else {
            System.out.println("Very well, no shopping today!");
        }
    }

    private void openShop() {
        boolean checkingForValidInput = true;
        while (checkingForValidInput) {
            System.out.println("Which card would you like to buy? Enter 1, 2, 3, r to reroll, q to exit shop");
            displayShop();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("1")) {
                tryToBuyCard("1", 0);
            } else if (command.equals("2")) {
                tryToBuyCard("2", 1);
            } else if (command.equals("3")) {
                tryToBuyCard("3", 2);
            } else if (command.equals("q")) {
                System.out.println("Leaving shop!");
                checkingForValidInput = false;
            } else if (command.equals("r")) {
                System.out.println("Rerolling!");
                cardShop.reroll();
            } else {
                System.out.println("Invalid input");
            }
        }
        System.out.println(currentPlayer.getOwnedCards());
    }

    private void tryToBuyCard(String s, int i) {
        System.out.println("Trying to buy card " + s);
        if (currentPlayer.canAfford(cardShop.getAvailableCards().get(i))) {
            System.out.println("Buying card " + s + ":" + cardShop.getAvailableCards().get(i).getName() + "!");
            currentPlayer.changeEnergy(-cardShop.getAvailableCards().get(i).getCost());
            currentPlayer.addCard(cardShop.buyCard(i));
            System.out.println("Your total energy is now " + currentPlayer.getEnergy());
        } else {
            System.out.println("Can't afford this card");
        }
    }

    private void checkForEliminations() {
        for (Player p : playersInGame) {
            if (p.getHealth() <= 0) {
                playersInGame.remove(p);
            }
        }
    }

    private boolean gameOver() {
        return playersInGame.size() <= 1;
    }

    private void displayShop() {
        System.out.println("Here's what is available in the shop:");
        for (int i = 0; i < Shop.SHOP_SIZE; i++) {
            System.out.println("Card " + (i + 1) + " ");
            System.out.print("Cost: " + cardShop.getAvailableCards().get(i).getCost() + " ");
            if (cardShop.getAvailableCards().get(i).getIsKeep()) {
                System.out.print("Type: KEEP");
            } else {
                System.out.println("Type: DISCARD");
            }
            System.out.print("Effects: " + cardShop.getAvailableCards().get(i).getEffectsText() + "\n");
        }
    }


    private void checkEnterTokyo() {
        // stub
    }

    private void resetValues() {
        input = new Scanner(System.in);
        rerollsUsed = 0;
        numberOfOnes = 0;
        numberOfTwos = 0;
        numberOfThrees = 0;
        numberOfAttacks = 0;
        numberOfHeals = 0;
        numberOfEnergies = 0;
        allDice = new DieCollection(currentPlayer.getDiceAmount());
    }

    private void resolveDice() {
        printDiceResults();
        // getDiceResults();
        // resolve ones
        // resolve twos
        // resolve threes
        // resolve attacks
        // resolve health
        resolveEnergy();
    }

    private void resolveEnergy() {
        currentPlayer.changeEnergy(allDice.getNumberOfEnergies());
        System.out.println("Player " + (currentPlayerNumber + 1) + " has " + currentPlayer.getEnergy() + " energy!");
    }

    private void printDiceResults() {
        System.out.println("You rolled:");
        System.out.print(allDice.getNumberOfOnes() + " x [1], ");
        System.out.print(allDice.getNumberOfTwos() + " x [2], ");
        System.out.print(allDice.getNumberOfThrees() + " x [3], ");
        System.out.print(allDice.getNumberOfAttacks() + " x [attack], ");
        System.out.print(allDice.getNumberOfHeals() + " x [health], ");
        System.out.print("and " + allDice.getNumberOfEnergies() + " x [energy] \n");
    }

    /* private void getDiceResults() {
        numberOfOnes = 0;
        numberOfTwos = 0;
        numberOfThrees = 0;
        numberOfAttacks = 0;
        numberOfHeals = 0;
        numberOfEnergies = 0;

        for (Die d : allDice.getDiceList()) {
            if (d.getValue() == Die.ONE_VP) {
                numberOfOnes++;
            } else if (d.getValue() == Die.TWO_VP) {
                numberOfTwos++;
            } else if (d.getValue() == Die.THREE_VP) {
                numberOfThrees++;
            } else if (d.getValue() == Die.ATTACK) {
                numberOfAttacks++;
            } else if (d.getValue() == Die.HEAL) {
                numberOfHeals++;
            } else if (d.getValue() == Die.ENERGY) {
                numberOfEnergies++;
            } else {
                System.out.println("Die value invalid");
            }
        }
    } */

    // !!!
    private String processStringCommand(String str) {
        System.out.println("processStringCommand");
        if (str.equals("a")) {
            System.out.println("a was read");
        } else if (str.equals("e")) {
            System.out.println("Exiting game...");
            gameIsRunning = false;
        }
        return str;
    }

    private void initPlayers() {
        boolean initNotDone = true;
        while (initNotDone) {
            try {
                Scanner initPlayersInput = new Scanner(System.in);
                System.out.println("How many players are in this game? (Must be an integer greater than 1)");
                numPlayers = initPlayersInput.nextInt();
                if (numPlayers > 1) {
                    initNotDone = false;
                }
            } catch (Exception e) {
                System.out.println("That wasn't an integer");
            }
        }
        addPlayers();
    }

    private void addPlayers() {
        if (numPlayers > 1) {
            System.out.println("You have chosen a " + numPlayers + " player game!");
            System.out.println("Game start!");
            for (int i = 0; i < numPlayers; i++) {
                playersInGame.add(new Player(i));
            }
            this.currentPlayer = playersInGame.get(currentPlayerNumber);
        }
    }

    private boolean gameIsOver() {
        return numPlayers <= 1;
    }

    private void diceRollPhase() {
        // roll dice
        // check if enough rerolls remain
        // check for reroll
        //   - if yes reroll, roll dice again
        //   - if no reroll, roll dice and loop diceRollPhase
        // boolean checkingForReroll = true;
        rollCurrentPlayerDice();

        // checkForReroll();
        /*
        while (checkingForReroll) {
            Scanner input = new Scanner(System.in);
            String command = input.next();
            command = command.toLowerCase();

            if (yesNoInput()) {
                System.out.println("");
            }
        } */
    }

    private void rollCurrentPlayerDice() {
        System.out.println("Rolling dice!");
        allDice.rollAllDice();
    }

    private void checkForReroll() {
        if (rerollsUsed < MAX_REROLLS) {
            System.out.println("Would you like to reroll any dice? Enter y/n");
            // processReroll
        } else {
            System.out.println("Time to resolve dice!");
        }
    }

    private boolean yesNoInput() {
        boolean checkingForValidInput = true;
        boolean result = false;

        while (checkingForValidInput) {
            Scanner input = new Scanner(System.in);
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("n")) {
                System.out.println("NO");
                checkingForValidInput = false;
                result = false;
            } else if (command.equals("y")) {
                System.out.println("YES");
                checkingForValidInput = false;
                result = true;
            } else {
                System.out.println("Invalid  response");
            }
        }
        return result;
    }
}
