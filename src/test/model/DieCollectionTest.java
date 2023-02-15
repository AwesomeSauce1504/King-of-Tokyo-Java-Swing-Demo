package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DieCollectionTest {
    private DieCollection dc1;

    @BeforeEach
    void setupTests() {
        this.dc1 = new DieCollection(8);
    }

    @Test
    void constructorTest() {
        assertEquals(8, dc1.getDiceList().size());
        assertEquals(0, dc1.getNumberOfOnes());
        assertEquals(0, dc1.getNumberOfTwos());
        assertEquals(0, dc1.getNumberOfThrees());
        assertEquals(0, dc1.getNumberOfAttacks());
        assertEquals(0, dc1.getNumberOfHeals());
        assertEquals(0, dc1.getNumberOfEnergies());
    }

    @Test
    void rollAllDiceTest() {
        int seenNumberOfOnes = 0;
        int seenNumberOfTwos = 0;
        int seenNumberOfThrees = 0;
        int seenNumberOfAttacks = 0;
        int seenNumberOfHeals = 0;
        int seenNumberOfEnergies = 0;
        for (int i = 0; i < 1000; i++) {
            dc1.rollAllDice();
            seenNumberOfOnes += dc1.getNumberOfOnes();
            seenNumberOfTwos += dc1.getNumberOfTwos();
            seenNumberOfThrees += dc1.getNumberOfThrees();
            seenNumberOfAttacks += dc1.getNumberOfAttacks();
            seenNumberOfHeals += dc1.getNumberOfHeals();
            seenNumberOfEnergies += dc1.getNumberOfEnergies();
        }
        assertTrue(seenNumberOfOnes > 0);
        assertTrue(seenNumberOfTwos > 0);
        assertTrue(seenNumberOfThrees > 0);
        assertTrue(seenNumberOfAttacks > 0);
        assertTrue(seenNumberOfHeals > 0);
        assertTrue(seenNumberOfEnergies > 0);
    }
}
