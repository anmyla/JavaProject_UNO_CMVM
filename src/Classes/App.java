package Classes;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static Classes.Game.*;
import static Classes.Player.*;

public class App {
    private final Scanner input;
    private final PrintStream output;
    private static boolean exit = false;
    public int size;

    public static void setExit(boolean exit) {
        App.exit = exit;
    }

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
        setPlayers(); // setting up human/bot players

        distributeInitialCardsToPlayers(); //distributes initial cards to players
        printPlayer(); // printing each player's 7 cards (initial player's hand) on the console.

        List<Card> discardDeck = firstGame.getDiscardDeck(); //creating a discard deck

        layFirstCard(); // laying the first card on the discard deck

        System.out.println("LET THE GAMES BEGIN!!!");
        chooseFirstPlayer();
    }

    private void readUserInput(Player player) {
        playerToPlay(); // alert the players: whose turn it is to play
        thisPlayerIsBlocked();
        if (!isCardValid() && !isBlocked()) {
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
        if(checkWinner().isWinner()) {
            System.out.println("Congratulations " + getWinnerOfThisRound().getName() + " you won this round!");
            System.out.println("You get a total of " + computePoints() + " points this round" );
            setExit(true);
        } else {
            checkNextTurn();
        }
    }

    private void printState() {
        if (!exit) {
            printDiscardDeck();
        }
    }
}