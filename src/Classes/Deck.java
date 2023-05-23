package Classes;

import java.util.*;

public class Deck {
    public static List<Card> cardDeck;

    public Deck(int totalCards) {  //Constructor
        cardDeck = new ArrayList<>(totalCards);
    }

    public List<Card> getCardDeck() {
        return cardDeck;
    }


    public List<Card> initialDeck() { // this method will fill e fresh card deck
        String[] faceValue = Card.getFaceValueCollections(); //(length = 16)
        String[] colorValue = Card.getColorValueCollections(); // (length = 5)
        int[] points = Card.getPointsCollections(); // (length = 12)

        for (int i = 0; i < colorValue.length - 1; i++) { // 4 Repetitions ColorValue 0 = R, 1 = G, 2 = B, 3 = Y
            cardDeck.add(new Card(colorValue[i], faceValue[0],  points[0])); // this adds the following cards: ( R0, G0, B0, Y0)
            for (int j = 1; j < faceValue.length - 4; j++) {   // 11 repetitions
                cardDeck.add(new Card(colorValue[i], faceValue[j],  points[j])); // this adds the following cards: (1st copy R1 - <->, G1 - <->, B1 - <->, Y1 - <->)
                cardDeck.add(new Card(colorValue[i], faceValue[j], points[j])); // this adds the following cards: (2nd copy R1 - <->, G1 - <->, B1 - <->, Y1 - <->)
            }
            for (int j = faceValue.length - 4; j < faceValue.length - 2; j++) { //2 repetitions
                cardDeck.add(new Card(colorValue[i], faceValue[12],  points[10])); //this adds the following cards: (1st copy +2)
            }
        }
        for (int i = 0; i < 4; i++) { //4 repetitions for non-colored cards (joker cards)
            cardDeck.add(new Card(colorValue[4], faceValue[14],  points[points.length - 1])); //this adds the following cards: JC (4 copies)
            cardDeck.add(new Card( colorValue[4], faceValue[15], points[points.length - 1])); //this adds the following cards: JC+4 (4 copies)
        }
        return cardDeck;
    }

    public void printDeck() {  // this method will print the cards in the deck
        for (Card card : cardDeck) {
            System.out.print(card + ", ");
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cardDeck);
    }


    public List<Card> distributeInitialCards() {
        List<Card> playerInitialCards = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            playerInitialCards.add(cardDeck.get(cardDeck.size() - 1));
            cardDeck.remove(cardDeck.size() - 1);
        }
        return playerInitialCards;
    }

    public void removeFromCardDeck() {
        cardDeck.remove(0);
    }


}

