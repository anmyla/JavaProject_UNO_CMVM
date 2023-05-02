package Classes;

import java.util.Objects;

public class Cards {
    private final String cardValue;
    private final String cardColor;
    private int cardPoints;
    private final static String[] faceValueCollections = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X", "<->", "+2", "+4", "JC", "JC+4"};
    private final static String[] colorValueCollections = {"R", "G", "B", "Y", ""};
    private final static int[] pointsCollections = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 50};


    public Cards(String cardColor, String cardValue, int cardPoints) {
        this.cardColor = cardColor;
        this.cardValue = cardValue;
        this.cardPoints = cardPoints;
    }

    public Cards(String cardValue, String cardColor) {
        this.cardValue = cardValue;
        this.cardColor = cardColor;
    }

    public Cards() {
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
    public int hashCode() {
        return Objects.hash(cardColor, cardValue, cardPoints);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cards card = (Cards) obj;
        return Objects.equals(cardColor, card.cardColor) && Objects.equals(cardValue, card.cardValue);
    }

    @Override
    public String toString() {
        return cardValue + cardColor;
    }
}
