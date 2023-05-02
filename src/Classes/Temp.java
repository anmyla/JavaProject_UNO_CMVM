package Classes;

//Created this temporary Class to test random functions
public class Temp {
    public static void main(String[] args) {

        Deck newCardDeck = new Deck(108);
        newCardDeck.initialDeck(); // filled up a new card deck

        System.out.println("\n 1. FILL THE DECK WITH CARDS");
        newCardDeck.printDeck(); // just to check the cards in the new deck
        newCardDeck.shuffleDeck(); // shuffle the cards;
        System.out.println();

        System.out.println("\n 2. NOW THE DECK HAVE BEEN SHUFFLED ");
        newCardDeck.printDeck(); // print the deck once more to check if the shuffle method worked.
        System.out.println();

        System.out.println("\n 3. SETTING UP HUMAN PLAYERS");
        Game newGame = new Game(Game.getPlayers()); // Creating a new game
        newGame.setUpPlayers(4); // setting up human players
        System.out.println();

        System.out.println("\n 3. DISTRIBUTE INITIAL PLAYER CARDS");
        newGame.distributeInitialCardsToPlayers(); //distributes initial cards to players
        newGame.printPlayer(); // print to check if player have 7 cards each.
    }
}

