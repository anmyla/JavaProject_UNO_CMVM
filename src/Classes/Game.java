package Classes;

import java.util.*;

import static Classes.App.setExit;
import static Classes.Deck.*;
import static Classes.Player.isPlay;

public class Game {
    static List<Player> players = new ArrayList<>();
    private static Deck cardDeck = new Deck(108);
    protected static List<Card> discardDeck = new ArrayList<>();
    private static int turn;
    private static boolean isClockwise = true;
    protected static boolean isJoker = false;
    protected static String newColor;
    protected static boolean cardValid;
    protected static boolean hasCardToPlay;
    protected static boolean challengeWon = true;
    protected static boolean penaltyGiven;

    public static final String BLUE = "\u001B[38;2;86;119;209m";
    public static final String GREEN = "\u001B[38;2;67;185;135m";
    public static final String PURPLE = "\u001B[38;2;235;127;229m";
    public static final String RESET = "\u001B[0m";

    protected static Scanner input = new Scanner(System.in);
    protected static Random random = new Random();

    public List<Card> getDiscardDeck() {
        return this.discardDeck;
    }

    public static int getTurn() { //get the current player's index
        return turn;
    }

    public static void setTurn(int playerIndex) { //set the current player's index
        turn = playerIndex;
    }

    public static boolean isJoker() { //is the played card a joker
        return isJoker;
    }

