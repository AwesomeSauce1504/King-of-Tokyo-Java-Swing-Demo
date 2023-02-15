package ui;

import model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Main game ui application
public class Game {
    // public static final int MAX_REROLLS = 3;
    private Deck powerCardDeck;
    private Shop cardShop;
    // Tokyo board
    private List<Player> playersInGame;
    private Scanner input;
    private int numPlayers;
    private int currentPlayerNumber;
    private Player currentPlayer;
    private DieCollection allDice;
    private boolean gameIsRunning;

    // EFFECTS: begins the game
    public Game() {
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: initialize the deck, card shop, players, player number, rerolls, and dice
    private void initBoard() {
        this.powerCardDeck = new Deck();
        this.cardShop = new Shop(powerCardDeck);
        this.playersInGame = new ArrayList<Player>();
        this.currentPlayerNumber = 0;
        this.allDice = new DieCollection(0);
    }

    // MODIFIES: this
    // EFFECTS: initializes all main game components, the players (after getting total players in game),
    //          and runs the game loop
    private void runGame() {
        gameIsRunning = true;
        String strCommand;
        initBoard();
        initPlayers();

        while (gameIsRunning) {
            resetValues();
            displayPlayers();
            System.out.println("It is Player " + (currentPlayerNumber + 1) + "'s turn!");
            diceRollPhase();
            resolveDice();
            displayShop();
            askIfPlayerWantsToShop();
            System.out.println("Would you like to exit game? (Enter e to exit, any other key to continue playing)");
            strCommand = input.next();
            askIfWantToExit(strCommand);

            if (gameIsOver()) {
                gameIsRunning = false;
            }
            setUpNextPlayer();

        }
    }

    // REQUIRES: currentPlayerNumber >= 0
    // MODIFIES: this
    // EFFECTS: set the currentPlayer to the next player if not all players have gone in a round,
    //          else reset the player turn to the first player
    private void setUpNextPlayer() {
        if (currentPlayerNumber < playersInGame.size() - 1) {
            currentPlayerNumber++;
            currentPlayer = playersInGame.get(currentPlayerNumber);
        } else {
            currentPlayerNumber = 0;
            currentPlayer = playersInGame.get(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: ask the player if they want to shop. If they do, open the shop and give prompts for shopping
    private void askIfPlayerWantsToShop() {
        System.out.println("Would you like to buy any power cards? Enter y/n");
        if (yesNoInput()) {
            System.out.println("Let's go shopping!");
            openShop();
        } else {
            System.out.println("Very well, no shopping today!");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the shop and allows players to buy cards, reroll cards, or exit the shop
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
                tryToReroll();
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    // REQUIRES: i < Shop.SHOP_SIZE
    // MODIFIES: this
    // EFFECTS: buys card i in the shop if the current player can afford it
    private void tryToBuyCard(String s, int i) {
        System.out.println("Trying to buy card " + s);
        if (currentPlayer.canAfford(cardShop.getAvailableCards().get(i))) {
            System.out.println("Buying card " + s + ": " + cardShop.getAvailableCards().get(i).getName() + "!");
            currentPlayer.changeEnergy(-cardShop.getAvailableCards().get(i).getCost());
            currentPlayer.addCard(cardShop.buyCard(i));
            System.out.println("Your total energy is now " + currentPlayer.getEnergy());
        } else {
            System.out.println("Can't afford this card");
        }
    }

    // MODIFIES: this
    // EFFECTS: rerolls the shop if the current player has enough energy to pay the reroll cost
    private void tryToReroll() {
        System.out.println("Trying to reroll");
        if (currentPlayer.getEnergy() >= Shop.REROLL_COST) {
            System.out.println("Rerollling!");
            cardShop.reroll();
        } else {
            System.out.println("Can't reroll: not enough energy");
        }
    }

    // EFFECTS: display all player health, VPs, Energy, and owned cards
    private void displayPlayers() {
        System.out.println("--- Player statuses ---");
        for (Player player : playersInGame) {
            System.out.println("Player " + (player.getPlayerNumber() + 1) + ": ");
            System.out.print("Health: " + player.getHealth() + " ");
            System.out.print("VPs: " + player.getVictoryPoints() + " ");
            System.out.print("Energy: " + player.getEnergy() + " \n");
            for (Card card : player.getOwnedCards()) {
                System.out.println("\t" + card.getName() + ": " + card.getEffectsText());
            }
        }
        System.out.println("-----------------------");
    }

    // EFFECTS: Displays every card in the shop that is available to buy with their name, cost, type, and effects
    private void displayShop() {
        System.out.println("Here's what is available in the shop:");
        for (int i = 0; i < Shop.SHOP_SIZE; i++) {
            System.out.print((i + 1) + ": " + cardShop.getAvailableCards().get(i).getName() + " ");
            System.out.print("Cost: " + cardShop.getAvailableCards().get(i).getCost() + " ");
            if (cardShop.getAvailableCards().get(i).getIsKeep()) {
                System.out.print("Type: KEEP ");
            } else {
                System.out.print("Type: DISCARD ");
            }
            System.out.print("Effects: " + cardShop.getAvailableCards().get(i).getEffectsText() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: resets values for the start of the next round. Ensures the correct number of dice are used and that the
    //          scanner works
    private void resetValues() {
        input = new Scanner(System.in);
        allDice = new DieCollection(currentPlayer.getDiceAmount());
    }

    // MODIFIES: this
    // EFFECTS: displays what the current player rolled and resolves all energy dice
    private void resolveDice() {
        printDiceResults();
        // resolve ones
        // resolve twos
        // resolve threes
        // resolve attacks
        // resolve health
        resolveEnergy();
    }

    // MODIFIES: this
    // EFFECTS: gives the current player energy equal to the number of Energy he rolled and displays their current
    //          amount of energy
    private void resolveEnergy() {
        currentPlayer.changeEnergy(allDice.getNumberOfEnergies());
        System.out.println("Player " + (currentPlayerNumber + 1) + " has " + currentPlayer.getEnergy() + " energy!");
    }

    // EFFECTS: displays all values of dice rolled
    private void printDiceResults() {
        System.out.print("You rolled: ");
        System.out.print(allDice.getNumberOfOnes() + " x [1], ");
        System.out.print(allDice.getNumberOfTwos() + " x [2], ");
        System.out.print(allDice.getNumberOfThrees() + " x [3], ");
        System.out.print(allDice.getNumberOfAttacks() + " x [attack], ");
        System.out.print(allDice.getNumberOfHeals() + " x [health], ");
        System.out.print("and " + allDice.getNumberOfEnergies() + " x [energy] \n");
    }

    // MODIFIES: this
    // EFFECTS: exits the game if e input is read by making gameIsRunning false
    private void askIfWantToExit(String str) {
        if (str.equals("e")) {
            System.out.println("Exiting game...");
            gameIsRunning = false;
        }
    }

    // REQUIRES: input is an integer
    // MODIFIES: this
    // EFFECTS: asks for the number of players in game and adds that many players to the game
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
            } catch (InputMismatchException e) {
                System.out.println("That wasn't an integer");
            }
        }
        addPlayers();
    }

    // MODIFIES: this
    // EFFECTS: displays how many players there are in game and adds them to the game
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

    // EFFECTS: returns true if there is only one player in game
    private boolean gameIsOver() {
        return numPlayers <= 1;
    }

    // EFFECTS: roll the current player's dice
    private void diceRollPhase() {
        rollDiceCollection();
        // Rerolling to be implemented
    }

    // MODIFIES: this
    // EFFECTS: roll all dice in current diceCollection
    private void rollDiceCollection() {
        System.out.println("Rolling dice!");
        allDice.rollAllDice();
    }

    // EFFECTS: checks for y/n input and returns true/false respectively
    private boolean yesNoInput() {
        boolean checkingForValidInput = true;
        boolean result = false;

        while (checkingForValidInput) {
            Scanner input = new Scanner(System.in);
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("n")) {
                checkingForValidInput = false;
                result = false;
            } else if (command.equals("y")) {
                checkingForValidInput = false;
                result = true;
            } else {
                System.out.println("Invalid  response");
            }
        }
        return result;
    }
}
