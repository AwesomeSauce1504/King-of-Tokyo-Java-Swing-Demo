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
}
