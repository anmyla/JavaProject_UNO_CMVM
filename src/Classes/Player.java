package Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Player {
    private String name;
    private int playerPoints;
    protected List<Card> playersHand; //Player's own set of cards

    private boolean turn;



    private Card playedCard;

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

    public Card playerEntersCardToPlay() {
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
            cardColor = cardInput.nextLine();
        }

        Card cardToPlay = new Card(cardColor, cardValue);
        Game.isCardJokerColor();

        setPlayedCard(cardToPlay);
        return cardToPlay;
    }

    //this method will remove the card that is played from the playersHand
    public void removeFromPlayersHand(Card c) {
        playersHand.remove(c);
    }


}


