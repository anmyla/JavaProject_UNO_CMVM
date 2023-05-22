package Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    static List<Player> players = new ArrayList<>();
    Deck cardDeck = new Deck(108);
    static List<Card> discardDeck = new ArrayList<>();
    private static int turn = chooseFirstPlayer();
    private static boolean isClockwise = true;


    public void setUpPlayers(int humanPlayers) {
        //Just human players for now. A simple method to collect player names and add it to the List<Players>
        Scanner playerInput = new Scanner(System.in);

        for (int i = 0; i < humanPlayers; i++) {
            System.out.println("Please enter a name for PLAYER " + (i + 1) + ": ");
            String name = playerInput.nextLine();

            players.add(new Human(name));
        }
    }

    public void distributeInitialCardsToPlayers() {
        cardDeck.initialDeck();
        cardDeck.shuffleDeck();
//        System.out.println();
//        cardDeck.printDeck();
        for (Player player : players) {
            player.setPlayerPoints(0);
            player.setPlayersHand(cardDeck.distributeInitialCards());
        }
    }


    public static void printPlayer() {//print the players and their cards on the console
        for (Player player : players) {
            System.out.print(player.toString());
            System.out.println();
        }
    }

    public List<Card> getDiscardDeck() {
        return this.discardDeck;
    }

    public void layFirstCard() {  //this method will take one Card from the DECK and add it to the DISCARD DECK to start the game.
        discardDeck.add(cardDeck.getCardDeck().get(0));
        cardDeck.removeFromCardDeck();
    }

    public static int chooseFirstPlayer() { //Randomly choose the player that will play first.
        Random firstPlayer = new Random();
        return firstPlayer.nextInt(3);
    }

    public static int getTurn() { //get the current player's index
        return turn;
    }

    public static void setTurn(int playerIndex) { //set the current player's index
        turn = playerIndex;
    }

    public static int nextTurn() { //THIS IS A TEMPORARY METHOD!! WILL BE DELETED ONCE WE HAVE THE CHECKS
        Card currentCard = discardDeck.get(discardDeck.size() - 1); // get the current card from the discard deck...

        if (discardDeck.size() == 1) {
            turn = 0;
        }
        if (currentCard.getCardValue().equals("<->")) {
            // If isClockwise is true, the previous player should play
            if (isClockwise) {
                if (turn == 0) {
                    turn = 3;
                } else {
                    turn--;
                }
                isClockwise = false;
            } else if (isClockwise == false) {
                if (turn == 3) {
                    turn = 0;
                } else {
                    turn++;
                }
                isClockwise = true;
            }
        } else if (currentCard.getCardValue().equals("X")) {
            if (isClockwise) {
                if (turn >= 3) {
                    turn = (turn - 3) + 1;
                } else {
                    turn = turn + 2;
                }
            } else if (isClockwise == false) {
                if (turn <= 0) {
                    turn = 3;
                    turn--;
                } else {
                    turn = turn - 2;
                }
            }
        }
        else {
            // If the current card is a regular card, the next player should play
            if (isClockwise) {
            turn++;
            }
            else {
                turn--;
            }
            if (turn >= players.size()) {
                turn = 0;
            }
        }
        return turn;
    }

    public static Player currentPlayer() {
        Player currentPlayer = players.get(nextTurn());
        return currentPlayer;
    }

    public static void playerToPlay() { // alerting player that it is their turn to play.
        Player currentPlayer = currentPlayer();
        System.out.println("\n" + currentPlayer.toString() + " it's your turn to play!");
    }

    public static void acceptPlayersInput() { //this method will take one Card from the player's initialCards and add it to the DISCARD DECK.
        Player currentPlayer = currentPlayer();
        Card playedCard = currentPlayer.playerEntersCardToPlay();
        discardDeck.add(playedCard);
        currentPlayer.removeFromPlayersHand(playedCard);
    }

    public static void printDiscardDeck() {  // this method will print the cards in the DISCARD DECK
        for (Card card : discardDeck) {
            System.out.print(card + ", ");
        }
    }
}
