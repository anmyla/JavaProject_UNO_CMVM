package Classes;

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

    public Card(String cardColor, String cardValue) {
        this.cardColor = cardColor;
        this.cardValue = cardValue;
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
        return getCard();
    }


// Here, we override the equals(). With this method, we can compare two Card objects for
// equality based on the values of their cardValue and cardColor fields.
// This is particularly useful when we need to perform equality checks
// or use objects in data structures that rely on equality, such as HashMap, HashSet,
// or when using objects as keys in a Map.

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