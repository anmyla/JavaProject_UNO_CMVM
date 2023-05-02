package Classes;

import java.util.List;

public abstract class Player {
    private String name;
    private int playerPoints;
    private List<Card> playerInitialCards;

    public Player(String name, List<Card> playerInitialCards) {
        this.name = name;
        this.playerInitialCards = playerInitialCards;
        this.playerPoints = 0;
    }

    public Player(String name) {
        this.name = name;
        this.playerPoints = 0;
    }

    public List<Card> getPlayerInitialCards() {
        return this.playerInitialCards;
    }

    public String getName() {
        return name;
    }


    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public void setPlayerInitialCards(List<Card> playerInitialCards) {
        this.playerInitialCards = playerInitialCards;
    }

    @Override
    public String toString() {
        return name + playerInitialCards;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

}