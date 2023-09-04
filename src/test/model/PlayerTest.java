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
        p1.changeEnergy(0);
        assertEquals(0, p1.getEnergy());
        p1.changeEnergy(15);
        assertEquals(15, p1.getEnergy());
        p1.changeEnergy(-100);
        assertEquals(0, p1.getEnergy());
    }

    @Test
    void changeVPsTest() {
        p1.changeVPs(0);
        assertEquals(0, p1.getVictoryPoints());
        p1.changeVPs(5);
        assertEquals(5, p1.getVictoryPoints());
        p1.changeVPs(-6);
        assertEquals(0, p1.getVictoryPoints());
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

    // DISCLAIMER: tests below here for getters/setters are because these methods are used in the Game class in ui and
    //             are thus not tested because they are in ui

    @Test
    void getHealthTest() {
        assertEquals(Player.MAX_HEALTH, p1.getHealth());

    }

    @Test
    void getDiceAmountTest() {
        assertEquals(Player.STARTING_DICE_AMOUNT, p1.getDiceAmount());
    }

    @Test
    void getPlayerNumberTest() {
        assertEquals(0, p1.getPlayerNumber());
        Player p2 = new Player(16);
        assertEquals(16, p2.getPlayerNumber());
    }

    @Test
    void getVictoryPointsTest() {
        assertEquals(0, p1.getVictoryPoints());
        p1.setVictoryPoints(15);
        assertEquals(15, p1.getVictoryPoints());
    }

}