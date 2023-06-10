package Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static Classes.Deck.drawOneCard;
import static Classes.Game.callHelp;
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
        String inputMove;
        String cardColor = null;
        String cardValue = null;
        String call = null;


        do {
            System.out.println("WHAT'S YOUR MOVE?:"); // Player enters both color and value
            inputMove = input.nextLine().toUpperCase();

            StringBuilder stringBuilder1 = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();

            if (!inputMove.equals("EXIT") && !inputMove.equals("HELP") && inputMove.length()> 1 && inputMove != null) {

                char[] inputArray = inputMove.toCharArray();

                if (inputArray.length > 1 && inputArray.length < 9) {
                    for (int i = 1; i < inputArray.length; i++) {
                        if (inputArray[i] == ' ') {
                            break; // Exit the loop when a space is encountered
                        }
                        stringBuilder1.append(inputArray[i]);
                    }

                    cardColor = String.valueOf(inputArray[0]);
                    cardValue = stringBuilder1.toString();

                    // Extract the remaining characters after the space

                    for (int i = stringBuilder1.length() + 1; i < inputArray.length; i++) {
                        stringBuilder2.append(inputArray[i]);
                    }
                    call = stringBuilder2.toString();
                } else {
                    cardColor = String.valueOf(inputArray[0]);
                    cardValue = String.valueOf(inputArray[1]);
                }
            } else if (inputMove.equals("EXIT")) {
                System.exit(0); // we still have to make this part
                break;

            } else if (inputMove.equals("HELP")) {
                callHelp();
            }

            if(inputMove.equals("HELP")) {
                System.out.println();
            }
            else  if (inputMove == null || inputMove.trim().isEmpty() || inputMove.equals("") || inputMove.length()<1 || !validCardColorList.contains(cardColor) || !validCardValuesList.contains(cardValue) && (!inputMove.equals("EXIT") || !inputMove.equals("HELP"))) {
                System.out.println("There is no such move, please try again!");
            }

        } while (!validCardColorList.contains(cardColor) || !validCardValuesList.contains(cardValue) && (!inputMove.equals("EXIT") || !inputMove.equals("HELP")));

        Card cardToPlay = new Card(cardColor, cardValue);

        if (call != null) {
            if (cardToPlay != null && currentPlayer.playersHand.size() == 2 && call.equals("UNO")) {
                currentPlayer.setUno(true);
                System.out.println(currentPlayer.getName() + " called UNO!");
            } else if (cardToPlay != null && currentPlayer.playersHand.size() > 2 && call.equals("UNO")) {
                System.out.println("That was a foul call. You have to draw a card!");
                drawOneCard();
                currentPlayer.setUno(false);
            }
        } else {
            currentPlayer.setUno(false);
        }
        return cardToPlay;
    }
}

