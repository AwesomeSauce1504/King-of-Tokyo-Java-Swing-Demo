package model;

import java.util.ArrayList;
import java.util.List;

// A collection of Die objects
public class DieCollection {
    private List<Die> diceList;
    private int numberOfOnes;
    private int numberOfTwos;
    private int numberOfThrees;
    private int numberOfAttacks;
    private int numberOfHeals;
    private int numberOfEnergies;

    // Create a DieCollection containing numOfDice dice and set the count of roll results to 0
    public DieCollection(int numOfDice) {
        diceList = new ArrayList<Die>();

        for (int i = 0; i < numOfDice; i++) {
            this.diceList.add(new Die());
        }
        numberOfOnes = 0;
        numberOfTwos = 0;
        numberOfThrees = 0;
        numberOfAttacks = 0;
        numberOfHeals = 0;
        numberOfEnergies = 0;
    }

    public void rollAllDice() {
        for (Die d : diceList) {
            d.rollDie();
        }
        updateDiceResults();
    }

    private void updateDiceResults() {
        numberOfOnes = 0;
        numberOfTwos = 0;
        numberOfThrees = 0;
        numberOfAttacks = 0;
        numberOfHeals = 0;
        numberOfEnergies = 0;

        for (Die d : diceList) {
            if (d.getValue() == Die.ONE_VP) {
                numberOfOnes++;
            } else if (d.getValue() == Die.TWO_VP) {
                numberOfTwos++;
            } else if (d.getValue() == Die.THREE_VP) {
                numberOfThrees++;
            } else if (d.getValue() == Die.ATTACK) {
                numberOfAttacks++;
            } else if (d.getValue() == Die.HEAL) {
                numberOfHeals++;
            } else if (d.getValue() == Die.ENERGY) {
                numberOfEnergies++;
            }
        }
    }

    public List<Die> getDiceList() {
        return diceList;
    }

    public int getNumberOfOnes() {
        return numberOfOnes;
    }

    public int getNumberOfTwos() {
        return numberOfTwos;
    }

    public int getNumberOfThrees() {
        return numberOfThrees;
    }

    public int getNumberOfAttacks() {
        return numberOfAttacks;
    }

    public int getNumberOfHeals() {
        return numberOfHeals;
    }

    public int getNumberOfEnergies() {
        return numberOfEnergies;
    }
}