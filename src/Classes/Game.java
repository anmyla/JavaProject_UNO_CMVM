package Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    static List<Player> players = new ArrayList<>();
    Deck cardDeck = new Deck(108);

    public Game(List<Player> players) {
        this.players = players;
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static void addPlayers(Player player) {
        players.add(player);
    }

    private static List<String> playerNames() {
        List<String> names = new ArrayList<>();
        for (Player player : players) {
            names.add(player.getName());
        }
        return names;
    }

    public void setUpPlayers(int humanPlayers) {
        //Just human players for now. A simple method to collect player names and add it to the List<Players>
        Scanner playerInput = new Scanner(System.in);

        for (int i = 0; i < humanPlayers; i++) {
            System.out.println("Please enter a name for PLAYER " + (i + 1) + ": ");
            String name = playerInput.nextLine();

            players.add(new Human(name));
        }
    }

    public void distributeInitialCardsToPlayers() {
        cardDeck.initialDeck();
        cardDeck.shuffleDeck();
        for (Player player : players) {
            player.setPlayerPoints(0);
            player.setPlayerInitialCards(cardDeck.distributeInitialCards());
        }

    }


    public static void printPlayer() {
        //Just to check players and their cards
        for (Player player : players) {
            System.out.print(player.toString());
            System.out.println();
        }
    }
}

