package Classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Player {
    private String name;
    private int playerPoints;
    private List<Cards> playerCards;

    private boolean mustDraw = true;

    public Player(String name, List<Cards> playerCards) {
        this.name = name;
        this.playerCards = playerCards;
        this.playerPoints = 0;
    }

    public Player(String name) {
        this.name = name;
        this.playerPoints = 0;
    }

    public void setPlayerCards(List<Cards> playerCards) {
        this.playerCards = playerCards;
    }

    public List<Cards> getPlayerCards() {
        return this.playerCards;
    }

    public String getName() {
        return name;
    }

    public boolean getMustDraw() {
        return mustDraw;
    }

    public void setMustDraw(boolean mustDraw) {
        this.mustDraw = mustDraw;
    }


    @Override
    public String toString() {
        return name;
    }

    public Cards areCardsOnHands(String cardToPlay) {
        Cards haveCards = new Cards();
        for (Cards cardsOnHand : this.getPlayerCards()) {
            if (cardsOnHand.getCard().equals(cardToPlay.toUpperCase())) {
                haveCards = cardsOnHand;
                break;
            } else {
                haveCards = null;
            }
        }
        return haveCards;
    }

    public void takeCards(List<Cards> cardsToBeTaken) {
        playerCards.addAll(cardsToBeTaken);
    }


    public int getPlayerPoints() {
        return playerPoints;
    }

    public void removeCardFromHand(Cards card) {
        Iterator<Cards> it = this.getPlayerCards().iterator();
        while (it.hasNext()) {
            Cards cardOnHand = it.next();
            if (cardOnHand.equals(card)) {
                it.remove();
                break;
            }
        }
    }

    public boolean checkUno() { //checks if player only has 1 card
        return playerCards.size() == 1;
    }

    public boolean cardColorCheck(String color) { // check if the player has a color to play
        for (Cards cardOnHand : this.getPlayerCards()) {
            if (cardOnHand.getCardColor().equals(color) && (cardOnHand.getCardPoints() < 20)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRightInput(String color) {
        color = color.toUpperCase();
        for (int i = 0; i < Cards.getColorValueCollections().length - 1; i++) {
            if (color.equals(Cards.getColorValueCollections()[i])) {
                return true;
            }
        }
        System.out.println("Invalid color input");
        return false;
    }

    public abstract void printCards();
}