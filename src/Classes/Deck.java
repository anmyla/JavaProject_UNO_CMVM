package Classes;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    public List<Cards> cards;
    public static ArrayList<Cards> cardDeck = new ArrayList<>();

    public Deck(ArrayList<Cards> cardDeck) {
        this.cardDeck = cardDeck;
    }

    public static void initialDeck() {
        String[] faceValue = Cards.getFaceValue(); //(length = 16)
        String[] colorValue = Cards.getColorValue(); // (length = 5)
        int [] points = Cards.getPoints(); // (length = 12)

        for (int i = 0; i < colorValue.length-1; i++) { // 4 Repetitions ColorValue 0= R, 1 = G, 2 = B, 3 = Y
            cardDeck.add(new Cards(colorValue[i], faceValue[0], points[0])); // this adds the following cards: ( R0, G0, B0, Y0)
            for (int j = 1; j < faceValue.length-4; j++) {   // 11 repetitions
                cardDeck.add( new Cards(faceValue[j], colorValue[i], points[j] )); // this adds the following cards: (1st copy R1 - <->, G1 - <->, B1 - <->, Y1 - <->)
                cardDeck.add( new Cards(faceValue[j], colorValue[i], points[j] )); // this adds the following cards: (2nd copy R1 - <->, G1 - <->, B1 - <->, Y1 - <->)
            }

            for (int j = faceValue.length-4; j < faceValue.length-2; j++) { //2 repetitions
                cardDeck.add( new Cards(faceValue[j], colorValue[i], points[points.length-2] )); //this adds the following cards: (1st copy +2, +4)
                cardDeck.add( new Cards(faceValue[j], colorValue[i], points[points.length-2] )); //this adds the following cards: (2nd copy +2, +4)
            }
        }

        for (int i = 0; i < 4; i++) { //4 repetitions for non-colored cards (joker cards)
            cardDeck.add( new Cards(faceValue[14], colorValue[4], points[points.length-1] )); //this adds the following cards: JC (4 copies)
            cardDeck.add( new Cards(faceValue[15], colorValue[4], points[points.length-1] )); //this adds the following cards: JC+4 (4 copies)
        }
    }

    public void shuffleDeck() {
    }

    public void distributeInitialCardsToPlayers() {

    }

    public void layOneInitialCard() {

    }

}
