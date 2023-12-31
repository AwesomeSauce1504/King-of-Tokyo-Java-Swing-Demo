package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        resetValues();
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

    // MODIFIES: this
    // EFFECTS: resolves the current player VP dice
    public void resolveVPDice() {
        resolveOneDiceType(1, allDice.getNumberOfOnes());
        resolveOneDiceType(2, allDice.getNumberOfTwos());
        resolveOneDiceType(3, allDice.getNumberOfThrees());
    }

    // MODIFIES: this
    // EFFECTS: give VPs to current player if they rolled 3 of a kind, +1 for each additional die of that kind
    public void resolveOneDiceType(int dieValue, int numOfDice) {
        if (numOfDice >= 3) {
            numOfDice -= 3;
            currentPlayer.changeVPs(dieValue);
            currentPlayer.changeVPs(numOfDice);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds health to current player equal to the number of HEAL they rolled if they are not in Tokyo
    public void resolveHealDice() {
        if (!currentPlayer.isInTokyo()) {
            currentPlayer.changeHealth(allDice.getNumberOfHeals());
        }
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

    // MODIFIES: this
    // EFFECTS: clears all cards owned by all players and log the event
    public void clearAllPlayerOwnedCards() {
        for (Player player : playersInGame) {
            player.getOwnedCards().clear();
        }
        EventLog.getInstance().logEvent(new Event("All player cards cleared."));
    }

    // EFFECTS: return true if a player in game is dead
    public Player identifyFirstDeadPlayer() {
        for (Player player : playersInGame) {
            if (player.getHealth() <= 0) {
                return player;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: removes all players who are dead from playersInGame and updates the player count
    public void removeEliminatedPlayers() {
        Player firstDeadPlayer = identifyFirstDeadPlayer();
        while (firstDeadPlayer != null) {
            playersInGame.remove(firstDeadPlayer);
            firstDeadPlayer = identifyFirstDeadPlayer();
        }
    }

    // Created based on the JsonSerializationDemo WorkRoom toJson method
    // EFFECTS: returns this as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("players", playersInGameToJson());
        json.put("power card deck", powerCardDeck.toJson());
        json.put("card shop", cardShop.toJson());
        json.put("number of players", numPlayers);
        json.put("current player number", currentPlayerNumber);
        json.put("all dice", allDice.toJson());
        // NOTE: currentPlayer is not stored, it is just found via current player number index

        return json;
    }

    // Created based on the JsonSerializationDemo WorkRoom toJson method
    // EFFECTS: returns playersInGame as a JSONArray
    public JSONArray playersInGameToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : playersInGame) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: return true if a player has over 20 VPs or there is one (or less) players left
    public boolean gameIsOver() {
        if (playersInGame.size() <= 1) {
            return true;
        }
        for (Player p : playersInGame) {
            if (p.getVictoryPoints() >= Player.MAX_VP) {
                return true;
            }
        }
        return false;
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumPlayers() {
        return playersInGame.size();
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

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setCardShop(Shop cardShop) {
        this.cardShop = cardShop;
    }

    public void setPowerCardDeck(Deck powerCardDeck) {
        this.powerCardDeck = powerCardDeck;
    }

    public void setAllDice(DieCollection allDice) {
        this.allDice = allDice;
    }

    // REQUIRES: i < playersInGame.size()
    public void setCurrentPlayer(int i) {
        this.currentPlayer = playersInGame.get(i);
    }

}
