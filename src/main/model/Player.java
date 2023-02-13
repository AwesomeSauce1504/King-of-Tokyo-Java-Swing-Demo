package model;

import java.util.ArrayList;
import java.util.List;

// Represents a player, with their health, victory points, energy, cards, and whether they are in Tokyo
public class Player {
    public static final int MAX_HEALTH = 10;
    public static final int MAX_VP = 20;
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
        this.diceAmount = 8;
    }

    // EFFECTS: returns true if Player has enough energy to buy Card c
    public boolean canAfford(Card c) {
        return energy >= c.getCost();
    }

    public void changeEnergy(int amount) {
        energy += amount;
    }

    public void addCard(Card c) {
        ownedCards.add(c);
    }

    /*public boolean getIsInTokyo() {
        return isInTokyo;
    }*/

    /*public void changeHealth(int amount) {
        health += amount;
    }*/

    /* public void changeIsInTokyo(boolean state) {
        isInTokyo = state;
    } */

    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Card> getOwnedCards() {
        return ownedCards;
    }

    public void setOwnedCards(List<Card> ownedCards) {
        this.ownedCards = ownedCards;
    }

    public int getDiceAmount() {
        return diceAmount;
    }

    public void setDiceAmount(int diceAmount) {
        this.diceAmount = diceAmount;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
