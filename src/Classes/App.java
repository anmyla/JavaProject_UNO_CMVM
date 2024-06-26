package Classes;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

import static Classes.DB.recordWinnerOfRoundInDB1;
import static Classes.Database.recordWinnerOfRoundInDB;
import static Classes.Game.*;
import static Classes.Player.*;

public class App {
    private final Scanner input;
    private final PrintStream output;
    public static boolean exit = false;

    protected static boolean tournamentWinner = false;


    public static void setExit(boolean exit) {
        App.exit = exit;
    }

    public void setTournamentWinner(boolean overAllWinner) {
        this.tournamentWinner = overAllWinner;
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
        while (!tournamentWinner && !exit) { //this will allow further rounds until there is a tournamentWinner
            printState();
            while (!isThereAWinnerOfThisRound && !exit) {
                readUserInput(Game.currentPlayer());
                updateState();
                printState();
            }
            checkIfSomeoneReached500Points();

            if (!tournamentWinner) {
                playAnotherRound();
            } else {
                setExit(true);
                return;
            }
        }
    }

    private void initialize() {
        System.out.println("LET THE GAMES BEGIN!!!");
        Deck theCardDeck = new Deck(108);
        Game newGame = new Game(); // Creating a new game
        theCardDeck.initialDeck(); // filled up a new card deck
        theCardDeck.shuffleDeck(); // shuffle the cards;
        setPlayers(); // setting up human/bot players
        sessionID();
        newGame.database();
        System.out.println("--------------------------------" + " ROUND  1 " + "------------------------------------");
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
        }
    }

    private void updateState() {
        if (checkWinnerOfCurrentRound().isWinner()) {
            System.out.println(SUNNY + "Congratulations " + getWinnerOfThisRound().getName() + " you won this round!" + RESET);
            computePoints();
            recordWinnerOfRoundInDB();
            recordWinnerOfRoundInDB1();
            winnerOfThisRound.setWinner(false); //reset to default value
        } else {
            checkNextTurn();
        }

        setBlocked(false); //reset to default
    }

    private void printState() {
        if (!exit && !isThereAWinnerOfThisRound) {
            printDiscardDeck();
        }
    }

    private void checkIfSomeoneReached500Points() {
        boolean ultimateWinner = false;
        for (Player p : players) {
            if (p.getPlayerPoints() > 500) {
                System.out.println("We have an ultimate winner!");
                System.out.println(p.getName() + ", congratulations! you are our UNO MASTER!!!");
                ultimateWinner = true;
                break;
            } else {
                ultimateWinner = false;
            }
        }
        setTournamentWinner(ultimateWinner);
    }

    public void startANewRound() {
        distributeInitialCardsToPlayers(); //distributes initial cards to players
        printPlayer(); // printing each player's 7 cards (initial player's hand) on the console.
        layFirstCard(); // laying the first card on the discard deck
        if (!exit) {
            chooseFirstPlayer();
        }
        setRoundWinnerPoints(0);
    }

    public void playAnotherRound() {
        int counter = getRound(); //Counter for Rounds

        if (!exit) {
            System.out.println("Round " + counter + " is complete!");
        }
        for (Player player : players) { // all cards left in each player's had will be discarded
            for (Card card : player.playersHand) {
                discardDeck.add(card);
            }
        }

        Iterator<Card> iterator = discardDeck.iterator(); // this will get all the cards from the discard pile and added it to the cardDeck
        while (iterator.hasNext()) {
            Card card = iterator.next();
            iterator.remove();
            cardDeck.add(card);
        }

        cardDeck.shuffleDeck(); // shuffle the card deck before starting another round
        winnerOfThisRound = null; // reset this to default value;

        counter = counter + 1;
        setRound(counter);

        setIsThereAWinnerOfThisRound(false);

        String anotherRound;
        if (!exit) {
            do {
                System.out.println("Would you like to play another round? Y/N: ");
                anotherRound = input.nextLine().toUpperCase();

                if (anotherRound.equals("HELP")) {
                    callHelp();
                }
            } while (!anotherRound.equals("N") && !anotherRound.equals("Y")
                    && !anotherRound.equals("EXIT"));

            if (anotherRound.equals("Y")) {
                System.out.println("--------------------------------" + "ROUND " + counter + "-------------------------------------");
                startANewRound();
            } else if (anotherRound.equals("N")) {
                System.out.println(SUNNY + playerPoints().getName() + " has the most points so she's the overall winner!" + RESET);
                setExit(true);
                System.out.println("You decided to end the tournament here...");
            }else if (anotherRound.equals("EXIT")){

                System.out.println(SUNNY + playerPoints().getName() + " has the most points so she's the overall winner!" + RESET);
                setExit(true);
                System.out.println("Exiting the game....");
            }
        }
    }
}