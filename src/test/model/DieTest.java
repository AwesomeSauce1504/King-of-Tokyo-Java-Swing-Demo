package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DieTest {
    private Die die1;

    @BeforeEach
    void setupTests() {
        die1 = new Die();
    }

    @Test
    void constructorTest() {
        assertEquals(0, die1.getValue());
    }

    @Test
    void rollDieTest() {
        for (int i = 0; i < 1000; i++) {
            assertTrue(0 <= die1.getValue() && die1.getValue() <= 5);
        }
    }

    @Test
    void textValueOfDiceResult() {
        die1.setValue(Die.ONE_VP);
        assertEquals(Die.ONE_VP_TEXT, die1.textValueOfDiceResult());
        die1.setValue(Die.TWO_VP);
        assertEquals(Die.TWO_VP_TEXT, die1.textValueOfDiceResult());
        die1.setValue(Die.THREE_VP);
        assertEquals(Die.THREE_VP_TEXT, die1.textValueOfDiceResult());
        die1.setValue(Die.ATTACK);
        assertEquals(Die.ATTACK_TEXT, die1.textValueOfDiceResult());
        die1.setValue(Die.HEAL);
        assertEquals(Die.HEAL_TEXT, die1.textValueOfDiceResult());
        die1.setValue(Die.ENERGY);
        assertEquals(Die.ENERGY_TEXT, die1.textValueOfDiceResult());
    }
}
