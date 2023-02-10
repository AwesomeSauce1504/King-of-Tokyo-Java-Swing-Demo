package model;

import java.util.ArrayList;
import java.util.List;

// Represents a player, with their health, victory points, energy, cards, and whether they are in Tokyo
public class Player {
    public static final int MAX_HEALTH = 10;
    public static final int MAX_VP = 20;
    private int health;
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
        this.energy = 0;
        this.isInTokyo = false;
        this.ownedCards = new ArrayList<Card>();
        this.diceAmount = 8;
        /* this.playerDice = new ArrayList<Die>();
        for (int i = 0; i < 8; i++) {
            this.playerDice.add(new Die());
        } */
    }

    // EFFECTS: returns true if Player has enough energy to buy Card c
    public boolean canAfford(Card c) {
        return energy >= c.getCost();
    }

    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean getIsInTokyo() {
        return isInTokyo;
    }

    public void changeHealth(int amount) {
        health += amount;
    }

    public void changeEnergy(int amount) {
        energy += amount;
    }

    public void changeIsInTokyo(boolean state) {
        isInTokyo = state;
    }

    public List<Card> getOwnedCards() {
        return ownedCards;
    }

    public void addCard(Card c) {
        ownedCards.add(c);
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
}
