package Classes;

import java.util.List;
import static Classes.Bot.botMakesAMove;
import static Classes.Deck.drawOneCard;
import static Classes.Game.*;
import static Classes.Human.humanMakesAMove;
public abstract class Player {
    private String name;
    private int playerPoints;
    protected List<Card> playersHand; //Player's own set of cards
    private static Card playedCard;
    protected static boolean canMakeAMove = true;
    protected boolean uno;
    protected boolean winner;

    public Player(String name, List<Card> playerInitialCards) {
        this.name = name;
        this.playersHand = playerInitialCards;
        this.playerPoints = 0;
    }
    public Player(String name) {
        this.name = name;
        this.playerPoints = 0;
    }
    public String getName() {
        return name;
    }
    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public List<Card> getPlayersHand() {
        return playersHand;
    }

    public static void setCanMakeAMove(boolean canPlay) {
        Player.canMakeAMove = canPlay;
    }

    public void setPlayersHand(List<Card> playersHand) {
        this.playersHand = playersHand;
    }

    public Card getPlayedCard() {
        return playedCard;
    }

    public static void setPlayedCard(Card card) {
        playedCard = card;
    }

    public boolean isUno() {
        return uno;
    }

    public void setUno(boolean uno) {
        this.uno = uno;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return (SKY + name + " " + playersHand + RESET);
    }

    public int getPlayerPoints(int round) {
        return playerPoints;
    }

    //this method will remove the card that is played from the playersHand
    public void removeFromPlayersHand(Card c) {
        playersHand.remove(c);
    }


    public static boolean checkIfPlayerHasCardToPlay() { //method to check if the player's hand has a valid card to be played
        Player currentPlayer = currentPlayer();
        String currentDiscardCardColor = discardDeck.get(0).getCardColor();
        String currentDiscardCardValue = discardDeck.get(0).getCardValue();
        String playerCardColor;

        String playerCardValue;
        boolean hasCardToPlay = false;

        for (Card card : currentPlayer.playersHand) {
            playerCardColor = card.getCardColor();
            playerCardValue = card.getCardValue();
            if (playerCardColor.equals("J")) {
                hasCardToPlay = true;
                break;
            } else if (playerCardColor.equals(newColor)) {
                hasCardToPlay = true;
                break;
            } else if (playerCardColor.equals(currentDiscardCardColor)) {
                hasCardToPlay = true;
                break;
            } else if (playerCardValue.equals(currentDiscardCardValue)) {
                hasCardToPlay = true;break;

            }
        }
        return hasCardToPlay;
    }

    public static void currentPlayersTurn() { //this method will decide who's turn it is based on what's on the discard Deck

        Card cardToCheck = discardDeck.get(0);
        if (cardToCheck.getCardColor().equals("J")) {
            addTempCard();
        }

        if (canMakeAMove) {
            playerEntersCardToPlay();
        } else {
            System.out.println("You have to pass this turn because you still don't have a card to play!");
        }
    }


    public static Card playerEntersCardToPlay() {
        Player currentPlayer = currentPlayer();
        Card cardToPlay = currentPlayer.getPlayedCard();
        if (currentPlayer instanceof Human) {
            cardToPlay = humanMakesAMove();
        } else {
            cardToPlay = botMakesAMove();
            if (currentPlayer.isUno()) {
                System.out.println("Your move: " + cardToPlay + " UNO");
                System.out.println(currentPlayer.getName() + " called UNO!");
            } else if (!currentPlayer.isUno() && currentPlayer.playersHand.size() == 2) {
                System.out.println("Your move: " + cardToPlay);
                System.out.println("Oh no, you forgot to call UNO!");
                System.out.println("Now you have to get a penalty card!");
                drawOneCard();
                System.out.println("Here is you updated hand:");
                System.out.println(currentPlayer.toString());
            }else {
                System.out.println("Your move: " + cardToPlay );
            }
        }
        setPlayedCard(cardToPlay);
        try {
            if (cardToPlay.getCardColor().equals("J")) {
                setJoker(true);
                setColorIfCardIsJoker();
            } else if (isCardValid()) {
                setNewColor(null);
            }

            if (cardToPlay.getCardValue().equals("C+4") || cardToPlay.getCardValue().equals("+2")) {
                setPenaltyGiven(false);
            }

        } catch (NullPointerException e) {
            System.out.println("Player decided to exit game.....");
        }
        setPlayedCard(cardToPlay);
        currentPlayer.setUno(false);
        return cardToPlay;
    }


    public static boolean canPlayerMakeAMove() {
        Player currentPlayer = currentPlayer();
        Card cardToCheck = discardDeck.get(0);
        boolean canPlay = false;
        if (cardToCheck.getCardColor().equals("J")) {
            addTempCard();
        }
        if (checkIfPlayerHasCardToPlay()) {
            canPlay = true;
        } else {
            System.out.println(currentPlayer.getName() + ", it looks like you don't have a card to play this turn.");
            System.out.println("Sorry but you have to draw a card!");
            drawOneCard();
            System.out.println("Here's your updated hand:");
            System.out.println(currentPlayer.toString());
            if (!checkIfPlayerHasCardToPlay()) {
                System.out.println("Oh no, you STILL do not have a card to play!");
                System.out.println("Let's move on to the next player.");
                canPlay = false;
            } else if (checkIfPlayerHasCardToPlay()) {
                System.out.println("You've drawn a card you can play!");
                canPlay = true;
            }
        }
        setCanMakeAMove(canPlay);
        return canPlay;
    }
}