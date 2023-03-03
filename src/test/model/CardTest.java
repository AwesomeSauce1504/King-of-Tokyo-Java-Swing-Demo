package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    private Card c1;
    private Card c2;

    @BeforeEach
    void setupTests() {
        this.c1 = new Card("cardname1", 5, "These are effects", true);
        this.c2 = new Card("cardname2", 17, "Card two's effects", false);
    }

    @Test
    void constructorTest() {
        assertEquals("cardname1", c1.getName());
        assertEquals("cardname2", c2.getName());
        assertEquals(5, c1.getCost());
        assertEquals(17, c2.getCost());
        assertEquals("These are effects", c1.getEffectsText());
        assertEquals("Card two's effects", c2.getEffectsText());
        assertTrue(c1.getIsKeep());
        assertFalse(c2.getIsKeep());
    }
}
