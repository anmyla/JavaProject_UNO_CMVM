package Classes;


//This Class defines the cards to be used in the game.
//This is where we declare and initialize the cards and their corresponding value, color, and points
public class Cards {

    private String cardValue = "";
    private String cardColor = "";
    private String card= "";


    protected static String[] faceValue = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X", "<->", "+2", "+4", "JC", "JC+4"};
    protected static String[] colorValue = {"R", "G", "B", "Y", ""};
    protected static int[] points = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 50};


    public Cards(String[] faceValue, String[] colorValue, int[] points) {
        this.cardValue = card;
        this.faceValue = faceValue;
        this.colorValue = colorValue;
        this.points = points;
    }

    public Cards(String cardValue, String faceValue, int points) {

    }


    public static int[] getPoints() {
        return points;
    }

    public static String[] getFaceValue() {
        return faceValue;
    }

    public static String[] getColorValue() {
        return colorValue;
    }

    public String getValue() {
        for (String value : faceValue) {
            cardValue = value;
        }
        return cardValue;
    }

    public String getColor() {
        for (String color : colorValue) {
            cardColor = color;
        }
        return cardColor;
    }

    public String getCard(){
        card = getCard() + getValue();
        return card;
    }


}
