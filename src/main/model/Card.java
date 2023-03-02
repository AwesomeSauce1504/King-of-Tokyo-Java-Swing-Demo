package model;

import org.json.JSONObject;

// Represents an individual card with its name, cost, effects, and keep/discard type
public class Card {
    private String name;
    private int cost;
    private String effectsText;
    private boolean isKeep;

    // EFFECTS: Creates a card with a name, cost to buy, effects, and if it is a keep card or not
    public Card(String name, int cost, String effectsText, boolean isKeep) {
        this.name = name;
        this.cost = cost;
        this.effectsText = effectsText;
        this.isKeep = isKeep;
    }

    // !!!
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cost", cost);
        json.put("effects text", effectsText);
        json.put("is keep", isKeep);
        return json;
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
