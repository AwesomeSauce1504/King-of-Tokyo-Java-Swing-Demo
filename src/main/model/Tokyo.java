package model;

import java.util.ArrayList;
import java.util.List;

public class Tokyo {
    private List<Player> playersInTokyo;
    private int maxPlayersInTokyo;

    public Tokyo(int numberOfPlayers) {
        this.playersInTokyo = new ArrayList<Player>();
        if (numberOfPlayers > 4) {
            this.maxPlayersInTokyo = 2;
        } else {
            this.maxPlayersInTokyo = 1;
        }
    }

    public void setMaxPlayersInTokyo(int numberOfPlayers) {

    }
}
