package Classes;

import java.util.*;

public class Game {
    private static List<Player> players = new ArrayList<>();
    private Deck cardDeck = new Deck(108);
    private static List<Card> discardDeck = new ArrayList<>();
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

//    public static int nextTurn() { //THIS IS A TEMPORARY METHOD!! WILL BE DELETED ONCE WE HAVE THE CHECKS
//    }

    public static Player currentPlayer() {
        Player currentPlayer = players.get(getTurn());
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
        System.out.println

                ("DISCARD DECK: ");
        Collections.reverse(discardDeck);

        for (Card card : discardDeck) {
            System.out.print(card + ", ");
        }
    }

    public static void checkCard() { // this a method that checks the current card played, implements rules, and return the next player
        Card currentCard = discardDeck.get(0);

        if (currentCard.getCardValue().equals("<->")) {
            isCardIsReverse();
        } else if (currentCard.getCardValue().equals("X")) {
            isCardSkip();
        } else if (currentCard.getCardValue().equals("+2")) {
            isCardTakeTwo();
        } else if (currentCard.getCardValue().equals("+4")) {
            isCardTakeFour();
        } else if (currentCard.getCardValue().equals("C")) {
            isCardJokerColor();
        }
        else {
            isCardNormal();
        }
    }
    public static int isCardIsReverse() { //This method is to decide who has the next turn when the card "<->" is played
        int currentPlayerIndex = getTurn();

        if (currentPlayerIndex == 0) {
            if (isClockwise) {
                currentPlayerIndex = 3;
                isClockwise = false;
            } else {
                currentPlayerIndex = 1;
                isClockwise = true;
            }
        } else if (currentPlayerIndex == 3) {
            if (isClockwise) {
                currentPlayerIndex = 2;
                isClockwise = false;
            } else {
                currentPlayerIndex = 0;
                isClockwise = true;
            }
        } else {
            if (isClockwise) {
                currentPlayerIndex--;
                isClockwise = false;
            } else {
                currentPlayerIndex++;
                isClockwise = true;
            }
        }
        setTurn(currentPlayerIndex);
        return currentPlayerIndex;
    }

    public static int isCardSkip() { //This method is to decide who has the next turn when the card "X" is played
        int currentPlayerIndex = getTurn();

            if (currentPlayerIndex == 0) {
                currentPlayerIndex = 2;
            } else if (currentPlayerIndex == 1) {
                currentPlayerIndex = 3;

            } else if (currentPlayerIndex == 2) {
                currentPlayerIndex = 0;

            } else if (currentPlayerIndex == 3) {
                currentPlayerIndex = 1;

            }
        setTurn(currentPlayerIndex);
        return currentPlayerIndex;
    }

    public static int isCardTakeTwo() {
        int currentPlayerIndex = getTurn();
        return currentPlayerIndex;
    }

    public static int isCardTakeFour() {
        int currentPlayerIndex = getTurn();
        return currentPlayerIndex;
    }

    public static int isCardJokerColor() {
        int currentPlayerIndex = getTurn();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a color " + currentPlayer().getName() + ": ");
        String enteredColor = scanner.next();
        currentPlayerIndex++;

        setTurn(currentPlayerIndex);
        return currentPlayerIndex;
    }
    public static int isCardNormal() {
        int currentPlayerIndex = getTurn();

        if (currentPlayerIndex == 0) {
            if (isClockwise) {
                currentPlayerIndex++;
            } else {
                currentPlayerIndex = 3;
            }
        } else if (currentPlayerIndex == 3) {
            if (isClockwise) {
                currentPlayerIndex = 0;
            } else {
                currentPlayerIndex = 2;
            }
        } else {
            if (isClockwise) {
                currentPlayerIndex++;
            } else {
                currentPlayerIndex--;
            }
        }
        setTurn(currentPlayerIndex);
        return currentPlayerIndex;
    }
}
