package Classes;

import java.util.*;

import static Classes.Deck.drawOneCard;
import static Classes.Player.isPlay;

public class Game {
    private static List<Player> players = new ArrayList<>();
    private static Deck cardDeck = new Deck(108);
    private static List<Card> discardDeck = new ArrayList<>();
    private static int turn;
    private static boolean isClockwise = true;
    static boolean isJoker = false;
    static String newColor;
    static boolean cardValid;
    static boolean hasCardToPlay;


    public static int getTurn() { //get the current player's index
        return turn;
    }
    public static void setTurn(int playerIndex) { //set the current player's index
        turn = playerIndex;
    }
    public static boolean isJoker() {
        return isJoker;
    }
    public void setJoker(boolean joker) {
        this.isJoker = joker;
    }
    public static void setCardValid(boolean cardValid) {
        cardValid = cardValid;
    }
    public static boolean isCardValid() {
        return cardValid;
    }
    public static String getNewColor() {
        return newColor;
    }
    public static void setNewColor(String newColor) {
        newColor = newColor;
    }


    public static boolean isHasCardToPlay() {
        return hasCardToPlay;
    }

    public static void setHasCardToPlay(boolean hasCardToPlay) {
        Game.hasCardToPlay = hasCardToPlay;
    }

    public static Player currentPlayer() {
        Player currentPlayer = players.get(getTurn());
        return currentPlayer;
    }

    public static void setUpPlayers(int humanPlayers) {
        //Just human players for now. A simple method to collect player names and add it to the List<Players>
        Scanner playerInput = new Scanner(System.in);

        for (int i = 0; i < humanPlayers; i++) {
            System.out.println("Please enter a name for PLAYER " + (i + 1) + ": ");
            String name = playerInput.nextLine();

            players.add(new Human(name));
        }
    }

