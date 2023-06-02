package Classes;

import java.util.List;

//Created this temporary Class to test random functions
// you can ignore thsi class for the meantime

public class Temp {
    public static void main(String[] args) {

        Deck theCardDeck = new Deck(108);
        theCardDeck.initialDeck(); // filled up a new card deck

        System.out.println("\n 1. FILL THE DECK WITH CARDS");
        theCardDeck.printDeck(); // just to check the cards in the new deck
        theCardDeck.shuffleDeck(); // shuffle the cards;
        System.out.println();

        System.out.println("\n 2. NOW THE DECK HAVE BEEN SHUFFLED ");
        theCardDeck.printDeck(); // print the deck once more to check if the shuffle method worked.
        System.out.println();

        System.out.println("\n 3. SETTING UP HUMAN PLAYERS");
        Game firstGame = new Game(); // Creating a new game
        firstGame.setUpHumanPlayers(4); // setting up human players
        System.out.println();

        System.out.println("\n 4. DISTRIBUTE INITIAL PLAYER CARDS");
        firstGame.distributeInitialCardsToPlayers(); //distributes initial cards to players
        firstGame.printPlayer(); // print to check if player have 7 cards each.
        System.out.println();

        System.out.println("\n 5. PRINTED TO CHECK IF THE DISTRIBUTED CARDS ARE REMOVED FROM THE DECK");
        theCardDeck.printDeck();

        List<Card> discardDeck = firstGame.getDiscardDeck();

        System.out.println();
        System.out.println("\n ---------LAY ONE INITIAL CARD------------------");
        firstGame.layFirstCard();
        firstGame.printDiscardDeck(); //show discard deck on the console

        System.out.println();

        firstGame.playerToPlay();
        firstGame.acceptPlayersInput();
        firstGame.printPlayer();
        firstGame.printDiscardDeck();
        firstGame.playerToPlay();
        firstGame.acceptPlayersInput();
        firstGame.printPlayer();
        firstGame.printDiscardDeck();


    }
}