    protected static void setJoker(boolean joker) {
        isJoker = joker;
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

    public static void setNewColor(String newColor) { // if played card isJoker, we can setNewColor
        Game.newColor = newColor;
    }


    public static boolean isHasCardToPlay() { //player has a valid card to play
        return hasCardToPlay;
    }

    public static void setHasCardToPlay(boolean hasCardToPlay) { //set if player has a valid card to play or not
        Game.hasCardToPlay = hasCardToPlay;
    }

    public static boolean isChallengeWon() { // true if current player won a challenge against previous player
        return challengeWon;
    }

    public static void setChallengeWon(boolean challengeWon) {
        Game.challengeWon = challengeWon;
    }


    public static boolean isPenaltyGiven() { // true if +2 and +4 cards are already "claimed"
        return penaltyGiven;
    }

    public static void setPenaltyGiven(boolean penaltyGiven) {
        Game.penaltyGiven = penaltyGiven;
    }


    public static void setUpHumanPlayers(int humanPlayers) { //method to collect names for Human Players and add these to player's list
        for (int i = 0; i < humanPlayers; i++) {
            System.out.println("Please enter a name for PLAYER " + (i + 1) + ": ");
            String name = input.nextLine();

            while (players.contains(name) || name.isEmpty()) {
                System.out.println("This field cannot be empty and name must be unique. Please enter name: ");
                name = input.nextLine();
            }
            players.add(new Human(name));
        }
    }

    public static void setUpBotPlayers(int bots) { //method to set up Bot players and add these to player's list
        String[] botNames = {"Bot_Ariel", "Bot_SnowWhite", "Bot_Rapunzel", "Bot_Cinderella", "Bot_Elsa", "Bot_Mulan"};
        String name;

        for (int i = 0; i < bots; i++) {
            boolean nameExists;
            do {
                int temp = random.nextInt(botNames.length);
                name = botNames[temp];
                nameExists = false;

                for (Player player : players) {
                    if (player.getName().equals(name)) {
                        nameExists = true;
                        break;
                    }
                }
            } while (nameExists);
            players.add(new Bot(name));
            System.out.println(name + " is added.");
        }
    }

    protected static void setPlayers() {
        System.out.println("How many Bots you want to play with? (0-4): ");
        int answer = input.nextInt();

        while (answer > 4) {
            System.out.println("You can only have a maximum of 4 Bots! Enter the number of Bots again (0-4) :  ");
            answer = input.nextInt();
        }

        if (answer > 0 && answer <= 4) {
            setUpBotPlayers(answer);

        } else {
            System.out.println("No Bots in this game.");
        }
        int totalPlayers = 4 - answer;
        setUpHumanPlayers(totalPlayers);
    }


    public static void distributeInitialCardsToPlayers() { // each player gets his/her set of cards
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


    public static void layFirstCard() {  //this method will take one Card from the DECK and add it to the DISCARD DECK to start the game.
        discardDeck.add(cardDeck.getCardDeck().get(0));
        cardDeck.removeFromCardDeck();

    }

    public static void chooseFirstPlayer() {//Randomly choose the player that will play first.
        Card firstCard = discardDeck.get(0);

        if (firstCard.getCardValue().equals("<->")) {
            Random firstPlayer = new Random();
            turn = firstPlayer.nextInt(3);
            checkNextTurn();
        } else if (firstCard.getCardColor().equals("J")) {
            String[] colorListArray = Card.getColorValueCollections();
            int randomIndex = random.nextInt(colorListArray.length - 2);
            // Retrieve the random value from the array
            String randomValue = colorListArray[randomIndex];

            setNewColor(randomValue);
            System.out.println("\nThe Next color to be played is : " + randomValue);

            Random firstPlayer = new Random();
            turn = firstPlayer.nextInt(3);


        } else {
            Random firstPlayer = new Random();
            turn = firstPlayer.nextInt(3);

        }
    }

    public static Player currentPlayer() {
        Player currentPlayer = players.get(getTurn());
        return currentPlayer;
    }

    public static void playerToPlay() { // alerting player that it is their turn to play.
        Player currentPlayer = currentPlayer();
        System.out.println(GREEN + "\n" + currentPlayer.toString() + " it's your turn to play!" + RESET);
        takeAdditionalCards();
    }

    public static Player getPreviousPlayer() {
        int currentPlayerIndex = getTurn();
        if (currentPlayerIndex == 0) {
            if (isClockwise) {
                currentPlayerIndex = 3;
            } else {
                currentPlayerIndex++;
            }
        } else if (currentPlayerIndex == 3) {
            if (isClockwise) {
                currentPlayerIndex = 2;
            } else {
                currentPlayerIndex = 0;
            }
        } else {
            if (isClockwise) {
                currentPlayerIndex--;
            } else {
                currentPlayerIndex++;
            }
        }
        Player previousPlayer = players.get(currentPlayerIndex);
        return previousPlayer;
    }

    public static Player getNextPlayer() {
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
        Player nextPlayer = players.get(currentPlayerIndex);
        return nextPlayer;
    }

    public static void acceptPlayersInput() { //this method will take one Card from the player's initialCards and add it to the DISCARD DECK.
        Player currentPlayer = currentPlayer();
        Card playedCard = currentPlayer.getPlayedCard();
        discardDeck.add(0, playedCard);
        currentPlayer.removeFromPlayersHand(playedCard);
        if (discardDeck.get(1).getCardValue().equals("Color")) {
            discardDeck.remove(discardDeck.get(1));
        }

        checkWinner();

        setCardValid(false); //IMPORTANT! Reset the value after accepting player's played card.
    }

    public static void printDiscardDeck() {  // this method will print the cards in the DISCARD DECK
        System.out.println(BLUE + "DISCARD DECK: ");
        for (Card card : discardDeck) {
            if (!card.getCardValue().equals("Color"))
                System.out.print(BLUE + card + ", " + RESET);
        }
    }

    public static void checkNextTurn() { // method that checks the current card played, implements rules, and return the next player
        Card currentCard = discardDeck.get(0);
        if (!isPlay()) { //the player does NOT have card to play
            isCardNormal();
        } else if (currentCard.getCardValue().equals("<->")) {
            isCardIsReverse();
        } else if (currentCard.getCardValue().equals("X")) {
            isCardSkip();
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


    public static int isCardNormal() { //This method is to decide who has the next turn when the "normal" card is played
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

    public static boolean isPlayedCardValid() { //this method checks if the played card is valid by comparing played card to the discardDeck.get(0)
        Player currentPlayer = currentPlayer();
        Card cardToCheck = currentPlayer.getPlayedCard();
        boolean valid;
        if (hasPlayerThisCardInHand()) {
            if (cardToCheck.getCardColor().equals("J")) {
                valid = true;
            } else if (cardToCheck.getCardColor().equals(newColor)) {
                valid = true;
            } else if (cardToCheck.getCardColor().equals(discardDeck.get(0).getCardColor()) || cardToCheck.getCardValue().equals(discardDeck.get(0).getCardValue())) {
                valid = true;
            } else {
                System.out.println(BLUE + currentPlayer.getName() + " this move is invalid because the Card you played is wrong!");
                System.out.println("Sorry, but you have to draw a card and you can't play this turn anymore! " + RESET);
                drawOneCard();
                System.out.println(currentPlayer.toString());
                valid = false;
            }
        } else {
            System.out.println(BLUE + currentPlayer.getName() + " this move is invalid because you do not have this card in your hand!");
            System.out.println("Sorry, but you have to draw a card and you can't play this turn anymore!" + RESET);
            drawOneCard();
            System.out.println(currentPlayer.toString());
            valid = false;
        }
        setCardValid(valid);
        return valid;
    }

    public static void setColorIfCardIsJoker() { //method to get color input from player whenever a player plays a joker card.
        Player currentPlayer = currentPlayer();
        Card playedCard = currentPlayer.getPlayedCard();

        if (currentPlayer instanceof Human) {
            System.out.println("What Color should we play next? (R, G, B, Y) :");
            setNewColor(input.nextLine());
            System.out.println(playedCard.toString() + " NEW COLOR: " + getNewColor());

        } else {
            String[] colors = {"R", "Y", "B", "G"};
            setNewColor(colors[random.nextInt(colors.length)]);
            System.out.println(playedCard.toString() + " NEW COLOR: " + getNewColor());
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
        if (cardToCheck.getCardValue().equals("C+4")) {
            System.out.println("You have to take 4 cards!");
            drawOneCard();
            drawOneCard();
            drawOneCard();
            drawOneCard();
            System.out.println("Here is your updated cards");
            System.out.println(currentPlayer.toString());
        }
    }


    public static boolean hasPlayerThisCardInHand() { //This method will check if the card being played is existing in the player's hand.
        Player currentPlayer = currentPlayer();
        boolean playerHasThisCard = false;
        Card cardToCheck = currentPlayer.getPlayedCard();

        String currentPlayerCardColor = cardToCheck.getCardColor();
        String currentPlayerCardValue = cardToCheck.getCardValue();
        String playedCardColor;
        String playedCardValue;

        for (Card card : currentPlayer.playersHand) {
            playedCardColor = card.getCardColor();
            playedCardValue = card.getCardValue();
            if (playedCardColor.equals(currentPlayerCardColor) && playedCardValue.equals(currentPlayerCardValue)) {
                playerHasThisCard = true;
                break;
            } else {
                playerHasThisCard = false;
            }
        }
        return playerHasThisCard;
    }


    public static boolean isChallenged() {
        Player previousPlayer = getPreviousPlayer();
        Player currentPlayer = currentPlayer();

        List<Card> previousPlayerHand = previousPlayer.playersHand;
        Card cardToCheck = discardDeck.get(1);
        boolean hasOtherCardsToPlay = false;

        for (Card card : previousPlayerHand) {
            if (card.getCardColor().equals(cardToCheck.getCardColor()) || card.getCardValue().equals(cardToCheck.getCardValue())) {
                hasOtherCardsToPlay = true;
            } else {
                hasOtherCardsToPlay = false;
            }
        }

        if (hasOtherCardsToPlay) {
            System.out.println("Gotcha! " + previousPlayer.getName() + ", you have to take 4 cards!");
            previousPlayerDrawsFourCardsWhenChallengeTrue();
        } else {
            currentPlayerDrawsSixCardsWhenChallengeFalse();
            System.out.println("OH NO! " + currentPlayer.getName() + " you have to take 6 cards!");
        }
        return hasOtherCardsToPlay;

    }

    public static void addTempCard() {
        Card dummyCard = new Card(getNewColor(), "Color");
        discardDeck.add(0, dummyCard);
    }


    public static void takeAdditionalCards() { //method contains challenge and penalty cards
        Player currentPlayer = currentPlayer();
        Player previousPlayer = getPreviousPlayer();
        Card cardToCheck = discardDeck.get(0);
        String answer = null;

        setChallengeWon(true); //this resets to the default value.

        if (!isPenaltyGiven()) { //check if the penalty of this card has already been given to the previous player so that the current player won't be penalized if true
            if (cardToCheck.getCardValue().equals("+2")) {
                isCardTakeTwo();
                setPenaltyGiven(true);
            } else if (cardToCheck.getCardValue().equals("C+4") && discardDeck.get(1) != null) {
                if (currentPlayer instanceof Human) {
                    System.out.println("Do you like to challenge the previous player? (Y/N)");
                    answer = input.nextLine();
                    while (!(answer.equals("Y") || answer.equals("N"))) {
                        System.out.println("Your input is invalid. Please put int Y or N: ");
                        answer = input.nextLine();
                    }
                } else { // Player is a bot
                    // Randomly generate an answer for the bot
                    answer = random.nextBoolean() ? "Y" : "N";
                }
                if (answer.equals("Y")) {
                    System.out.println("You chose to challenge " + previousPlayer.getName());
                    if (isChallenged()) {
                        setChallengeWon(true); //game will continue
                        System.out.println("You challenged " + previousPlayer.getName() + " and you won!");
                        System.out.println(currentPlayer.toString() + "you may continue to play!");
                    } else {
                        System.out.println("Sorry " + currentPlayer.getName() + " your challenge backfired!");// game starts with the next player
                        setChallengeWon(false);
                    }
                } else {
                    System.out.println("You chose not the challenge " + getPreviousPlayer().getName());
                    isCardTakeFour();
                }
            } else if (cardToCheck.getCardValue().equals("C+4") && discardDeck.get(1) == null) {
                isCardTakeFour();
            }
        }
    }

    public static Player checkWinner() {
        Player winner = currentPlayer();
        if(winner.playersHand.size()== 0) {
            System.out.println("Congratulations " + winner.getName() + " you won this round!");
            setExit(true);
        }
        return winner;
    }

}


