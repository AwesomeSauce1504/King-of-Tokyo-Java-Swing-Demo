package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a collection (deck) of card objects
public class Deck {
    public static final Card[] ALL_EXISTING_CARDS = {
            new Card("Alien Metabolism", 3, "Buying cards costs you 1 less energy", true),
            new Card("Complete Destruction", 3,
                    "If you roll 1-2-3-HEAL-ATK-ENERGY "
                            + "gain 9 VP in addition to the regular results", true),
            new Card("Energy Hoarder", 3,
                    "You gain 1 VP for every 6 energy you have at the end of your turn", true),
            new Card("Eater of the Dead", 4,
                    "Gain 3 VP every time a Monster's health goes to 0", true),
            new Card("Burrowing", 5,
                    "While you are in Tokyo, deal 1 extra damage."
                            + "When you yield Tokyo, deal 1 damage to the monster taking it", true),
            new Card("Energize", 8, "+9 Energy", false),
            new Card("Background Dweller", 4, "You can always roll any 3s you have", true),
            new Card("Evacuation Orders", 7, "All other Monsters lose 5 VPs", false),
            new Card("National Guard", 3, "+2 VPs and take 2 damage", false),
            new Card("Acid Attack", 6,
                    "Deal 1 extra damage each turn (even when you don't otherwise attack)", true),
            new Card("Urbavore", 4,
                    "Gain 1 extra VP when beginning the turn in Tokyo."
                            + "Deal 1 extra damage when dealing any damage from Tokyo", true),
            new Card("Commuter Train", 4, "+2 VPs", false),
            new Card("Death From Above", 5,
                    "+2 VPs and take control of Tokyo if you don't already control it", false),
            new Card("Heal", 3, "Heal 2 damage", false),
            new Card("Plot Twist", 3, "Change one die to any result. Discard when used", true),
            new Card("Camouflage", 3, "If you take damage, roll a die for each damage point."
                    + "On a HEALTH, you do not take that damage point.", true),
            new Card("Frenzy", 7,
                    "When you purchase this card take another turn immediately after this one", false),
            new Card("Made in a Lab", 2,
                    "When purchasing cards you can peek at and purchase the top card of the deck", true),
            new Card("Alpha Monster", 5, "Gain 1 VP when you attack", true),
            new Card("Extra Head", 7, "You get 1 extra die", true),
            new Card("Rapid Healing", 3, "Spend 2 Energy at any time to heal 1 damage", true),
            new Card("Shrink Ray", 6,
                    "When you deal damage, give each Monster damaged a Shrink token. "
                            + "A monster rolls one less die for each Shrink token. "
                            + "A monster can get rid of a Shrink token with a HEALTH"
                            + "instead of using it to heal damage", true)
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
