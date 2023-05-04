package Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    List<Player> players = new ArrayList<>();
    Deck cardDeck = new Deck(108);
    List<Card> discardDeck = new ArrayList<>();


    public void setUpPlayers(int humanPlayers) {
        //Just human players for now. A simple method to collect player names and add it to the List<Players>
        Scanner playerInput = new Scanner(System.in);

        for (int i = 0; i < humanPlayers; i++) {
            System.out.println("Please enter a name for PLAYER " + (i + 1) + ": ");
            String name = playerInput.nextLine();

            players.add(new Human(name));
        }
    }

    public void distributeInitialCardsToPlayers() {
        cardDeck.initialDeck();
        cardDeck.shuffleDeck();
//        System.out.println();
//        cardDeck.printDeck();
        for (Player player : players) {
            player.setPlayerPoints(0);
            player.setPlayersHand(cardDeck.distributeInitialCards());
        }
    }


    public void printPlayer() {
        //Just to check players and their cards
        for (Player player : players) {
            System.out.print(player.toString());
            System.out.println();
        }
    }

    public List<Card> getDiscardDeck() {
        return this.discardDeck;
    }

    public void layFirstCard() {  //this method will take one Card from the DECK and add it to the DISCARD DECK to start the game.
        discardDeck.add(cardDeck.getCardDeck().get(0));
        cardDeck.removeFromCardDeck();
    }

    public int nextTurn() { //*** this method is working
        int currentPlayerIndex = 0; // start with the first player
        Card currentCard = discardDeck.get(discardDeck.size() - 1); // get the current card from the discard deck...
        if (discardDeck.size() == 1) {
            currentPlayerIndex = 0;
        }
        else if (currentCard.getCardValue().equals("<->")) {         // If the current card is "reverse", the previous player should play
            currentPlayerIndex--;
            if (currentPlayerIndex < 0) {
                currentPlayerIndex = players.size() - 1;
            }
        } else if (currentCard.getCardValue().equals("X")) {   // If the current card is "block", the next player skips a turn
            currentPlayerIndex++;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
            currentPlayerIndex++;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
        } else { // If the current card is a regular card, the next player should play
            currentPlayerIndex++;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
        }
        return currentPlayerIndex;
    }

    public Player currentPlayer() {
        Player currentPlayer = players.get(nextTurn());
        return currentPlayer;
    }

    public void playerToPlay() {
        Player currentPlayer = currentPlayer();
        System.out.println(currentPlayer.toString() + " it's your turn to play.");
    }

    public void acceptPlayersInput() { //this method will take one Card from the player's initialCards and add it to the DISCARD DECK.
        Player currentPlayer = currentPlayer();
        Card playedCard = currentPlayer.playerEntersCardToPlay();
        discardDeck.add(playedCard);
        currentPlayer.removeFromPlayersHand(playedCard);
    }

    public void printDiscardDeck() {  // this method will print the cards in the DISCARD DECK
        for (Card card : discardDeck) {
            System.out.print(card + ", ");
        }
    }
}
