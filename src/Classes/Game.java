package Classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static Classes.Database.createDatabase;
import static Classes.App.setExit;
import static Classes.Deck.*;
import static Classes.Player.*;

public class Game {
    protected static int round = 1;
    protected static List<Player> players = new ArrayList<>();
    protected static Deck cardDeck = new Deck(108);
    protected static List<Card> discardDeck = new ArrayList<>();
    private static int turn;
    private static boolean isClockwise = true;
    protected static boolean isJoker = false;
    protected static String newColor;
    protected static boolean cardValid;
    protected static boolean challengeWon = true;
    protected static boolean penaltyGiven;
    protected static Player winnerOfThisRound;
    public static final String ROSE = "\u001B[38;2;209;86;108m";
    public static final String SKY = "\u001B[38;2;153;205;240m";
    public static final String SUNNY = "\u001B[38;2;204;204;102m";
    public static final String RESET = "\u001B[0m";
    protected static boolean isBlocked;

    protected static boolean isThereAWinnerOfThisRound;

    public List<Card> getDiscardDeck() {
        return this.discardDeck;
    }

    public static int getTurn() { //get the current player's index
        return turn;
    }

    public static void setTurn(int playerIndex) { //set the current player's index
        turn = playerIndex;
    }


    protected static void setJoker(boolean joker) {
        isJoker = joker;
    }

    public static void setCardValid(boolean valid) {
        cardValid = valid;
    }

    public static boolean isCardValid() {
        return cardValid;
    }

    public static boolean isBlocked() {
        return isBlocked;
    }

    public static void setBlocked(boolean blocked) {
        Game.isBlocked = blocked;
    }

    public static String getNewColor() {
        return newColor;
    }

