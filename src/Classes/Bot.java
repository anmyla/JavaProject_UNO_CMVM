package Classes;

import java.util.List;

public class Bot extends Player{
    public Bot(String name, List<Card> playerInitialCards) {
        super(name, playerInitialCards);
    }

    public Bot(String name) {
        super(name);
    }
}
