package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    void resolveVPDiceTest() {
        gm1.addNPlayers(1);
        gm1.resetValues();
        assertEquals(0, gm1.getCurrentPlayer().getVictoryPoints());
        for (int i = 0; i < 100; i++) {
            int prevVPValue = gm1.getCurrentPlayer().getVictoryPoints();
            int expectedVPValue = 0;
            gm1.getAllDice().rollAllDice();

            int numOfOnes = gm1.getAllDice().getNumberOfOnes();
            int numOfTwos = gm1.getAllDice().getNumberOfTwos();
            int numOfThrees = gm1.getAllDice().getNumberOfThrees();
            if (numOfOnes >= 3) {
                numOfOnes -= 3;
                expectedVPValue += 1 + numOfOnes;
            }
            if (numOfTwos >= 3) {
                numOfTwos -= 3;
                expectedVPValue += 2 + numOfTwos;
            }
            if (numOfThrees >= 3) {
                numOfThrees -= 3;
                expectedVPValue += 3 + numOfThrees;
            }
            gm1.resolveVPDice();
            assertEquals(prevVPValue + expectedVPValue, gm1.getCurrentPlayer().getVictoryPoints());
        }
    }

    @Test
    void resolveOneDiceTypeTest() {
        gm1.addNPlayers(1);
        gm1.resetValues();
        assertEquals(0, gm1.getCurrentPlayer().getVictoryPoints());
        gm1.resolveOneDiceType(2, 33);
        assertEquals(2 + 30, gm1.getCurrentPlayer().getVictoryPoints());
    }

    @Test
    void resolveHealDiceTest() {
        gm1.addNPlayers(1);
        gm1.resetValues();
        gm1.getCurrentPlayer().setHealth(5);
        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            int prevHealthValue = gm1.getCurrentPlayer().getHealth();
            gm1.getAllDice().rollAllDice();
            int numOfHeals = gm1.getAllDice().getNumberOfHeals();
            gm1.resolveHealDice();

            if (numOfHeals + prevHealthValue >= 10) {
                assertEquals(10, gm1.getCurrentPlayer().getHealth());
            } else {
                assertEquals(numOfHeals + prevHealthValue, gm1.getCurrentPlayer().getHealth());
            }
            gm1.getCurrentPlayer().setHealth(rand.nextInt(11));
        }

        gm1.getCurrentPlayer().setInTokyo(true);
        
        for (int i = 0; i < 100; i++) {
            int prevHealthValue = gm1.getCurrentPlayer().getHealth();
            gm1.getAllDice().rollAllDice();
            gm1.resolveHealDice();
            int newHealthValue = gm1.getCurrentPlayer().getHealth();

            assertEquals(newHealthValue, prevHealthValue);
            gm1.getCurrentPlayer().setHealth(rand.nextInt(11));
        }

    }

    @Test
    void addNPlayersTest() {
        assertEquals(0, gm1.getNumPlayers());
        gm1.addNPlayers(3);
        assertEquals(3, gm1.getNumPlayers());
        assertEquals(0, gm1.getPlayersInGame().get(0).getPlayerNumber());
        assertEquals(2, gm1.getPlayersInGame().get(2).getPlayerNumber());
    }

    @Test
    void gameIsOverTest() {
        assertTrue(gm1.gameIsOver());
        gm1.addNPlayers(1);
        assertTrue(gm1.gameIsOver());
        gm1.addNPlayers(1);
        assertFalse(gm1.gameIsOver());
        gm1.getPlayersInGame().get(0).setVictoryPoints(20);
        assertTrue(gm1.gameIsOver());
        gm1.getPlayersInGame().get(1).setVictoryPoints(20);
        assertTrue(gm1.gameIsOver());
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

    @Test
    void checkIfAnyPlayersEliminatedTest() {
        gm1.addNPlayers(4);
        gm1.removeEliminatedPlayers();
        assertEquals(4, gm1.getNumPlayers());
        gm1.getPlayersInGame().get(2).setHealth(0);
        gm1.getPlayersInGame().get(0).setHealth(-33);
        gm1.removeEliminatedPlayers();
        assertEquals(2, gm1.getNumPlayers());
    }

    @Test
    void getNumPlayersTest() {
        assertEquals(0, gm1.getNumPlayers());
        gm1.addNPlayers(3);
        assertEquals(3, gm1.getNumPlayers());
    }

}
