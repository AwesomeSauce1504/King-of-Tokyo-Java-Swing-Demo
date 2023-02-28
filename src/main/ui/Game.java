package ui;

import model.*;

import java.util.InputMismatchException;
import java.util.Scanner;

// Main game ui application
public class Game {
    private Scanner input;
    private boolean gameIsRunning;
    private GameManager gm;

    // EFFECTS: begins the game
    public Game() {
        gm = new GameManager();
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: initializes all main game components, the players (after getting total players in game),
    //          and runs the game loop
    private void runGame() {
        gameIsRunning = true;
        initPlayers();

        while (gameIsRunning) {
            input = new Scanner(System.in);
            gm.resetValues();
            displayPlayers();
            System.out.println("It is Player " + (gm.getCurrentPlayerNumber() + 1) + "'s turn!");
            diceRollPhase();
            resolveDice();
            displayShop();
            askIfPlayerWantsToShop();
            askIfWantToExit();
            if (gm.gameIsOver()) {
                gameIsRunning = false;
            }
            gm.setUpNextPlayer();

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
        if (gm.getCurrentPlayer().canAfford(gm.getCardShop().getAvailableCards().get(i))) {
            System.out.println("Buying card " + s + ": " + gm.getCardShop().getAvailableCards().get(i).getName() + "!");
            gm.getCurrentPlayer().changeEnergy(-gm.getCardShop().getAvailableCards().get(i).getCost());
            gm.getCurrentPlayer().addCard(gm.getCardShop().buyCard(i));
            System.out.println("Your total energy is now " + gm.getCurrentPlayer().getEnergy());
        } else {
            System.out.println("Can't afford this card");
        }
    }

    // MODIFIES: this
    // EFFECTS: rerolls the shop if the current player has enough energy to pay the reroll cost
    private void tryToReroll() {
        System.out.println("Trying to reroll");
        if (gm.getCurrentPlayer().getEnergy() >= Shop.REROLL_COST) {
            System.out.println("Rerollling!");
            gm.getCurrentPlayer().changeEnergy(-2);
            gm.getCardShop().reroll();
        } else {
            System.out.println("Can't reroll: not enough energy");
        }
    }

    // EFFECTS: display all player health, VPs, Energy, and owned cards
    private void displayPlayers() {
        System.out.println("--- Player statuses ---");
        for (Player player : gm.getPlayersInGame()) {
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
            System.out.print((i + 1) + ": " + gm.getCardShop().getAvailableCards().get(i).getName() + " ");
            System.out.print("Cost: " + gm.getCardShop().getAvailableCards().get(i).getCost() + " ");
            if (gm.getCardShop().getAvailableCards().get(i).getIsKeep()) {
                System.out.print("Type: KEEP ");
            } else {
                System.out.print("Type: DISCARD ");
            }
            System.out.print("Effects: " + gm.getCardShop().getAvailableCards().get(i).getEffectsText() + "\n");
        }
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
        gm.resolveEnergy();
        System.out.println("Player " + (gm.getCurrentPlayerNumber() + 1) + " has "
                + gm.getCurrentPlayer().getEnergy() + " energy!");
    }

    // EFFECTS: displays all values of dice rolled
    private void printDiceResults() {
        System.out.print("You rolled: ");
        System.out.print(gm.getAllDice().getNumberOfOnes() + " x [1], ");
        System.out.print(gm.getAllDice().getNumberOfTwos() + " x [2], ");
        System.out.print(gm.getAllDice().getNumberOfThrees() + " x [3], ");
        System.out.print(gm.getAllDice().getNumberOfAttacks() + " x [attack], ");
        System.out.print(gm.getAllDice().getNumberOfHeals() + " x [health], ");
        System.out.print("and " + gm.getAllDice().getNumberOfEnergies() + " x [energy] \n");
    }

    // MODIFIES: this
    // EFFECTS: exits the game if e input is read by making gameIsRunning false
    private void askIfWantToExit() {
        System.out.println("Would you like to exit game? (Enter e to exit, any other key to continue playing)");
        String str = input.next();
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
        int result = 0;
        while (initNotDone) {
            try {
                Scanner initPlayersInput = new Scanner(System.in);
                System.out.println("How many players are in this game? (Must be an integer greater than 1)");
                result = initPlayersInput.nextInt();
                if (result > 1) {
                    initNotDone = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("That wasn't an integer");
            }
        }
        System.out.println("You have chosen a " + gm.getNumPlayers() + " player game!");
        System.out.println("Game start!");
        gm.addNPlayers(result);
    }

    // MODIFIES: this
    // EFFECTS: roll the current player's dice
    private void diceRollPhase() {
        rollDiceCollection();
        // Rerolling to be implemented
    }

    // MODIFIES: this
    // EFFECTS: roll all dice in current diceCollection
    private void rollDiceCollection() {
        System.out.println("Rolling dice!");
        gm.getAllDice().rollAllDice();
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
