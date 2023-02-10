package model;

import java.util.Random;

// Represents an individual die with its value
public class Die {
    private int value;
    private Random rand = new Random();
    public static final int ONE_VP = 0;
    public static final int TWO_VP = 1;
    public static final int THREE_VP = 2;
    public static final int ATTACK = 3;
    public static final int HEAL = 4;
    public static final int ENERGY = 5;

    // EFFECTS: set the die to a default value of 0
    public Die() {
        this.value = 0;
    }

    // MODIFIES: this
    // EFFECTS: sets the die value to a random number from 0-5 and returns its new value
    public int rollDie() {
        this.value = rand.nextInt(6);
        return value;
    }

    public int getValue() {
        return value;
    }
}
