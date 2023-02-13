package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {
    private Shop shop1;
    private Deck deck;
    private Card c1;
    private Card c2;
    private Card c3;
    private Card c4;
    private Card c5;
    private Card c6;

    @BeforeEach
    void setupTests() {
        deck = new Deck();
        c1 = deck.getCardsInDeck().get(0);
        c2 = deck.getCardsInDeck().get(1);
        c3 = deck.getCardsInDeck().get(2);
        c4 = deck.getCardsInDeck().get(3);
        c5 = deck.getCardsInDeck().get(4);
        c6 = deck.getCardsInDeck().get(5);
        shop1 = new Shop(deck);
    }

    @Test
    void constructorTest() {
        assertEquals(deck, shop1.getDeck());
        assertEquals(Shop.SHOP_SIZE, shop1.getAvailableCards().size());
        assertEquals(c1, shop1.getAvailableCards().get(0));
        assertEquals(c2, shop1.getAvailableCards().get(1));
        assertEquals(c3, shop1.getAvailableCards().get(2));
    }

    @Test
    void buyCardOneTest() {
        Card boughtCard = shop1.buyCard(0);
        assertEquals(c1, boughtCard);
        assertEquals(c4, shop1.getAvailableCards().get(0));
    }

    @Test
    void buyCardTwoTest() {
        Card boughtCard = shop1.buyCard(1);
        assertEquals(c2, boughtCard);
        assertEquals(c4, shop1.getAvailableCards().get(1));
    }

    @Test
    void buyCardThreeTest() {
        Card boughtCard = shop1.buyCard(2);
        assertEquals(c3, boughtCard);
        assertEquals(c4, shop1.getAvailableCards().get(2));
    }

    @Test
    void rerollTest() {
        shop1.reroll();
        assertEquals(c4, shop1.getAvailableCards().get(0));
        assertEquals(c5, shop1.getAvailableCards().get(1));
        assertEquals(c6, shop1.getAvailableCards().get(2));
    }
}
