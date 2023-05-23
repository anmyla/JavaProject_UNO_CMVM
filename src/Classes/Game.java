package Classes;

import java.sql.SQLOutput;
import java.util.*;

import static Classes.Deck.drawOneCard;

public class Game {
    private static List<Player> players = new ArrayList<>();
    private Deck cardDeck = new Deck(108);
    private static List<Card> discardDeck = new ArrayList<>();
    private static int turn = chooseFirstPlayer();
    private static boolean isClockwise = true;
    private static boolean isJoker = false;
    private static String newColor;


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

    public boolean isJoker() {
        return isJoker;
    }

    public void setJoker(boolean joker) {
        this.isJoker = joker;
    }

    public String getNewColor() {
        return newColor;
    }

    public void setNewColor(String newColor) {
        this.newColor = newColor;
    }

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
        Card playedCard = currentPlayer.getPlayedCard();
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

    public static void checkNextTurn() { // this a method that checks the current card played, implements rules, and return the next player
        Card currentCard = discardDeck.get(0);

        if (currentCard.getCardValue().equals("<->")) {
            isCardIsReverse();
        } else if (currentCard.getCardValue().equals("X")) {
            isCardSkip();
        } else if (currentCard.getCardValue().equals("+2")) {
            isCardNormal(); // same rule when choosing the next player like isCardNormal()
        } else if (currentCard.getCardValue().equals("+4")) {
            isCardNormal(); // same rule when choosing the next player like isCardNormal()
        } else if (currentCard.getCardValue().equals("C")) {
            isCardNormal(); // same rule when choosing the next player like isCardNormal()
        } else {
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

    public static void isCardJokerColor() {
        int currentPlayerIndex = getTurn();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a color " + currentPlayer().getName() + ": ");
        String enteredColor = scanner.next();
    }

    public static boolean isPlayedCardValid() {
        boolean isValid = false;
        Card cardToCheck = currentPlayer().getPlayedCard();

        if (cardToCheck.getCardColor().equals(discardDeck.get(0).getCardColor()) || cardToCheck.getCardValue().equals(discardDeck.get(0).getCardValue())) {
            isValid = true;
        } else if (cardToCheck.getCardColor().equals("C")) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public void isCardJoker() {
        Scanner newColorInput = new Scanner(System.in);

        if (currentPlayer().getPlayedCard().getCardValue().equals("C")) {
            isJoker = true;
            System.out.println("What Color should we play next? (R, G, B, Y) :");
            newColor = newColorInput.nextLine();
            setNewColor(newColor);
            setJoker(true);
        } else {
            isJoker = false;
            setJoker(false);
        }
        System.out.println(currentPlayer().getPlayedCard().toString());
    }

    public static boolean playerHasCardToPlay() {
        Player currentPlayer = currentPlayer();
        boolean hasCardToPlay = false;
        String currentDiscardCardColor = discardDeck.get(discardDeck.size()-1).getCardColor();
        String currentDiscardCardValue = discardDeck.get(discardDeck.size()-1).getCardValue();

        for (Card card : currentPlayer.playersHand) {
            if (card.getCardColor().equals(currentDiscardCardColor)) {
                hasCardToPlay = true;
                break;
            }
            else if (card.getCardValue().equals(currentDiscardCardValue)) {
                hasCardToPlay = true;
                    break;
            }
            else if(currentDiscardCardColor.equals(newColor) ) {
                hasCardToPlay = true;
            }
            else {
                hasCardToPlay = false;
                break;
            }
        }
        return hasCardToPlay;
    }

    public static void playOrPass() {
        Player currentPlayer = currentPlayer();
        Scanner playerInput = new Scanner(System.in);
        if (playerHasCardToPlay()) {
            System.out.println(currentPlayer().getName() + ", will you play(Y) or pass(N)?" );
            String passOrPlay = playerInput.nextLine().toLowerCase();

            while (!passOrPlay.equals("y") && !passOrPlay.equals("n")) {
                System.out.println("Invalid input. Please enter 'Y' for play or 'N' for pass: ");
                passOrPlay = playerInput.nextLine().toLowerCase();
            }

            if (passOrPlay.equals("n")) {
                System.out.println("Understood. You may chose to pass but you must draw a card");
                drawOneCard();
            }
            else {
                currentPlayer.playerEntersCardToPlay();
            }
        }
        else {
            System.out.println("Looks like you don't have a card to play this round");
            System.out.println("Sorry but you have to draw a card!");
            drawOneCard();
        }
    }
}

