package Classes;
import java.util.List;

public class Human extends Player {
    public Human(String name, List<Card> playerInitialCards) {
        super(name, playerInitialCards);
    }

    public Human(String name) {
        super(name);
    }

}