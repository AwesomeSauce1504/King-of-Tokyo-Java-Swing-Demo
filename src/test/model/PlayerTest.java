package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player p1;
    private Card c1;
    private Card c2;

    @BeforeEach
    void setupTests() {
        p1 = new Player(0);
        c1 = new Card("CardName1", 5, "EffectsText1", true);
        c2 = new Card("CardName2", 3, "EffectsText2", true);
    }

    @Test
    void canAffordTest() {
        assertFalse(p1.canAfford(c1));
        p1.changeEnergy(c1.getCost());
        assertTrue(p1.canAfford(c1));
    }

    @Test
    void changeEnergyTest() {
        p1.changeEnergy(15);
        assertEquals(15, p1.getEnergy());
    }

    @Test
    void addCardTest() {
        p1.addCard(c1);
        List<Card> expectedOwnedCards = new ArrayList<Card>();
        expectedOwnedCards.add(c1);
        assertEquals(expectedOwnedCards, p1.getOwnedCards());
        p1.addCard(c2);
        expectedOwnedCards.add(c2);
        assertEquals(expectedOwnedCards, p1.getOwnedCards());
    }
}