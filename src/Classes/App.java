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

    protected boolean ultimateWinner = false;

    public static void setExit(boolean exit) {
        App.exit = exit;
    }

    public void setUltimateWinner(boolean overAllWinner) {
        this.ultimateWinner = overAllWinner;
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

        for(int i = 0; !ultimateWinner;  i++ ) {
            printState();
            while (!exit) {
                readUserInput(Game.currentPlayer());
                updateState();
                printState();
            }
        }
    }

    private void initialize() {
        Deck theCardDeck = new Deck(108);
        theCardDeck.initialDeck(); // filled up a new card deck
        theCardDeck.shuffleDeck(); // shuffle the cards;
        setPlayers(); // setting up human/bot players
        startANewRound();
    }

    private void readUserInput(Player player) {
        playerToPlay(); // alert the players: whose turn it is to play,
        // check if players has to take penalty cards or do a challenge
        // before moving on with the game
        if (!isBlocked()) { // check if the players are blocked from moving on with the game
            if (canPlayerMakeAMove()) { // check if player has valid card in his hand that he can play
                currentPlayersTurn();// player can enter his move
            }
            if (canMakeAMove && !exit) { //player has card to play in his hand, player made a move, and it did not exit
                if (isPlayedCardValid()) { // player's move is validated
                    acceptPlayersInput(); // finally remove played card from player's hand
                    // and add it to the top of the discard deck
                    // resets values that must be reset
                }
            }
        } else {
            System.out.println("Let's move on to the next player.");
        }
    }

    private void updateState() {
        if (checkWinner().isWinner()) {
            System.out.println("Congratulations " + getWinnerOfThisRound().getName() + " you won this round!");
            System.out.println("You get a total of " + computePoints() + " points this round");
            setExit(true);
            recordWinnerOfRoundInDB();
        } else {
            checkNextTurn();
        }
        setBlocked(false); //reset to default
    }

    private void printState() {
        if (!exit) {
            printDiscardDeck();
        }
    }

    private void checkIfSomeoneReached500Points(){
        boolean ultimateWinner = false;
        for (Player p : players) {
            if (p.getPlayerPoints() >= 500) {
                System.out.println("We have an ultimate winner!");
                System.out.println(p.getName() + ", congratulations! you are our UNO MASTER!!!");
                ultimateWinner = true;
                break;
            } else {
                ultimateWinner = false;
            }
        }
        setUltimateWinner(ultimateWinner);
    }

    public void startANewRound() {
        Game newGame = new Game(); // Creating a new game
        distributeInitialCardsToPlayers(); //distributes initial cards to players
        printPlayer(); // printing each player's 7 cards (initial player's hand) on the console.
        List<Card> discardDeck = newGame.getDiscardDeck(); //creating a discard deck
        layFirstCard(); // laying the first card on the discard deck
        if (!exit) {
            System.out.println("LET THE GAMES BEGIN!!!");
            chooseFirstPlayer();
        }

    }
}