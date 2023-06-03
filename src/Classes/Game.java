package Classes;

import java.util.*;

import static Classes.Deck.*;
import static Classes.Player.isPlay;

public class Game {
    static List<Player> players = new ArrayList<>();
    private static Deck cardDeck = new Deck(108);
    protected static List<Card> discardDeck = new ArrayList<>();
    private static int turn;
    private static boolean isClockwise = true;
    static boolean isJoker = false;
    static String newColor;
    static boolean cardValid;
    static boolean hasCardToPlay;
    static boolean challengeWon = true;

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[38;2;67;185;135m";
    public static final String RESET = "\u001B[0m";

    protected static Scanner input = new Scanner(System.in);
    protected static Random random = new Random();



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
        Game.newColor = newColor;
    }


    public static boolean isHasCardToPlay() {
        return hasCardToPlay;
    }

    public static void setHasCardToPlay(boolean hasCardToPlay) {
        Game.hasCardToPlay = hasCardToPlay;
    }

    public static boolean isChallengeWon() {
        return challengeWon;
    }

    public static void setChallengeWon(boolean challengeWon) {
        Game.challengeWon = challengeWon;
    }

    public static Player currentPlayer() {
        Player currentPlayer = players.get(getTurn());
        return currentPlayer;
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
            addTempCard();

            Random firstPlayer = new Random();
            turn = firstPlayer.nextInt(3);


        } else {
            Random firstPlayer = new Random();
            turn = firstPlayer.nextInt(3);

        }
    }

    public static void playerToPlay() { // alerting player that it is their turn to play.
        Player currentPlayer = currentPlayer();
        System.out.println(GREEN + "\n" + currentPlayer.toString() + " it's your turn to play!" + RESET);
    }

    public static void acceptPlayersInput() { //this method will take one Card from the player's initialCards and add it to the DISCARD DECK.
        Player currentPlayer = currentPlayer();
        Card playedCard = currentPlayer.getPlayedCard();
        discardDeck.add(0, playedCard);
        currentPlayer.removeFromPlayersHand(playedCard);
        if (discardDeck.get(1).getCardValue().equals("Color")) {
            discardDeck.remove(discardDeck.get(1));
        }
        setCardValid(false); //IMPORTANT! Reset the value after accepting player's played card.
    }

    public static void printDiscardDeck() {  // this method will print the cards in the DISCARD DECK
        System.out.println(RED + "DISCARD DECK: ");
        for (Card card : discardDeck) {
            System.out.print(RED + card + ", " + RESET);
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
        } else if (currentCard.getCardValue().equals("C+4")) {
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
            if (cardToCheck.getCardColor().equals("J")) {
                valid = true;
            } else if (cardToCheck.getCardColor().equals(newColor)) {
                valid = true;
            } else if (cardToCheck.getCardColor().equals(discardDeck.get(0).getCardColor()) || cardToCheck.getCardValue().equals(discardDeck.get(0).getCardValue())) {
                valid = true;
            } else {
                System.out.println(RED + currentPlayer.getName() + " you just made an invalid moved!");
                System.out.println("Sorry, but you have to draw a card! PLAYER PLACED A WRONG CARD" + RESET);
                drawOneCard();
                System.out.println("Here's your updated Cards!");
                System.out.println(currentPlayer.toString());
                valid = false;
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

        if(currentPlayer instanceof Human) {
            if (playedCard.getCardValue().equals("C") || playedCard.getCardValue().equals("C+4")) {
                isJoker = true;
                System.out.println("What Color should we play next? (R, G, B, Y) :");
                newColor = input.nextLine();
                System.out.println(playedCard.toString() + " NEW COLOR: " + getNewColor());

            } else {
                isJoker = false;
                setNewColor(null);
                System.out.println(playedCard.toString());
            }
        }

        else {
            if (playedCard.getCardValue().equals("C") || playedCard.getCardValue().equals("C+4")) {
                isJoker = true;
                String[] colors = {"R", "Y", "B", "G"};
                newColor = colors[random.nextInt(colors.length)];
                System.out.println(playedCard.toString() + " NEW COLOR: " + newColor);
            } else {
                isJoker = false;
                setNewColor(null);
                System.out.println(playedCard.toString());
            }
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

    public static boolean playerHasCardToPlay() {
        Player currentPlayer = currentPlayer();
        String currentDiscardCardColor = discardDeck.get(0).getCardColor();
        String currentDiscardCardValue = discardDeck.get(0).getCardValue();
        String playerCardColor;
        String playerCardValue;

        for (Card card : currentPlayer.playersHand) {
            playerCardColor = card.getCardColor();
            playerCardValue = card.getCardValue();

            if (playerCardColor.equals("J")) {
                setHasCardToPlay(true);
                break;
            } else if (playerCardColor.equals(newColor)) {
                setHasCardToPlay(true);
                break;
            } else if (playerCardColor.equals(currentDiscardCardColor)) {
                setHasCardToPlay(true);
                break;
            } else if (playerCardValue.equals(currentDiscardCardValue)) {
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
        Card cardToPlay = null;
        String cardColor;
        String cardValue;

        String[] validCardColors = Card.getColorValueCollections();
        List<String> validCardColorList = new ArrayList<>(Arrays.asList(validCardColors));

        if (currentPlayer instanceof Human) {
            System.out.println("ENTER CARD COLOR (R, B, G, Y, J):"); //Player chooses a card color (R,B,Y,G, J)
            cardColor = input.nextLine();
            while (!validCardColorList.contains(cardColor)) {
                System.out.println("Your CARD COLOR input is invalid. Please choose one (R, B, G, Y, or J): ");
                cardColor = input.nextLine();
            }

            System.out.println("ENTER CARD VALUE:"); //Player chooses a value (1,2,3, <->, etc.)
            cardValue = input.nextLine();
            while (!validCardValuesList.contains(cardValue)) {
                System.out.println("Your CARD VALUE input is invalid. Please choose one (0, 1 , 2 , 3 , 4 , 5 ,6 , 7 , 8 , 9 , X , <-> , +2, +4, C, or C+4)");
                cardValue = input.nextLine();
            }
            cardToPlay = new Card(cardColor, cardValue);

        } else {
            cardToPlay = botMakesMove();
        }

        currentPlayer.setPlayedCard(cardToPlay);
        isCardJoker();

        return cardToPlay;
    }

    public static void currentPlayersTurn() { //this method will decide who's turn it is based on what's on the discard Deck
        setChallengeWon(true); //this resets the default value.
        Player currentPlayer = currentPlayer();
        Card cardToCheck = discardDeck.get(0);
        String answer = null;

        if (isPlay()) {
            if (cardToCheck.getCardValue().equals("+2")) {
                isCardTakeTwo();
                playerEntersCardToPlay();
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
                    if (isChallenged()) {
                        setChallengeWon(true); //game will continue
                        System.out.println(currentPlayer.toString() + "you may play!");
                        addTempCard();
                        printDiscardDeck();
                        playerEntersCardToPlay();
                    } else {
                        System.out.println("Sorry " + currentPlayer.getName() + " your challenge backfired!");// game starts with the next player
                        setChallengeWon(false);
                        addTempCard();
                    }
                } else {
                    isCardTakeFour();
                    playerEntersCardToPlay();
                }
            } else {
                playerEntersCardToPlay();
            }
        } else if (cardToCheck.getCardValue().equals("C+4") && discardDeck.get(1) == null) {
            isCardTakeFour();
            playerEntersCardToPlay();
        } else if (!isPlay() && (cardToCheck.getCardValue().equals("C+4"))) {
            isCardTakeFour();
        } else if (!isPlay() && (cardToCheck.getCardValue().equals("+2"))) {
            isCardTakeTwo();
        } else {
            System.out.println("You have to pass this turn because you still don't have a card to play!");
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
                currentPlayerIndex = 4;
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
                currentPlayerIndex = 4;
            } else {
                currentPlayerIndex = 0;
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
            previousPlayerDrawsFourCardsWhenChallengeTrue();
            System.out.println("Oops! " + previousPlayer.getName() + ", you have to take 4 cards!");
            System.out.println(previousPlayer.toString());
        } else {
            currentPlayerDrawsSixCardsWhenChallengeFalse();
            System.out.println("OH NO! " + currentPlayer.getName() + " you have to take 6 cards!");
            System.out.println(currentPlayer.toString());
        }
        return hasOtherCardsToPlay;
    }

    public static void addTempCard() {
        Card dummyCard = new Card(getNewColor(), "Color");
        discardDeck.add(0, dummyCard);
    }

    public static void addBotsToPlayersList(int bots) {
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
            System.out.println("You can only have a maximum for 4 Bots! Enter the number of Bots again (0-4) :  ");
            answer = input.nextInt();
        }

        if (answer > 0 && answer <= 4) {
            addBotsToPlayersList(answer);

        } else {
            System.out.println("No Bots in this game.");
        }
        int totalPlayers = 4 - answer;
        setUpHumanPlayers(totalPlayers);
    }

    public static Card botMakesMove() {
        Player currentPlayer = currentPlayer();
        List<Card> botsCard = currentPlayer.playersHand;
        Card cardToCheck = discardDeck.get(0);
        Card validCardToPlay = null;

        for (Card card : botsCard) {
            if (card.getCardColor().equals(cardToCheck.getCardColor()) || card.getCardValue().equals(cardToCheck.getCardValue())) {
                validCardToPlay = card;
            }
        }

        if (validCardToPlay == null) {
            for (Card card : botsCard) {
                if (card.getCardColor().equals("J")) {
                    validCardToPlay = card;
                }
            }
        }
        return validCardToPlay;
    }
}


