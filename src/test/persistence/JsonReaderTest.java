package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Created based on JsonReaderTest in JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameManager gm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderTwoPlayerGame() {
        JsonReader reader = new JsonReader("./data/testReaderTwoPlayerGame");
        try {
            GameManager gm = reader.read();
            assertEquals(2, gm.getNumPlayers());
            assertEquals(0, gm.getCurrentPlayerNumber());

            List<Player> players = gm.getPlayersInGame();
            assertEquals(2, players.size());
            Player p0 = players.get(0);
            checkPlayer(p0, 0, 10, 0, 0, false, 8, 0);
            Player p1 = players.get(1);
            checkPlayer(p1, 1, 10, 0, 0, false, 8, 0);

            Deck powerCardDeck = gm.getPowerCardDeck();
            Card c4 = powerCardDeck.getCardsInDeck().get(0);
            Card c5 = powerCardDeck.getCardsInDeck().get(1);
            Card c6 = powerCardDeck.getCardsInDeck().get(2);
            Card c7 = powerCardDeck.getCardsInDeck().get(3);
            checkCard(c4, "card 4", 4, "card 4 text", false);
            checkCard(c5, "card 5", 5, "card 5 text", false);
            checkCard(c6, "card 6", 6, "card 6 text", true);
            checkCard(c7, "card 7", 7, "card 7 text", true);

            Shop cardShop = gm.getCardShop();
            Card c1 = cardShop.getAvailableCards().get(0);
            Card c2 = cardShop.getAvailableCards().get(1);
            Card c3 = cardShop.getAvailableCards().get(2);
            checkCard(c1, "card 1", 1, "card 1 text", false);
            checkCard(c2, "card 2", 2, "card 2 text", true);
            checkCard(c3, "card 3", 3, "card 3 text", false);

            DieCollection dieCollection = gm.getAllDice();
            checkDie(dieCollection.getDiceList().get(0), 0);
            checkDie(dieCollection.getDiceList().get(1), 1);
            checkDie(dieCollection.getDiceList().get(2), 2);
            checkDie(dieCollection.getDiceList().get(3), 3);
            checkDie(dieCollection.getDiceList().get(4), 4);
            checkDie(dieCollection.getDiceList().get(5), 5);
            checkDie(dieCollection.getDiceList().get(6), 3);
            checkDie(dieCollection.getDiceList().get(7), 0);

            Player currentPlayer = gm.getCurrentPlayer();
            assertEquals(currentPlayer, gm.getPlayersInGame().get(gm.getCurrentPlayerNumber()));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame");
        try {
            GameManager gm = reader.read();
            assertEquals(4, gm.getNumPlayers());
            assertEquals(2, gm.getCurrentPlayerNumber());

            List<Player> players = gm.getPlayersInGame();
            assertEquals(4, players.size());
            Player p0 = players.get(0);
            checkPlayer(p0, 0, 10, 10, 0, false, 8, 2);
            Card p0c0 = p0.getOwnedCards().get(0);
            Card p0c1 = p0.getOwnedCards().get(1);
            checkCard(p0c0, "Made in a Lab", 2,
                    "When purchasing cards you can peek at and purchase the top card of the deck", true);
            checkCard(p0c1, "Energy Hoarder", 3,
                    "You gain 1 VP for every 6 energy you have at the end of your turn", true);
            Player p1 = players.get(1);
            checkPlayer(p1, 1, 3, 19, 2, false, 8, 0);
            Player p2 = players.get(2);
            checkPlayer(p2, 2, 6, 2, 4, true, 9, 1);
            Card p2c0 = p2.getOwnedCards().get(0);
            checkCard(p2c0, "Camouflage", 3,
                    "If you take damage, roll a die for each damage point. " +
                            "On a HEALTH, you do not take that damage point.", true);
            Player p3 = players.get(3);
            checkPlayer(p3, 3, 9, 13, 6, true, 4, 3);
            Card p3c0 = p3.getOwnedCards().get(0);
            Card p3c1 = p3.getOwnedCards().get(1);
            Card p3c2 = p3.getOwnedCards().get(2);
            checkCard(p3c0, "Background Dweller", 4,
                    "You can always roll any 3s you have", true);
            checkCard(p3c1, "Plot Twist", 3,
                    "Change one die to any result. Discard when used", true);
            checkCard(p3c2, "Complete Destruction", 3,
                    "If you roll 1-2-3-HEAL-ATK-ENERGY gain 9 VP in addition to the regular results",
                    true);

            Deck powerCardDeck = gm.getPowerCardDeck();
            Card pcd1 = powerCardDeck.getCardsInDeck().get(0);
            Card pcd2 = powerCardDeck.getCardsInDeck().get(1);
            Card pcd3 = powerCardDeck.getCardsInDeck().get(2);
            Card pcd4 = powerCardDeck.getCardsInDeck().get(3);
            Card pcd5 = powerCardDeck.getCardsInDeck().get(4);
            checkCard(pcd1, "Death From Above", 5,
                    "+2 VPs and take control of Tokyo if you don't already control it", false);
            checkCard(pcd2, "Evacuation Orders", 7,
                    "All other Monsters lose 5 VPs", false);
            checkCard(pcd3, "National Guard", 3, "+2 VPs and take 2 damage", false);
            checkCard(pcd4, "Shrink Ray", 6,
                    "When you deal damage, give each Monster damaged a Shrink token. " +
                            "A monster rolls one less die for each Shrink token. " +
                            "A monster can get rid of a Shrink token with a HEALTH " +
                            "instead of using it to heal damage", true);
            checkCard(pcd5, "Background Dweller", 4,
                    "You can always roll any 3s you have", true);

            Shop cardShop = gm.getCardShop();
            Card c1 = cardShop.getAvailableCards().get(0);
            Card c2 = cardShop.getAvailableCards().get(1);
            Card c3 = cardShop.getAvailableCards().get(2);
            checkCard(c1, "Eater of the Dead", 4,
                    "Gain 3 VP every time a Monster's health goes to 0", true);
            checkCard(c2, "Made in a Lab", 2,
                    "When purchasing cards you can peek at and purchase the top card of the deck", true);
            checkCard(c3, "Plot Twist", 3,
                    "Change one die to any result. Discard when used", true);

            DieCollection dieCollection = gm.getAllDice();
            checkDie(dieCollection.getDiceList().get(0), 0);
            checkDie(dieCollection.getDiceList().get(1), 3);
            checkDie(dieCollection.getDiceList().get(2), 0);
            checkDie(dieCollection.getDiceList().get(3), 2);
            checkDie(dieCollection.getDiceList().get(4), 3);
            checkDie(dieCollection.getDiceList().get(5), 1);
            checkDie(dieCollection.getDiceList().get(6), 5);
            checkDie(dieCollection.getDiceList().get(7), 4);
            checkDie(dieCollection.getDiceList().get(8), 2);

            Player currentPlayer = gm.getCurrentPlayer();
            assertEquals(currentPlayer, gm.getPlayersInGame().get(gm.getCurrentPlayerNumber()));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
