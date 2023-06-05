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
        StringBuilder stringBuilder = new StringBuilder();

        Scanner input = new Scanner(System.in);
        String inputCard;
        String cardColor = null;
        String cardValue = null;
        String move = null;
        String call = null;


        System.out.println("WHAT'S YOUR MOVE?:"); // Player enters both color and value
        inputCard = input.nextLine().toUpperCase();


        if(input.equals("EXIT")) {
            move = "EXIT";
        }
        else if (input.equals("HELP")){
            move = "HELP";
        }
        else{
            char [] inputArray = inputCard.toCharArray();
            for (int i = 1; i <= 4; i++) {
                while (i < inputArray.length || inputArray[i] != ' ') {
                    stringBuilder.append(inputArray[i]);
                    i++;
                }
            }
            cardColor = String.valueOf(inputArray[0]);
            cardValue = stringBuilder.toString();

            while (!validCardColorList.contains(cardColor) || !validCardValuesList.contains(cardValue)) {
                System.out.println("Sorry, there's no such card. Please enter a valid card: ");
                inputCard = input.nextLine().toUpperCase();
                if (inputCard.contains("UNO")) {
                    call = "UNO";
                }
                inputArray = inputCard.toCharArray();
                for (int i = 1; i <= 4; i++) {
                    while (i < inputArray.length && inputArray[i] != ' ') {
                        stringBuilder.append(inputArray[i]);
                        i++;
                    }
                }
            }
        }

        Card cardToPlay = new Card(cardColor, cardValue);

        if(call != null) {
            if (cardToPlay != null && currentPlayer.playersHand.size() == 2 && call.equals("UNO")) {
                currentPlayer.setUno(true);
                System.out.println("UNO!!!!!!!");
            } else if (cardToPlay != null && currentPlayer.playersHand.size() > 2 && call.equals("UNO")) {
                System.out.println("That was a foul call. You have to draw a card!");
                drawOneCard();
                currentPlayer.setUno(false);
            }
        }
        else {
            currentPlayer.setUno(false);
        }
        return cardToPlay;
    }
}

