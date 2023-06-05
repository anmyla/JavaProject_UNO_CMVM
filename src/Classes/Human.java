package Classes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static Classes.Deck.drawOneCard;
import static Classes.Game.currentPlayer;

public class Human extends Player {
    public Human(String name, List<Card> playerInitialCards) {
        super(name, playerInitialCards);
    }

    public Human(String name) {
        super(name);
    }

    public static Card humanMakesAMove() {
        Player currentPlayer = currentPlayer();
        String[] validCardValues = Card.getFaceValueCollections();
        List<String> validCardValuesList = new ArrayList<>(Arrays.asList(validCardValues));

        String[] validCardColors = Card.getColorValueCollections();
        List<String> validCardColorList = new ArrayList<>(Arrays.asList(validCardColors));

        Scanner input = new Scanner(System.in);
        Card cardToPlay;
        String inputCard;
        String cardColor = null;
        String cardValue = null;
        String call = null;

        System.out.println("WHAT'S YOUR MOVE?:"); // Player enters both color and value
        inputCard = input.nextLine().toUpperCase();

        cardColor = String.valueOf(inputCard.charAt(0));
        cardValue = String.valueOf(inputCard.charAt(1));

        while (!validCardColorList.contains(cardColor) || !validCardValuesList.contains(cardValue)) {
            System.out.println("Sorry, there's no such card. Please enter a valid card: ");
            inputCard = input.nextLine().toUpperCase();
            if (inputCard.contains("UNO")) {
                call = "UNO";
            }
            cardColor = String.valueOf(inputCard.charAt(0));
            cardValue = String.valueOf(inputCard.charAt(1));
        }

        cardToPlay = new Card(cardColor, cardValue);

        if (cardToPlay != null && inputCard.contains(call)) {
            if (currentPlayer.playersHand.size() == 2) {
                currentPlayer.setUno(true);
            } else {
                System.out.println("That was a foul call. You have to draw a card!");
                drawOneCard();
            }
        }
        return cardToPlay;
    }

}