    public static void setNewColor(String newColor) { // if played card isJoker, we can setNewColor
        Game.newColor = newColor;
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

    public static Player getWinnerOfThisRound() {
        return winnerOfThisRound;
    }

    public static void setWinnerOfThisRound(Player winnerOfThisRound) {
        Game.winnerOfThisRound = winnerOfThisRound;
    }

    public static int getRound() {
        return round;
    }

    public static void setRound(int round) {
        Game.round = round;
    }
    public static void setIsThereAWinnerOfThisRound(boolean isThereAWinnerOfThisRound) {
        Game.isThereAWinnerOfThisRound = isThereAWinnerOfThisRound;
    }


    public void database() {
        Database db1 = new Database();
        createDatabase();
    }

    public static void setUpHumanPlayers(int humanPlayers) { //method to collect names for Human Players and add these to player's list
        Scanner inputName = new Scanner(System.in);
        for (int i = 0; i < humanPlayers; ) {
            String name;
            boolean nameExists;

            do {
                System.out.println("Please enter a name for PLAYER " + (players.size() + 1) + ": ");
                name = inputName.nextLine().toUpperCase().trim(); // Trim the input

                nameExists = false;
                for (Player player : players) {
                    if (player.getName().equals("HELP")) {
                        break;
                    } else if (player.getName().equals("EXIT")) {
                        setExit(true);
                        break;
                    } else if (player.getName().equals(name)) {
                        nameExists = true;
                        break;
                    }
                }
                if (nameExists || name.isEmpty()) {
                    System.out.println("This field cannot be empty and name must be unique!");
                }
            } while (nameExists || name.isEmpty());


            if (name.equals("HELP")) {
                callHelp();
            } else if (name.equals("EXIT")) {
                setExit(true);
                break;
            } else {
                players.add(new Human(name));
                i++;
            }
        }
    }

    public static void setUpBotPlayers(int bots) { //method to set up Bot players and add these to player's list
        String[] botNames = {"SnowWhite", "Cinderella", "Aurora", "Ariel", "Belle", "Jasmine", "Pocahontas", "Mulan", "Tiana", "Rapunzel", "Merida", "Moana"};
        String name;
        Random random = new Random();
        for (int i = 0; i < bots; i++) {
            boolean nameExists;
            do {
                int temp = random.nextInt(botNames.length); //automatically choose a random and "unique" name from the botNames[]
                name = botNames[temp];
                nameExists = false;
                for (Player player : players) {
                    if (player.getName().equals(name.toUpperCase())) {
                        nameExists = true;
                        break;
                    }
                }
            } while (nameExists);
            players.add(new Bot(name.toUpperCase()));
            System.out.println(name + " is added.");
        }
    }

    protected static void setPlayers() { // set up players for the round (humans and bots)
        Scanner input = new Scanner(System.in);
        int answer = 0;
        String userInput;
        while (true) {
            System.out.println("How many Bots do you want to play with? (0-4): ");
            userInput = input.nextLine().toUpperCase();
            if (!userInput.equals("HELP") && !userInput.equals("EXIT")) {
                try {
                    answer = Integer.parseInt(userInput);
                    if (answer >= 0 && answer <= 4) {
                        break; // Valid input, exit the loop
                    } else {
                        System.out.println("Invalid input! Please enter a number between 0 and 4.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number between 0 and 4.");
                }
            } else if (userInput.equals("HELP")) {
                callHelp();
            } else if (userInput.equals("EXIT")) {
                setExit(true);
                break;
            }
        }
        if (answer > 0 && answer <= 4) {
            setUpBotPlayers(answer);
        } else {
            if (!userInput.equals("EXIT")) {
                System.out.println("OK, so there will be NO bots in this game!");
            }
        }

        if (!userInput.equals("EXIT")) {
            int humanPlayers = 4 - answer;
            setUpHumanPlayers(humanPlayers); // answer is the number of bots
        }
    }

    public static void distributeInitialCardsToPlayers() { // each player gets his/her set of cards
        cardDeck.initialDeck();
        cardDeck.shuffleDeck();

        for (Player player : players) {
            player.setPlayersHand(cardDeck.distributeInitialCards());
        }
    }

    public static void printPlayer() {//print the players and their cards on the console
        for (Player player : players) {
            System.out.print(player.toString());
            System.out.println();
        }
    }

    public static void layFirstCard() { //this method will take one Card from the DECK and add it to the DISCARD DECK to start the game.
        Random random = new Random();
        Card initialCard;
        do {
            int index = random.nextInt(cardDeck.getCardDeck().size());
            initialCard = cardDeck.getCardDeck().get(index);

        } while (initialCard.getCardValue().equals("C+4") || initialCard.getCardValue().equals("+2")
                || initialCard.getCardValue().equals("C") || initialCard.getCardValue().equals("X")
                || initialCard.getCardValue().equals("<->"));//if an action card is randomly chosen, it will put it back to the DECK and choose another one.
        discardDeck.add(initialCard);
        cardDeck.remove(initialCard);
    }

    public static void chooseFirstPlayer() {//Randomly choose the player that will play first.
        Card firstCard = discardDeck.get(0);
        Random random = new Random();
            Random firstPlayer = new Random();
            turn = firstPlayer.nextInt(3);
        }


    public static Player currentPlayer() {
        Player currentPlayer = players.get(getTurn());
        return currentPlayer;
    }

    public static void playerToPlay() { // alerting player that it is their turn to play.
        Player currentPlayer = currentPlayer();
        System.out.println("\n" + currentPlayer.toString() + " it's your turn to play!");
        takeAdditionalCards(); // before a player can player, this will check if player has to take penalty cards.
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

    /*
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
*/
    public static void acceptPlayersInput() { //this method will take the playedCard from the player's hand and add it to the DISCARD DECK.
        Player currentPlayer = currentPlayer();
        Card playedCard = currentPlayer.getPlayedCard();
        discardDeck.add(0, playedCard);
        currentPlayer.removeFromPlayersHand(playedCard);
        if (discardDeck.get(1).getCardValue().equals("Color")) { //this is to delete the tempCard from the discardDeck.
            if (!discardDeck.get(0).getCardColor().equals("J")) {
                setNewColor(null); //reset the jokerColor value to default(null) after it's been used.
            }
            discardDeck.remove(discardDeck.get(1)); //delete temp card
        }
        setBlocked(false); //IMPORTANT! Resets this value after every player's turn.
        setCardValid(false); //IMPORTANT! Reset the value after accepting player's played card.
    }

    public static void printDiscardDeck() { // this method will print the cards in the DISCARD DECK
        System.out.println();
        System.out.print(ROSE + "DISCARD DECK: ");
        Card card = discardDeck.get(0);
        if (card.getCardColor().equals("J") && getNewColor() != null) {
            System.out.print(ROSE + discardDeck.get(0) + " New Color: " + getNewColor() + RESET);
        } else if (!card.getCardValue().equals("Color")) {
            System.out.print(ROSE + discardDeck.get(0) + RESET);

        } else if(card.getCardValue().equals("Color")) {
            System.out.print(ROSE + discardDeck.get(1) + " New Color: " + getNewColor() +RESET);
        } else {
            System.out.print(ROSE + discardDeck.get(0) + " New Color: " + getNewColor() + RESET);
        }
    }

    public static void checkNextTurn() { // method that checks the currentCardPlayed(top of discardDeck pile), implements rules and decides who plays next.
        Card currentCard = discardDeck.get(0);
        if (!canMakeAMove) { //if the player does NOT have a card to play, this will not be implemented
            cardIsNormal();
        } else if (currentCard.getCardValue().equals("<->")) {
            cardIsReverse();
        } else if (currentCard.getCardValue().equals("X")) {
            cardIsSkip();
        } else {
            cardIsNormal();
        }
    }

    public static int cardIsReverse() { //This method is to decide who has the next turn when the card "<->" is played
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

    public static int cardIsSkip() { //This method is to decide who has the next turn when the card "X" is played
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

    public static int cardIsNormal() { //This method is to decide who has the next turn when the "normal" card is played
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
                System.out.println(currentPlayer.getName() + " this move is invalid because the Card you played is wrong!");
                System.out.println("Sorry, but you have to draw a card and you can't play this turn anymore! ");
                drawOneCard();
                setBlocked(true);
                System.out.println(currentPlayer.getPlayersHand().toString());
                valid = false;
            }
        } else {
            System.out.println(currentPlayer.getName() + " this move is invalid because you do not have this card in your hand!");
            System.out.println("Sorry, but you have to draw a card and you can't play this turn anymore!");
            drawOneCard();
            setBlocked(true);
            System.out.println(currentPlayer.getPlayersHand().toString());
            valid = false;
        }
        setCardValid(valid);
        return valid;
    }

    public static void setColorIfCardIsJoker() { //method to get color input from player whenever a player plays a joker card.
        Player currentPlayer = currentPlayer();
        Card playedCard = currentPlayer.getPlayedCard();
        Scanner colorInput = new Scanner(System.in);
        Random random = new Random();

        if (currentPlayer instanceof Human) {
            String validColors = "RGBY";

            System.out.println("What Color should we play next? (R, G, B, Y):");
            String color = colorInput.nextLine().toUpperCase();

            while (!validColors.contains(color)) {
                System.out.println("Invalid input! Please enter one of the valid colors: R, G, B, Y");
                color = colorInput.nextLine().toUpperCase();
            }

            setNewColor(color);
            System.out.println(playedCard.toString() + " NEW COLOR: " + getNewColor());
        } else {
            String[] colors = {"R", "Y", "B", "G"};
            setNewColor(colors[random.nextInt(colors.length)]);
            System.out.println(playedCard.toString() + " NEW COLOR: " + getNewColor());
        }
    }

    public static void cardIsTakeTwo() { //this method implements the penalty if the "+2" card is played.
        Player currentPlayer = currentPlayer();
        Card cardToCheck = discardDeck.get(0);
        if (cardToCheck.getCardValue().equals("+2")) {
            System.out.println("You have to take 2 cards!");
            drawOneCard();
            drawOneCard();
            System.out.println("Here is your updated hand:");
            System.out.println(currentPlayer.toString());
        }
    }

    public static void cardIsTakeFour() { //this method implements the penalty if the "+4" card is played.
        Player currentPlayer = currentPlayer();
        Card cardToCheck = discardDeck.get(0);
        if (cardToCheck.getCardValue().equals("C+4")) {
            System.out.println("You have to take 4 cards!");
            for (int i = 0; i < 4; i++) {
                drawOneCard();
            }
            System.out.println("Here is your updated hand");
            System.out.println(currentPlayer.toString());
        }
    }

    public static boolean hasPlayerThisCardInHand() { //This method will check if the card being played is existing in the player's hand.
        Player currentPlayer = currentPlayer();
        boolean playerHasThisCard = false;
        Card cardToCheck = currentPlayer.getPlayedCard();
        try {
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
        } catch (NullPointerException e) {
            System.out.println(currentPlayer.getName() + " can't make a move this turn");
        }
        return playerHasThisCard;
    }

    public static boolean isChallenged() { //this method is a part of the "challenge mechanism" of the game
        Player previousPlayer = getPreviousPlayer();
        Player currentPlayer = currentPlayer();
        List<Card> previousPlayerHand = previousPlayer.playersHand;

        Card cardToCheck = discardDeck.get(1);
        boolean hasOtherCardsToPlay = false;

        for (Card card : previousPlayerHand) {
            if ((card.getCardColor().equals(cardToCheck.getCardColor()) || card.getCardValue().equals(cardToCheck.getCardValue()))) {
                hasOtherCardsToPlay = true;
                break;
            } else if (card.getCardValue().equals("C")){
                hasOtherCardsToPlay = true;
                break;
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

    public static void addTempCard() { // this method adds a temporary card (that has a colorValue) on the discardDeck whenever a JOKER card is played
        Card dummyCard = new Card(getNewColor(), "Color");
        discardDeck.add(0, dummyCard);
    }

    public static void takeAdditionalCards() { //method contains CHALLENGE and penalty cards
        Player currentPlayer = currentPlayer();
        Player previousPlayer = getPreviousPlayer();
        Card cardToCheck = discardDeck.get(0);
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        String answer = null;

        setChallengeWon(true); //this resets to the default value.

        if (!isPenaltyGiven()) { //check if the penalty of this card has already been given to the previous player so that the current player won't be penalized if true
            if (cardToCheck.getCardValue().equals("+2")) {
                cardIsTakeTwo();
                setPenaltyGiven(true);
                setBlocked(true);
            } else if (cardToCheck.getCardValue().equals("C+4") && discardDeck.size() > 1) {
                if (currentPlayer instanceof Human) {
                    do {
                    System.out.println("Do you like to challenge the previous player? (Y/N)");
                    answer = input.nextLine().toUpperCase();
                        if(answer.equals("HELP")) {
                            callHelp();
                            System.out.println("Do you like to challenge the previous player? (Y/N)");
                            answer = input.nextLine().toUpperCase();
                        } else if (answer.equals("EXIT")){
                            setExit(true);
                            break;
                        } else {
                            System.out.println("Your input is invalid. Please put in Y or N: ");
                            answer = input.nextLine().toUpperCase();
                        }
                    } while (!(answer.equals("Y") || answer.equals("N")) && !answer.equals("EXIT"));
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
                        setBlocked(false);
                    } else {
                        System.out.println("Sorry " + currentPlayer.getName() + " your challenge backfired!");// game starts with the next player
                        setChallengeWon(false);
                        setPenaltyGiven(true);
                        setBlocked(true);
                        System.out.println("And you are also blocked from playing this turn.");
                    }
                }else if (answer.equals("EXIT")) {
                    setExit(true);
                }else {
                    System.out.println("You chose not to challenge " + getPreviousPlayer().getName());
                    cardIsTakeFour();
                    setChallengeWon(false);
                    setPenaltyGiven(true);
                    setBlocked(true);
                    System.out.println("And you are also blocked from playing this turn.");
                }
            }
        }
    }

    public static Player checkWinnerOfCurrentRound() {
        Player winner = currentPlayer();
        if (winner.playersHand.size() == 0) {
            winner.setWinner(true);
            setIsThereAWinnerOfThisRound(true);
        }
        setWinnerOfThisRound(winner);
        return winner;
    }

    public static int computePoints() {
        ArrayList<Card> loserCards = new ArrayList<>();
        int winnerPoints = 0;

        for (Player p : players) {
            if (p !=  getWinnerOfThisRound()) {
                for (Card card : p.playersHand) {
                    loserCards.add(card);
                }
            }
        }

        for (Card card : loserCards) {
            winnerPoints = winnerPoints + card.getCardPoints();
        }

        System.out.println(winnerOfThisRound.getName() + ", you got " + winnerPoints + " for this round!" );
        getWinnerOfThisRound().setPlayerPoints(winnerOfThisRound.getPlayerPoints() + winnerPoints); //we add the points to the points from the previous rounds

        System.out.println();
        System.out.println("Players standing: ");
        System.out.println("The leading player is: " + playerPoints().getName());
        System.out.println();

        return winnerPoints;
    }

    public static void callHelp() {

        try {
            FileReader fr = new FileReader("unoHelp.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            System.out.print("\nYou called for help");
            for (int i = 0; i <= 3; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.print(".");
            }

            System.out.println();
            while (line != null) {
                System.out.println(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                System.out.println("Oops, the HELP file is missing, please call Admin.");
            } else {
                System.out.println("An error occurred while reading the help file.");
            }
        } catch (InterruptedException e) {
            System.out.println("Sorry, something went wrong. Please try again.");
        }
        System.out.println("---------------------------------------------------------");
        System.out.println();
    }
    public static Player playerPoints() {
        Player withHighestPoint = null;
        int highestPoints = 0;
        int temp = 0;
        for (Player p : players) {
            System.out.println(p.getName()+ ": " + p.getPlayerPoints());
            temp = p.getPlayerPoints();
            if (temp > highestPoints) {
                highestPoints = temp;
                withHighestPoint = p;
            }
        }
        return withHighestPoint;
        }
}

