package Classes;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static Classes.Game.*;

public class App {
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private int figureNr;
    //private Figure figure;
    public int size;
    // Konstruktor
    // input wird verwendet um Daten vom Benutzer einzulesen (verwendet scanner)
    // output wird verwendet um Text auf der Konsole auszugeben (verwendet sysout)
    public App(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }


    //die Gameloop
    public void Run() {
        initialize();
        printState();

        while (!exit) {
            readUserInput(Game.currentPlayer());
            updateState();
            printState();
        }
    }

    private void initialize() {
        Deck theCardDeck = new Deck(108);
        theCardDeck.initialDeck(); // filled up a new card deck
        theCardDeck.shuffleDeck(); // shuffle the cards;

        Game firstGame = new Game(); // Creating a new game
        firstGame.setUpPlayers(4); // setting up human players

        firstGame.distributeInitialCardsToPlayers(); //distributes initial cards to players
        firstGame.printPlayer(); // printing each player's 7 cards (initial player's hand) on the console.

        List<Card> discardDeck = firstGame.getDiscardDeck(); //creating a discard deck
        firstGame.layFirstCard(); // laying the first card on the discard deck
        System.out.println("LET THE GAMES BEGIN!!!");
        firstGame.printDiscardDeck(); //printing the discard deck on the console.
    }

    private void readUserInput(Player player) {
        playerToPlay(); // alert the players: whose turn it is to play
        currentPlayer().playerEntersCardToPlay();
        while (!isPlayedCardValid()) {
            System.out.println("Your input is invalid. Try again!");
            currentPlayer().playerEntersCardToPlay();
        }
        acceptPlayersInput(); // current player inputs card
        isPlayedCardValid(); // check if played card is valid:
        printDiscardDeck(); // print the discard deck on the console.
        checkNextTurn();
    }

    private void updateState() {
        //TODO: Benutzereingaben verarbeiten

    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
//        if (exit == true) {
//            System.out.println("Goodbye!");
//        } else if (figure != null) {
//            output.println(figure);
//        }
    }

}