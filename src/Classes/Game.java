package Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    static List<Player> players = new ArrayList<>();

    public Game(List<Player> players) {
        this.players = players;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static void addPlayers(Player player) {
        players.add(player);
    }
}
