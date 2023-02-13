package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a collection (deck) of card objects
public class Deck {
    public static final Card[] ALL_EXISTING_CARDS = {
            new Card("Name1", 1, "TEST1", false),
            new Card("Name2", 2, "TEST2", false),
            new Card("Name3", 3, "TEST3", true),
            new Card("Name4", 4, "TEST4", false),
            new Card("Name5", 5, "TEST5", true),
            new Card("Name6", 6, "TEST6", false),
            new Card("Name7", 7, "TEST7", true),
            new Card("Name8", 8, "TEST8", false),
            new Card("Name9", 9, "TEST9", true)
    };

    private List<Card> cardsInDeck;

    // EFFECTS: create a new Deck with all existing cards in it, shuffled
    public Deck() {
        this.cardsInDeck = new ArrayList<Card>();
        Collections.addAll(cardsInDeck, ALL_EXISTING_CARDS);
        Collections.shuffle(cardsInDeck);
    }

    // REQUIRES: Deck is not empty
    // MODIFIES: this
    // EFFECTS: return the first item in the Deck and remove it from the Deck
    public Card drawCard() {
        Card topCard = cardsInDeck.get(0);
        cardsInDeck.remove(0);
        return topCard;
    }

    public List<Card> getCardsInDeck() {
        return this.cardsInDeck;
    }
}
