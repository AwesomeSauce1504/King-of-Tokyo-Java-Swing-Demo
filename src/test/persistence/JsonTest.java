package persistence;

import model.Card;
import model.Die;
import model.Player;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Created based on JsonTest in JsonSerializationDemo
public class JsonTest {
    protected void checkPlayer(Player p, int playerNumber, int health, int victoryPoints,
                               int energy, boolean isInTokyo, int diceAmount, int numCardsOwned) {
        assertEquals(playerNumber, p.getPlayerNumber());
        assertEquals(health, p.getHealth());
        assertEquals(victoryPoints, p.getVictoryPoints());
        assertEquals(energy, p.getEnergy());
        assertEquals(isInTokyo, p.isInTokyo());
        assertEquals(diceAmount, p.getDiceAmount());
        assertEquals(numCardsOwned, p.getOwnedCards().size());
    }

    protected void checkCard(Card c, String name, int cost, String effectsText, boolean isKeep) {
        assertEquals(name, c.getName());
        assertEquals(cost, c.getCost());
        assertEquals(effectsText, c.getEffectsText());
        assertEquals(isKeep, c.getIsKeep());
    }

    protected void checkDie(Die d, int value) {
        assertEquals(value, d.getValue());
    }

}
