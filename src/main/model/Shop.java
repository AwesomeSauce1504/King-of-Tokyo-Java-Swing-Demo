package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// Represents the card shop, with a list of available cards to buy and a deck to refresh itself
public class Shop {
    private List<Card> availableCards;
    private Deck deck;
    public static final int SHOP_SIZE = 3;
    public static final int REROLL_COST = 2;

    // EFFECTS: set the shop's draw deck to deck and draw SHOP_SIZE cards and add them to availableCards
    public Shop(Deck deck) {
        this.availableCards = new ArrayList<>();
        this.deck = deck;
        for (int i = 0; i < SHOP_SIZE; i++) {
            availableCards.add(deck.drawCard());
        }
    }

    // EFFECTS: creates an empty shop with no deck
    // NOTE: must set deck and available cards manually
    //       This constructor is used for rebuilding the shop from save file
    public Shop() {
        this.availableCards = new ArrayList<>();
    }

    // REQUIRES: i < availableCards.size()
    // MODIFIES: this
    // EFFECTS: returns the card in availableCards at index i and replaces it with a new card from deck
    public Card buyCard(int i) {
        Card boughtCard = availableCards.get(i);
        availableCards.set(i, deck.drawCard());
        return boughtCard;
        // buy card i from the shop
        // should return the card
        // draw new card from deck and add it to shop in that position
    }

    // REQUIRES: deck.size() >= 3
    // MODIFIES: this
    // EFFECTS: empties availableCards and draws cards from deck to fill availableCards up to SHOP_SIZE
    public void reroll() {
        availableCards.clear();
        for (int i = 0; i < SHOP_SIZE; i++) {
            availableCards.add(deck.drawCard());
        }
    }

    // Created based on the JsonSerializationDemo WorkRoom toJson method
    // EFFECTS: returns this as a JSONArray
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card c: availableCards) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
