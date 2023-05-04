package Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Card {
    private final String cardValue;
    private final String cardColor;
    private int cardPoints;
    private final static String[] faceValueCollections = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X", "<->", "+2", "+4", "C", "C+4"};
    private final static String[] colorValueCollections = {"R", "G", "B", "Y", "J"};
    private final static int[] pointsCollections = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 50};


    public Card(String cardColor, String cardValue, int cardPoints) {
        this.cardColor = cardColor;
        this.cardValue = cardValue;
        this.cardPoints = cardPoints;
    }

    public Card(String cardValue, String cardColor) {
        this.cardValue = cardValue;
        this.cardColor = cardColor;
    }

    public Card() {
        this.cardColor = "";
        this.cardValue = "";
        this.cardPoints = 0;
    }

    public static int[] getPointsCollections() {
        return pointsCollections;
    }

    public static String[] getFaceValueCollections() {
        return faceValueCollections;
    }

    public static String[] getColorValueCollections() {
        return colorValueCollections;
    }

    public String getCardValue() {
        return cardValue;
    }

    public String getCardColor() {
        return cardColor;
    }

    public int getCardPoints() {
        return cardPoints;
    }

    public String getCard() {
        return cardColor + cardValue;
    }


    @Override
    public String toString() {
        return cardValue + cardColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardValue, card.cardValue) && Objects.equals(cardColor, card.cardColor);
    }
    @Override
    public int hashCode() {
        return Objects.hash(cardValue, cardColor);
    }

}



