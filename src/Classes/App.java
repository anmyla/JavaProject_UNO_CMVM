package Classes;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static Classes.Database.recordWinnerOfRoundInDB;
import static Classes.Game.*;
import static Classes.Player.*;

public class App {
    private final Scanner input;
    private final PrintStream output;
    public static boolean exit = false;
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

        database();

        distributeInitialCardsToPlayers(); //distributes initial cards to players
        printPlayer(); // printing each player's 7 cards (initial player's hand) on the console.

        List<Card> discardDeck = firstGame.getDiscardDeck(); //creating a discard deck

        layFirstCard(); // laying the first card on the discard deck
        if (!exit) {
            System.out.println("LET THE GAMES BEGIN!!!");
            chooseFirstPlayer();
        }
    }

    private void readUserInput(Player player) {
        playerToPlay(); // alert the players: whose turn it is to play,
        // check if players has to take penalty cards or do a challenge
        // before moving on with the game
        checkIfThisPlayerIsBlocked(); // check if the players are blocked from moving on with the game

        if (!isCardValid() && !isBlocked()) {
            canPlay(); // check if player has valid card in his hand that he can play
            currentPlayersTurn(); // player can enter his move
            if (isPlay()) { //player has card in his hand that he can play
                if (!exit) {
                    if (isPlayedCardValid()) { // player's move is validated
                        acceptPlayersInput(); // finally remove played card from player's hand
                        // and add it to the top of the discard deck
                        // resets values that must be reset
                    }
                }
            }
        }
    }

    private void updateState() {
        if (checkWinner().isWinner()) {
            System.out.println("Congratulations " + getWinnerOfThisRound().getName() + " you won this round!");
            System.out.println("You get a total of " + computePoints() + " points this round");
            recordWinnerOfRoundInDB();
            setExit(true);
        } else {
            checkNextTurn();
        }
    }

    private void printState() {
        if (!exit) {
            printDiscardDeck();
        } else {
            System.out.println("Goodbye!");
        }
    }
}