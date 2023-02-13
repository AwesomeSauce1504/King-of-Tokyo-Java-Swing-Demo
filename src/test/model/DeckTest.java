package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    private Deck deck1;

    @BeforeEach
    void setupTests() {
        this.deck1 = new Deck();
    }

    @Test
    void constructorTest() {
        for (Card c : Deck.ALL_EXISTING_CARDS) {
            assertTrue(deck1.getCardsInDeck().contains(c));
        }
    }

    @Test
    void drawCardTest() {
        Card topCard = deck1.getCardsInDeck().get(0);
        int initialDeckSize = deck1.getCardsInDeck().size();
        Card result = deck1.drawCard();
        assertEquals(topCard, result);
        assertEquals(initialDeckSize - 1, deck1.getCardsInDeck().size());
    }
}
