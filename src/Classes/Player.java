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

    private boolean turn;
    private Card playedCard;
    protected static boolean play = true;
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

    public static boolean isPlay() {
        return play;
    }

    public static void setPlay(boolean play) {
        Player.play = play;
    }

    public void setPlayersHand(List<Card> playersHand) {
        this.playersHand = playersHand;
    }

    public Card getPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(Card playedCard) {
        this.playedCard = playedCard;
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
        return (SKY + name + "  " + playersHand + RESET);
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    //this method will remove the card that is played from the playersHand
    public void removeFromPlayersHand(Card c) {
        playersHand.remove(c);
    }


    public static boolean playerHasCardToPlay() { //method to check if the player's hand has a valid card to be played
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

    public static void currentPlayersTurn() { //this method will decide who's turn it is based on what's on the discard Deck
        Card cardToCheck = discardDeck.get(0);
        if (cardToCheck.getCardColor().equals("J")) {
            addTempCard();
        }
        if (isPlay()) {
            playerEntersCardToPlay();
        } else {
            System.out.println("You have to pass this turn because you still don't have a card to play!");
        }
    }


    public static Card playerEntersCardToPlay() {
        Player currentPlayer = currentPlayer();
        Card cardToPlay = null;

        if (currentPlayer instanceof Human) {
            cardToPlay = humanMakesAMove();
        } else {
            cardToPlay = botMakesAMove();
            if (currentPlayer.isUno()) {
                System.out.println("Your move: " + cardToPlay + " UNO");
            } else {
                System.out.println("Your move: " + cardToPlay);
                currentPlayer.setUno(false);
            }
        }

        currentPlayer.setPlayedCard(cardToPlay);

        if (cardToPlay.getCardColor().equals("J")) {
            setJoker(true);
            setColorIfCardIsJoker();
        } else {
            setNewColor(null);
        }
        if (cardToPlay.getCardValue().equals("C+4") || cardToPlay.getCardValue().equals("+2")) {
            setPenaltyGiven(false);
        }
        return cardToPlay;
    }


    public static boolean canPlay() {
        Player currentPlayer = currentPlayer();
        Card cardToCheck = discardDeck.get(0);
        boolean canPlay = false;

        if (cardToCheck.getCardColor().equals("J")) {
            addTempCard();
        }

        cardToCheck = discardDeck.get(0);

        if (!playerHasCardToPlay()) {
            System.out.println(currentPlayer.getName() + ", it looks like you don't have a card to play this turn.");
            System.out.println("Sorry but you have to draw a card!");
            drawOneCard();
            System.out.println("Here's your updated Cards!");
            System.out.println(currentPlayer.toString());
            if (!playerHasCardToPlay()) {
                System.out.println("Oh no, you STILL do not have a card to play!");
                canPlay = false;
            } else {
                canPlay = true;
            }
        } else if (!isHasCardToPlay() && cardToCheck.getCardValue().equals("+2")) {
            System.out.println("You have to take 2 cards.");
            cardIsTakeTwo();
            currentPlayer.playersHand.toString();
            if (!playerHasCardToPlay()) {
                System.out.println("Yous still do not have cards to play!");
                canPlay = false;
            } else {
                canPlay = true;
            }
        } else if (!isHasCardToPlay() && cardToCheck.getCardValue().equals("+4")) {
            System.out.println("You have to take 4 cards.");
            cardIsTakeFour();
            currentPlayer.playersHand.toString();
            if (!playerHasCardToPlay()) {
                System.out.println("Yous still do not have cards to play!");
                addTempCard();
                canPlay = false;
            } else {
                canPlay = true;
            }
        } else {
            canPlay = true;
        }
        setPlay(canPlay);
        return canPlay;
    }

}

