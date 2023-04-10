package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameManagerTest {
    private GameManager gm1;

    @BeforeEach
    void setupTests() {
        gm1 = new GameManager();
    }

    @Test
    void constructorTest() {
        List<Card> allCardsThatShouldBeInDeck = new ArrayList<>();
        Collections.addAll(allCardsThatShouldBeInDeck, Deck.ALL_EXISTING_CARDS);

        for (Card c : gm1.getPowerCardDeck().getCardsInDeck()) {
            assertTrue(allCardsThatShouldBeInDeck.contains(c));
        }

        assertEquals(3, gm1.getCardShop().getAvailableCards().size());
        for (Card c : gm1.getCardShop().getAvailableCards()) {
            assertTrue(allCardsThatShouldBeInDeck.contains(c));
        }
        assertEquals(0, gm1.getPlayersInGame().size());
        assertEquals(0, gm1.getCurrentPlayerNumber());
        assertEquals(0, gm1.getAllDice().getDiceList().size());
    }

    @Test
    void setUpNextPlayerNotRolloverTest() {
        gm1.addNPlayers(3);
        gm1.setUpNextPlayer();
        assertEquals(1, gm1.getCurrentPlayerNumber());
        assertEquals(gm1.getPlayersInGame().get(1), gm1.getCurrentPlayer());
    }

    @Test
    void setUpNextPlayerRolloverTest() {
        gm1.addNPlayers(3);
        gm1.setCurrentPlayerNumber(2);
        gm1.setCurrentPlayer(2);
        assertEquals(2, gm1.getCurrentPlayerNumber());
        assertEquals(gm1.getPlayersInGame().get(2), gm1.getCurrentPlayer());
        gm1.setUpNextPlayer();
        assertEquals(0, gm1.getCurrentPlayerNumber());
        assertEquals(gm1.getPlayersInGame().get(0), gm1.getCurrentPlayer());
    }

    @Test
    void resetValuesTest() {
        gm1.addNPlayers(2);
        gm1.resetValues();
        assertEquals(gm1.getCurrentPlayer().getDiceAmount(), gm1.getAllDice().getDiceList().size());
    }

    @Test
    void resolveEnergyTest() {
        gm1.addNPlayers(1);
        gm1.resetValues();
        assertEquals(0, gm1.getCurrentPlayer().getEnergy());
        for (int i = 0; i < 100; i++) {
            int prevEnergyValue = gm1.getCurrentPlayer().getEnergy();
            gm1.getAllDice().rollAllDice();
            int numOfEnergies = gm1.getAllDice().getNumberOfEnergies();
            gm1.resolveEnergy();
            assertEquals(prevEnergyValue + numOfEnergies, gm1.getCurrentPlayer().getEnergy());
        }
    }

    @Test
    void addNPlayersTest() {
        assertEquals(0, gm1.getNumPlayers());
        gm1.addNPlayers(3);
        assertEquals(3, gm1.getNumPlayers());
    }

    @Test
    void gameIsOverTest() {
        assertTrue(gm1.gameIsOver());
        gm1.addNPlayers(1);
        assertTrue(gm1.gameIsOver());
        gm1.addNPlayers(1);
        assertFalse(gm1.gameIsOver());
    }

    @Test
    void clearAllPlayerOwnedCardsTest() {
        Card c1 = new Card("Card 1", 1, "card 1 text", true);
        Card c2 = new Card("Card 2", 4, "card 2 text", false);
        Card c3 = new Card("Card 3", 3, "card 3 text", false);
        Card c4 = new Card("Card 4", 2, "card 4 text", true);
        gm1.addNPlayers(4);
        gm1.getPlayersInGame().get(0).addCard(c1);
        gm1.getPlayersInGame().get(1).addCard(c2);
        gm1.getPlayersInGame().get(1).addCard(c3);
        gm1.getPlayersInGame().get(3).addCard(c4);
        assertEquals(1, gm1.getPlayersInGame().get(0).getOwnedCards().size());
        assertEquals(2, gm1.getPlayersInGame().get(1).getOwnedCards().size());
        assertEquals(0, gm1.getPlayersInGame().get(2).getOwnedCards().size());
        assertEquals(1, gm1.getPlayersInGame().get(3).getOwnedCards().size());
        gm1.clearAllPlayerOwnedCards();
        assertEquals(0, gm1.getPlayersInGame().get(0).getOwnedCards().size());
        assertEquals(0, gm1.getPlayersInGame().get(1).getOwnedCards().size());
        assertEquals(0, gm1.getPlayersInGame().get(2).getOwnedCards().size());
        assertEquals(0, gm1.getPlayersInGame().get(3).getOwnedCards().size());
    }

}
