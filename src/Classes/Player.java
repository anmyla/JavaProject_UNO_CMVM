package Classes;

import java.util.List;
import java.util.Scanner;

import static Classes.Deck.drawOneCard;
import static Classes.Game.currentPlayer;
import static Classes.Game.playerHasCardToPlay;


public abstract class Player {
    private String name;
    private int playerPoints;
    protected List<Card> playersHand; //Player's own set of cards

    private boolean turn;
    private Card playedCard;
    static boolean play = true;

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

    @Override
    public String toString() {
        return name + playersHand;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    //this method will remove the card that is played from the playersHand
    public void removeFromPlayersHand(Card c) {
        playersHand.remove(c);
    }

    public static boolean playOrPass() {
        Player currentPlayer = currentPlayer();
        if (!playerHasCardToPlay()) {
            System.out.println(currentPlayer.getName() + ", it looks like you don't have a card to play this round");
            System.out.println("Sorry but you have to draw a card!");
            drawOneCard();
            System.out.println("Here's your updated Cards!");
            System.out.println(currentPlayer.toString());
            if (!playerHasCardToPlay()) {
                System.out.println("Oh no, you STILL do not have a card to play!");
                setPlay(false);
            }
            else {
                setPlay(true);
            }
        }
        else {
            setPlay(true);
        }
        return isPlay();
    }


}

