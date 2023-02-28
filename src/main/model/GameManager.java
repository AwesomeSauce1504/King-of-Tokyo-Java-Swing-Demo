package model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    private Deck powerCardDeck;
    private Shop cardShop;
    private List<Player> playersInGame;
    private int numPlayers;
    private int currentPlayerNumber;
    private Player currentPlayer;
    private DieCollection allDice;

    // EFFECTS: initializes a new GameManager with a powerCardDeck, cardShop based on the powerCardDeck,
    //          an empty list of players, the first player as player 0, an empty DieCollection, and 0 total players
    public GameManager() {
        this.powerCardDeck = new Deck();
        this.cardShop = new Shop(powerCardDeck);
        this.playersInGame = new ArrayList<Player>();
        this.currentPlayerNumber = 0;
        this.allDice = new DieCollection(0);
        this.numPlayers = 0;
    }

    // REQUIRES: currentPlayerNumber >= 0
    // MODIFIES: this
    // EFFECTS: set the currentPlayer to the next player if not all players have gone in a round,
    //          else reset the player turn to the first player
    public void setUpNextPlayer() {
        if (currentPlayerNumber < playersInGame.size() - 1) {
            currentPlayerNumber++;
            currentPlayer = playersInGame.get(currentPlayerNumber);
        } else {
            currentPlayerNumber = 0;
            currentPlayer = playersInGame.get(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: resets values for the start of the next round. Ensures the correct number of dice are used.
    public void resetValues() {
        allDice = new DieCollection(currentPlayer.getDiceAmount());
    }

    // MODIFIES: this
    // EFFECTS: gives the current player energy equal to the number of Energy he rolled and displays their current
    //          amount of energy
    public void resolveEnergy() {
        currentPlayer.changeEnergy(allDice.getNumberOfEnergies());
    }

    // REQUIRES: n >= 0
    // MODIFIES: this
    // EFFECTS: displays how many players there are in game and adds them to the game
    public void addNPlayers(int n) {
        for (int i = 0; i < n; i++) {
            playersInGame.add(new Player(i));
            numPlayers++;
        }
        this.currentPlayer = playersInGame.get(currentPlayerNumber);
    }

    // EFFECTS: returns true if there is only one player in game
    public boolean gameIsOver() {
        return numPlayers <= 1;
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public List<Player> getPlayersInGame() {
        return playersInGame;
    }

    public DieCollection getAllDice() {
        return allDice;
    }

    public Shop getCardShop() {
        return cardShop;
    }

    public Deck getPowerCardDeck() {
        return powerCardDeck;
    }

    public void setCurrentPlayerNumber(int i) {
        this.currentPlayerNumber = i;
    }

    // REQUIRES: i < playersInGame.size()
    public void setCurrentPlayer(int i) {
        this.currentPlayer = playersInGame.get(i);
    }
}
