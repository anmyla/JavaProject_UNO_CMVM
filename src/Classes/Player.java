package Classes;

import java.util.List;
import java.util.Scanner;

public abstract class Player {
    private String name;
    private int playerPoints;
    protected List<Card> playersHand; //Player's own set of cards

    private boolean turn;

    private boolean joker = false;

    private String newColor;

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

    public void setPlayersHand(List<Card> playersHand) {
        this.playersHand = playersHand;
    }

    public boolean isJoker() {
        return joker;
    }

    public String getNewColor() {
        return newColor;
    }

    public void setJoker(boolean joker) {
        this.joker = joker;
    }
    public void setNewColor(String newColor) {
        this.newColor = newColor;
    }

    @Override
    public String toString() {
        return name + playersHand;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public Card playerEntersCardToPlay() {
        Scanner cardInput = new Scanner(System.in);
        System.out.println("ENTER CARD COLOR:"); //Player chooses a card color (R,B,Y,G, J)
        String cardColor = cardInput.nextLine();
        System.out.println("ENTER CARD VALUE:"); //Player chooses a value (1,2,3, <->, etc.)
        String cardValue = cardInput.nextLine();
        Card cardToPlay = new Card(cardColor,cardValue);

        if (cardToPlay.getCardValue().equals("C")) {
            System.out.println("What Color should we play next? (R, G, B, Y) :");
            joker = true;
            newColor= cardInput.nextLine();
            setNewColor(newColor);
        }
        else {
            joker = false;
        }
        System.out.println(cardToPlay.toString());
        return cardToPlay;
    }

    //this method will remove the card that is played from the playersHand
    public void removeFromPlayersHand(Card c) {
        playersHand.remove(c);
    }


}


