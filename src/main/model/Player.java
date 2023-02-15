package model;

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
        this.ownedCards = new ArrayList<Card>();
        this.diceAmount = STARTING_DICE_AMOUNT;
    }

    // EFFECTS: returns true if Player has enough energy to buy Card c
    public boolean canAfford(Card c) {
        return energy >= c.getCost();
    }

    // MODIFIES: this
    // EFFECTS: change energy by amount
    public void changeEnergy(int amount) {
        energy += amount;
    }

    // MODIFIES: this
    // EFFECTS: adds consumed card to ownedCards
    public void addCard(Card c) {
        ownedCards.add(c);
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
}
