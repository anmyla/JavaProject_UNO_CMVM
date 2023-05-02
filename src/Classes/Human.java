package Classes;
import java.util.List;

public class Human extends Player {
    public Human(String name, List<Cards> playerCards) {
        super(name, playerCards);
    }

    public Human(String name) {
        super(name);
    }

    @Override
    public void printCards() {
        // automatically created
    }
}