package Classes;

import java.util.List;
import java.util.Random;

import static Classes.Deck.drawOneCard;
import static Classes.Game.*;

public class Bot extends Player {
    public Bot(String name, List<Card> playerInitialCards) {
        super(name, playerInitialCards);
    }

    public Bot(String name) {
        super(name);
    }

    public static Card botMakesAMove() {
        Player currentPlayer = currentPlayer();
        List<Card> botsCard = currentPlayer.playersHand;
        Card cardToCheck = discardDeck.get(0);
        Card validCardToPlay = null;


        for (Card card : botsCard) {
            if (card.getCardColor().equals(cardToCheck.getCardColor()) || card.getCardValue().equals(cardToCheck.getCardValue()) || card.getCardColor().equals(getNewColor())) {
                validCardToPlay = card;
            }
        }

        if (validCardToPlay == null) {
            for (Card card : botsCard) {
                if (card.getCardColor().equals("J")) {
                    validCardToPlay = card;
                }
            }
        }

        if (validCardToPlay != null && currentPlayer.playersHand.size() == 2) {
            Random random = new Random();
            boolean randomCall = random.nextBoolean();
            currentPlayer.setUno(randomCall);
        } else {
            currentPlayer.setUno(false);
        }

        if (currentPlayer.isUno()) {
            System.out.println(currentPlayer.getName() + " called UNO!");
        } else if (!currentPlayer.isUno() && currentPlayer.playersHand.size() == 2) {
            System.out.println("Oh no, you forgot to call UNO!");
            System.out.println("Now you have to get a penalty card!");
            drawOneCard();
            System.out.println("Here is you updated hand:");
            System.out.println("\n" + currentPlayer.toString());
        } else {
        }

        return validCardToPlay;
    }
}