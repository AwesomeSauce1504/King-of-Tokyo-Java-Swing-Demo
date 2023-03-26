package model;

import org.json.JSONObject;

import java.util.Random;

// Represents an individual die with its value
public class Die {
    public static final int ONE_VP = 0;
    public static final int TWO_VP = 1;
    public static final int THREE_VP = 2;
    public static final int ATTACK = 3;
    public static final int HEAL = 4;
    public static final int ENERGY = 5;
    public static final String ONE_VP_TEXT = "1";
    public static final String TWO_VP_TEXT = "2";
    public static final String THREE_VP_TEXT = "3";
    public static final String ATTACK_TEXT = "ATTACK";
    public static final String HEAL_TEXT = "HEAL";
    public static final String ENERGY_TEXT = "ENERGY";

    private int value;
    private Random rand = new Random();

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

    // Created based on the JsonSerializationDemo WorkRoom toJson method
    // EFFECTS: returns this as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        return json;
    }

    // EFFECTS: returns inputted die result as its value as a string
    public String textValueOfDiceResult() {
        if (this.value == Die.ONE_VP) {
            return ONE_VP_TEXT;
        } else if (value == Die.TWO_VP) {
            return TWO_VP_TEXT;
        } else if (value == Die.THREE_VP) {
            return THREE_VP_TEXT;
        } else if (value == Die.ATTACK) {
            return ATTACK_TEXT;
        } else if (value == Die.HEAL) {
            return HEAL_TEXT;
        } else {
            return ENERGY_TEXT;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
