package Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static Classes.Deck.drawOneCard;
import static Classes.Game.currentPlayer;

// Importing required classes
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


//Created this temporary Class to test random functions
// you can ignore this class for the meantime

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


/*
    public static Card humanMakesAMove() {
        Player currentPlayer = currentPlayer();
        String[] validCardValues = Card.getFaceValueCollections();
        List<String> validCardValuesList = new ArrayList<>(Arrays.asList(validCardValues));

        String[] validCardColors = Card.getColorValueCollections();
        List<String> validCardColorList = new ArrayList<>(Arrays.asList(validCardColors));

        Scanner input = new Scanner(System.in);
        Card cardToPlay;
        String inputCard;
        String cardColor = null;
        String cardValue = null;
        String call = null;

        System.out.println("WHAT'S YOUR MOVE?:"); // Player enters both color and value
        inputCard = input.nextLine().toUpperCase();
        if (inputCard.contains("UNO")) {
            call = "UNO";
        }

        cardColor = String.valueOf(inputCard.charAt(0));
        cardValue = String.valueOf(inputCard.charAt(1));

        while (!validCardColorList.contains(cardColor) || !validCardValuesList.contains(cardValue)) {
            System.out.println("Sorry, there's no such card. Please enter a valid card: ");
            inputCard = input.nextLine().toUpperCase();
            if (inputCard.contains("UNO")) {
                call = "UNO";
            }
            cardColor = String.valueOf(inputCard.charAt(0));
            cardValue = String.valueOf(inputCard.charAt(1));
        }

        cardToPlay = new Card(cardColor, cardValue);

        if(call != null) {
            if (cardToPlay != null && currentPlayer.playersHand.size() == 2 && call.equals("UNO")) {
                currentPlayer.setUno(true);
                System.out.println("UNO!!!!!!!");
            } else if (cardToPlay != null && currentPlayer.playersHand.size() > 2 && call.equals("UNO")) {
                System.out.println("That was a foul call. You have to draw a card!");
                drawOneCard();
                currentPlayer.setUno(false);
            }
        }
        else {
            currentPlayer.setUno(false);
        }
        return cardToPlay;
    }


*/
}


