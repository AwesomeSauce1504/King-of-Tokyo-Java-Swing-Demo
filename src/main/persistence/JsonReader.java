package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Created based on the JsonSerializationDemo JsonReader class
// Represents a reader that reads gameManager from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads gameManager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GameManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses gameManager from JSON object and returns it
    private GameManager parseGameManager(JSONObject jsonObject) {
        GameManager gm = new GameManager();
        gm.getPowerCardDeck().getCardsInDeck().clear();
        addNumPlayers(gm, jsonObject);
        addCurrentPlayerNumber(gm, jsonObject);
        addPlayers(gm, jsonObject);
        addAllDice(gm, jsonObject);
        addPowerCardDeck(gm, jsonObject);
        addCardShop(gm, jsonObject);
        gm.getCardShop().setDeck(gm.getPowerCardDeck());
        gm.setCurrentPlayer(gm.getCurrentPlayerNumber());
        return gm;
    }


    // MODIFIES: gm
    // EFFECTS: parses number of players from JSON object and adds it to gameManager
    private void addNumPlayers(GameManager gm, JSONObject jsonObject) {
        int numPlayers = jsonObject.getInt("number of players");
        gm.setNumPlayers(numPlayers);
    }


    // MODIFIES: gm
    // EFFECTS: parses current player number from JSON object and adds it to gameManager
    private void addCurrentPlayerNumber(GameManager gm, JSONObject jsonObject) {
        int currentPlayerNumber = jsonObject.getInt("current player number");
        gm.setCurrentPlayerNumber(currentPlayerNumber);
    }


    // MODIFIES: gm
    // EFFECTS: parses all dice from JSON object and adds them to gameManager DieCollection
    private void addAllDice(GameManager gm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("all dice");
        for (Object json : jsonArray) {
            JSONObject nextDie = (JSONObject) json;
            addDie(gm, nextDie);
        }
    }


    // MODIFIES: gm
    // EFFECTS: parses a die from JSON object and adds them to gameManager dice
    private void addDie(GameManager gm, JSONObject jsonObject) {
        int value = jsonObject.getInt("value");
        Die d = new Die();
        d.setValue(value);
        gm.getAllDice().getDiceList().add(d);
    }


    // MODIFIES: gm
    // EFFECTS: parses cards in shop from JSON object and adds them to gameManager cardShop
    private void addCardShop(GameManager gm, JSONObject jsonObject) {
        Shop shop = new Shop();
        JSONArray jsonArray = jsonObject.getJSONArray("card shop");
        for (Object json : jsonArray) {
            JSONObject nextCardInShop = (JSONObject) json;
            addCardToShop(shop, nextCardInShop);
        }

        gm.setCardShop(shop);
    }


    // MODIFIES: gm
    // EFFECTS: parses a card in the card shop from JSON object and adds it to shop
    private void addCardToShop(Shop shop, JSONObject jsonObject) {
        Card c = parseCard(jsonObject);

        shop.getAvailableCards().add(c);
    }


    // MODIFIES: gm
    // EFFECTS: parses deck cards from JSON object and adds it to gameManager
    private void addPowerCardDeck(GameManager gm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("power card deck");
        for (Object json : jsonArray) {
            JSONObject nextCardInDeck = (JSONObject) json;
            addCardToDeck(gm, nextCardInDeck);
        }
    }

    // MODIFIES: gm
    // EFFECTS: parses a card in the deck from JSON object and adds it to deck
    private void addCardToDeck(GameManager gm, JSONObject jsonObject) {
        Card c = parseCard(jsonObject);

        gm.getPowerCardDeck().getCardsInDeck().add(c);
    }

    // MODIFIES: gm
    // EFFECTS: parses players from JSON object and adds them to gameManager
    private void addPlayers(GameManager gm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(gm, nextPlayer);
        }
    }

    // MODIFIES: gm
    // EFFECTS: parses player from JSON object and adds it to gameManager
    private void addPlayer(GameManager gm, JSONObject jsonObject) {
        int playerNumber = jsonObject.getInt("player number");
        Player p = new Player(playerNumber);
        int victoryPoints = jsonObject.getInt("victory points");
        boolean isInTokyo = jsonObject.getBoolean("is in tokyo");
        int diceAmount = jsonObject.getInt("dice amount");
        int health = jsonObject.getInt("health");
        List<Card> ownedCards = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("owned cards");

        int energy = jsonObject.getInt("energy");
        p.setVictoryPoints(victoryPoints);
        p.setInTokyo(isInTokyo);
        p.setDiceAmount(diceAmount);
        p.setHealth(health);
        p.setOwnedCards(ownedCards);
        p.setEnergy(energy);

        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCardToPlayer(p, nextCard);
        }

        gm.getPlayersInGame().add(p);
    }

    // MODIFIES: gm
    // EFFECTS: parses card from JSON object and adds it to player
    private void addCardToPlayer(Player player, JSONObject jsonObject) {
        Card c = parseCard(jsonObject);
        player.getOwnedCards().add(c);
    }

    // EFFECTS: parses a jsonObject representing a card and returns the card
    private Card parseCard(JSONObject jsonObject) {
        int cost = jsonObject.getInt("cost");
        String name = jsonObject.getString("name");
        boolean isKeep = jsonObject.getBoolean("is keep");
        String effectsText = jsonObject.getString("effects text");
        Card c = new Card(name, cost, effectsText, isKeep);
        return c;
    }
}
