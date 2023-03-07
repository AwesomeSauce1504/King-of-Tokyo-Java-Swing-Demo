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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
