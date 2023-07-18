package Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static Classes.App.setExit;
import static Classes.Deck.drawOneCard;
import static Classes.Game.*;
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

            if (!inputMove.equals("EXIT") && !inputMove.equals("HELP") && inputMove.length() > 1 && inputMove != null && !input.equals(' ')) {
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

                    for (int i = stringBuilder1.length() + 2; i < inputArray.length; i++) {
                        stringBuilder2.append(inputArray[i]);
                    }
                    call = stringBuilder2.toString();
                } else {
                    cardColor = String.valueOf(inputArray[0]);
                    cardValue = String.valueOf(inputArray[1]);
                }
            } else if (inputMove.equals("EXIT")) {
                setExit(true); // we still have to make this part
                break;

            } else if (inputMove.equals("HELP")) {
                callHelp();
                printDiscardDeck();
                System.out.println("\n" + currentPlayer.toString());
            }

            if (!inputMove.equals("HELP") && !inputMove.equals("EXIT") && inputMove.length() > 1 && inputMove != null && !input.equals(' ')) {
                if (cardColor.equals("J") && call.equals("+4")) {
                    call = null;
                    cardValue = "C+4";
                } else if (inputMove == null || inputMove.trim().isEmpty() || inputMove.equals("")
                        || inputMove.length() < 1 || !validCardColorList.contains(cardColor)
                        || !validCardValuesList.contains(cardValue) && (!inputMove.equals("EXIT") || !inputMove.equals("HELP"))) {
                    System.out.println("There is no such move, please try again!");
                }
            }
        } while (!validCardColorList.contains(cardColor) || !validCardValuesList.contains(cardValue) || inputMove.equals(" "));


        Card cardToPlay = new Card(cardColor, cardValue);

        if (call != null) {
            if (call.equals("UNO")) {
                if (currentPlayer.playersHand.size() == 2) {
                    currentPlayer.setUno(true);
                    System.out.println(currentPlayer.getName() + " called UNO!");
                } else if (currentPlayer.playersHand.size() > 2) {
                    System.out.println("That was a foul call! We'll accept your move but you have to draw a penalty card!");
                    drawOneCard();
                    System.out.println("Here's your updated hand:");
                    System.out.println("\n" + currentPlayer.getPlayersHand().toString());
                    currentPlayer.setUno(false);
                }

            } else if (currentPlayer.playersHand.size() == 2 && !call.equals("UNO")) {
                currentPlayer.setUno(false);
                System.out.println(currentPlayer.getName() + ", you forgot to call UNO.");
                System.out.println("Sorry, but you have to draw a penalty card!");
                drawOneCard();
                System.out.println("Here's your updated hand:");
                System.out.println("\n" + currentPlayer.getPlayersHand().toString());
                currentPlayer.setUno(false);
            }

        } else if (!currentPlayer.isUno() && currentPlayer.playersHand.size() == 2) {
            System.out.println("Oh no, you forgot to call UNO!");
            System.out.println("Now you have to get a penalty card!");
            drawOneCard();
            System.out.println("\n" + currentPlayer.getPlayersHand().toString());
        }
        return cardToPlay;
    }
}