    public static void distributeInitialCardsToPlayers() {
        cardDeck.initialDeck();
        cardDeck.shuffleDeck();

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

    public static void playerToPlay() { // alerting player that it is their turn to play.
        Player currentPlayer = currentPlayer();
        System.out.println("\n" + currentPlayer.toString() + " it's your turn to play!");
    }

    public static void acceptPlayersInput() { //this method will take one Card from the player's initialCards and add it to the DISCARD DECK.
        Player currentPlayer = currentPlayer();
        Card playedCard = currentPlayer.getPlayedCard();
        discardDeck.add(0, playedCard);
        currentPlayer.removeFromPlayersHand(playedCard);
        System.out.println(currentPlayer.toString());
        setCardValid(false); //IMPORTANT! Reset the value after accepting player's played card.
    }

    public static void printDiscardDeck() {  // this method will print the cards in the DISCARD DECK
        System.out.println("DISCARD DECK: ");
        for (Card card : discardDeck) {
            System.out.print(card + ", ");
        }
    }

    public static void checkNextTurn() { // this a method that checks the current card played, implements rules, and return the next player
        Card currentCard = discardDeck.get(0);
        if (!isPlay()) { //the player does NOT have card to pay
            isCardNormal();
        } else if (currentCard.getCardValue().equals("<->")) {
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


    public static boolean isPlayedCardValid() {
        Player currentPlayer = currentPlayer();
        Card cardToCheck = currentPlayer.getPlayedCard();
        boolean valid;
        if (hasPlayerThisCardInHand()) {
            if (cardToCheck.getCardColor().equals("C")) {
                valid = true;
            }
            else if (cardToCheck.getCardColor().equals(discardDeck.get(0).getCardColor()) || cardToCheck.getCardValue().equals(discardDeck.get(0).getCardValue())) {
                valid = true;
            } else {
                System.out.println(currentPlayer.getName() + " you just made an invalid moved!");
                System.out.println("Sorry, but you have to draw a card! PLAYER PLACED A WRONG CARD");
                drawOneCard();
                System.out.println("Here's your updated Cards!");
                System.out.println(currentPlayer.toString());
                valid = true;
            }
        } else {
            System.out.println(currentPlayer.getName() + " you just made an invalid moved!");
            System.out.println("Sorry, but you have to draw a card! PLAYER DOES NOT HAVE THE CARD");
            drawOneCard();
            System.out.println("Here's your updated Cards!");
            System.out.println(currentPlayer.toString());
            valid = false;
        }
        setCardValid(valid);
        return valid;
    }

    public static void isCardJoker() {
        Player currentPlayer = currentPlayer();
        Card playedCard = currentPlayer.getPlayedCard();
        Scanner newColorInput = new Scanner(System.in);

        if (playedCard.getCardValue().equals("C")) {
            isJoker = true;
            System.out.println("What Color should we play next? (R, G, B, Y) :");
            newColor = newColorInput.nextLine();
            System.out.println(playedCard.toString() + " NEW COLOR: " + getNewColor());

        } else {
            isJoker = false;
            setNewColor(null);
            System.out.println(playedCard.toString());
        }
    }

    public static void isCardTakeTwo() {
        Player currentPlayer = currentPlayer();
        Card cardToCheck = discardDeck.get(0);
        if (cardToCheck.getCardValue().equals("+2")) {
            System.out.println("You have to take 2 cards!");
            drawOneCard();
            drawOneCard();
            System.out.println("Here is your updated cards");
            System.out.println(currentPlayer.toString());
        }
    }

    public static void isCardTakeFour() {
        Player currentPlayer = currentPlayer();
        Card cardToCheck = discardDeck.get(0);
        if (cardToCheck.getCardValue().equals("+4")) {
            System.out.println("You have to take 4 cards!");
            drawOneCard();
            drawOneCard();
            drawOneCard();
            drawOneCard();
            System.out.println("Here is your updated cards");
            System.out.println(currentPlayer.toString());
        }
    }

    public static boolean playerHasCardToPlay() {
        Player currentPlayer = currentPlayer();
        String newColor = getNewColor();
        String currentDiscardCardColor = discardDeck.get(discardDeck.size() - 1).getCardColor();
        String currentDiscardCardValue = discardDeck.get(discardDeck.size() - 1).getCardValue();
        String checkCardColor;
        String checkCardValue;

        for (Card card : currentPlayer.playersHand) {
            checkCardColor = card.getCardColor();
            checkCardValue = card.getCardValue();
            if(checkCardColor.equals("J")){
                setHasCardToPlay(true);
                break;
            } else if (checkCardColor.equals(currentDiscardCardColor)) {
                setHasCardToPlay(true);
                break;
            } else if (checkCardValue.equals(currentDiscardCardValue)) {
                setHasCardToPlay(true);
                break;
            } else if (currentDiscardCardColor.equals(newColor)) {
                setHasCardToPlay(true);
                break;
            } else {
                setHasCardToPlay(false);
            }
        }
        setHasCardToPlay(hasCardToPlay);
        return isHasCardToPlay();
    }


    public static Card playerEntersCardToPlay() {
        Player currentPlayer = currentPlayer();
        String[] validCardValues = Card.getFaceValueCollections();
        List<String> validCardValuesList = new ArrayList<>(Arrays.asList(validCardValues));

        String[] validCardColors = Card.getColorValueCollections();
        List<String> validCardColorList = new ArrayList<>(Arrays.asList(validCardColors));

        Scanner cardInput = new Scanner(System.in);

        System.out.println("ENTER CARD COLOR (R, B, G, Y, J):"); //Player chooses a card color (R,B,Y,G, J)
        String cardColor = cardInput.nextLine();
        while (!validCardColorList.contains(cardColor)) {
            System.out.println("Your CARD COLOR input is invalid. Please choose one (R, B, G, Y, or J): ");
            cardColor = cardInput.nextLine();
        }

        System.out.println("ENTER CARD VALUE:"); //Player chooses a value (1,2,3, <->, etc.)
        String cardValue = cardInput.nextLine();
        while (!validCardValuesList.contains(cardValue)) {
            System.out.println("Your CARD VALUE input is invalid. Please choose one (0, 1 , 2 , 3 , 4 , 5 ,6 , 7 , 8 , 9 , X , <-> , +2, +4, C, or C+4)");
            cardValue = cardInput.nextLine();
        }

        Card cardToPlay = new Card(cardColor, cardValue);
        currentPlayer.setPlayedCard(cardToPlay);
        isCardJoker();

        return cardToPlay;
    }

    public static void currentPlayersTurn() { //this method will decide who's turn it is based on what's on the discard Deck
        Player currentPlayer = currentPlayer();
        Card cardToCheck = discardDeck.get(0);

        if(isPlay()) {
            if (cardToCheck.getCardValue().equals("+2")) {
                isCardTakeTwo();
                playerEntersCardToPlay();
            }

            else if (cardToCheck.getCardValue().equals("+4")) {
                isCardTakeFour();
                playerEntersCardToPlay();
            }

            else {
                playerEntersCardToPlay();
            }
        }
        else if (!isPlay() && (cardToCheck.getCardValue().equals("+2"))) {
            isCardTakeTwo();
        }
        else {
            System.out.println("You have to pass this turn because you still dont have a card to play!");
        }
    }

    public static boolean hasPlayerThisCardInHand() { //This method will check if the card being played is existing in the player's hand.
        Player currentPlayer = currentPlayer();
        boolean doIHaveThisCard = false;
        Card cardToCheck = currentPlayer.getPlayedCard();

        String currentPlayerCardColor = cardToCheck.getCardColor();
        String currentPlayerCardValue = cardToCheck.getCardValue();
        String playedCardColor;
        String playedCardValue;

        for (Card card : currentPlayer.playersHand) {
            playedCardColor = card.getCardColor();
            playedCardValue = card.getCardValue();
            if (playedCardColor.equals(currentPlayerCardColor) && playedCardValue.equals(currentPlayerCardValue)) {
                doIHaveThisCard = true;
                break;
            } else {
                doIHaveThisCard = false;
            }
        }
        return doIHaveThisCard;
    }
}


