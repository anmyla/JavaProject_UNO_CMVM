package Classes;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
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
    static boolean pass = false;

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

    public static boolean isPass() {
        return pass;
    }

    public static void setPass(boolean pass) {
        Player.pass = pass;
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
        Scanner playerInput = new Scanner(System.in);
        if (playerHasCardToPlay()) {
            System.out.println(currentPlayer().getName() + ", will you play(Y) or pass(N)?" );
            String toPlayOrPass = playerInput.nextLine().toLowerCase();

            while (!toPlayOrPass.equals("y") && !toPlayOrPass.equals("n")) {
                System.out.println("Invalid input. Please enter 'Y' for play or 'N' for pass: ");
                toPlayOrPass = playerInput.nextLine().toLowerCase();
            }

            if (toPlayOrPass.equals("n")) {
                System.out.println("Understood. You may chose to pass but you must draw a card");
                drawOneCard();
                System.out.println("Here's your updated Cards!");
                System.out.println(currentPlayer.toString());
                pass = true;
            }
            else {
                pass = false;
            }
        }
        else {
            System.out.println(currentPlayer.getName()+ ", it looks like you don't have a card to play this round");
            System.out.println("Sorry but you have to draw a card!");
            drawOneCard();
            System.out.println("Here's your updated Cards!");
            System.out.println(currentPlayer.toString());
            pass = true;
        }
        setPass(pass);
        return pass;
    }


}


