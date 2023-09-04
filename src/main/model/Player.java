package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a player, with their health, victory points, energy, cards, and whether they are in Tokyo
public class Player {
    public static final int MAX_HEALTH = 10;
    public static final int MAX_VP = 20;
    public static final int STARTING_DICE_AMOUNT = 8;
    private int health;
    private int victoryPoints;
    private int energy;
    private boolean isInTokyo;
    private int playerNumber;
    private List<Card> ownedCards;
    private int diceAmount;
    // private List<Die> playerDice;
    // private boolean isMyTurn;

    // EFFECTS: player is initialized with 10 health, 0 energy, 8 dice, and isInTokyo false
    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.health = 10;
        this.victoryPoints = 0;
        this.energy = 0;
        this.isInTokyo = false;
        this.ownedCards = new ArrayList<>();
        this.diceAmount = STARTING_DICE_AMOUNT;
    }

    // EFFECTS: player is initialized with specified playerNumber, health, victoryPoints, energy, isInTokyo,
    //          ownedCards, and diceAmount
    public Player(int playerNumber, int health, int victoryPoints, int energy, boolean isInTokyo,
                  List<Card> ownedCards, int diceAmount) {
        this.playerNumber = playerNumber;
        this.health = health;
        this.victoryPoints = victoryPoints;
        this.energy = energy;
        this.isInTokyo = isInTokyo;
        this.ownedCards = ownedCards;
        this.diceAmount = diceAmount;
    }

    // EFFECTS: returns true if Player has enough energy to buy Card c
    //          If the player can afford, log the event as a purchase
    public boolean canAfford(Card c) {
        if (energy >= c.getCost()) {
            EventLog.getInstance().logEvent(new Event("Player " + (this.playerNumber + 1)
                    + " bought Card: " + c.getName()));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: change energy by amount, prevents going below 0 energy
    public void changeEnergy(int amount) {
        energy += amount;
        if (energy < 0) {
            energy = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: change VPs by amount, prevents going below 0 VPs
    public void changeVPs(int amount) {
        victoryPoints += amount;
        if (victoryPoints < 0) {
            victoryPoints = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds consumed card to ownedCards
    public void addCard(Card c) {
        ownedCards.add(c);
    }

    // Created based on the JsonSerializationDemo WorkRoom toJson method
    // EFFECTS: returns this as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArrayOfCards = new JSONArray();
        json.put("player number", playerNumber);
        json.put("health", health);
        json.put("victory points", victoryPoints);
        json.put("energy", energy);
        json.put("is in tokyo", isInTokyo);
        for (Card c : ownedCards) {
            jsonArrayOfCards.put(c.toJson());
        }
        json.put("owned cards", jsonArrayOfCards);
        json.put("dice amount", diceAmount);
        return json;
    }

    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Card> getOwnedCards() {
        return ownedCards;
    }

    public int getDiceAmount() {
        return diceAmount;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int i) {
        victoryPoints = i;
    }

    public boolean isInTokyo() {
        return isInTokyo;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDiceAmount(int diceAmount) {
        this.diceAmount = diceAmount;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setInTokyo(boolean inTokyo) {
        isInTokyo = inTokyo;
    }

    public void setOwnedCards(List<Card> ownedCards) {
        this.ownedCards = ownedCards;
    }
}
