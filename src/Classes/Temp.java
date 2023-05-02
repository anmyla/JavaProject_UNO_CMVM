package Classes;


import java.util.HashMap;
import java.util.List;

//Created this temporary Class to test random functions
public class Temp {
    public static void main(String[] args) {
        Deck newCardDeck = new Deck(108);
        newCardDeck.initialDeck(); // filled up a new card deck
        newCardDeck.printDeck(); // just to check the cards in the new deck
        newCardDeck.shuffleDeck(); // shuffle the cards;
        System.out.println();
        newCardDeck.printDeck(); // print the deck once more to check if the shuffle method worked.

        Human p1 = new Human("player 1");
        Human p2 = new Human("player 2");
        Game uno = new Game(Game.getPlayers());
        uno.addPlayers(p1);
        uno.addPlayers(p2);
    }
}
