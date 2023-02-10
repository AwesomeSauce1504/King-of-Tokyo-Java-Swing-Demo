package model;

// Represents an individual card with its name, cost, effects, and keep/discard type
public class Card {
    private String name;
    private int cost;
    private String effectsText;
    private boolean isKeep;

    public Card(String name, int cost, String effectsText, boolean isKeep) {
        this.name = name;
        this.cost = cost;
        this.effectsText = effectsText;
        this.isKeep = isKeep;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getEffectsText() {
        return effectsText;
    }

    public boolean getIsKeep() {
        return isKeep;
    }
}
