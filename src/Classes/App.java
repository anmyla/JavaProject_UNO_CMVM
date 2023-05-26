package Classes;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static Classes.Game.*;
import static Classes.Player.isPlay;
import static Classes.Player.canPlay;

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
        chooseFirstPlayer();
    }

    private void readUserInput(Player player) {
        playerToPlay(); // alert the players: whose turn it is to play
        if (!isCardValid()) {
            canPlay();
            currentPlayersTurn();
            if (isPlay()) { //player DO NOT pass
                if (isPlayedCardValid()) {
                    acceptPlayersInput();
                }
            }
        }
    }


    private void updateState() {
        checkNextTurn();
        printDiscardDeck();
    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
    }

}