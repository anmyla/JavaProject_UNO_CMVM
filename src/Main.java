import Classes.App;

import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static  void main (String[] args){
        System.out.println("Welcome to an UNO Game!");
        Scanner input = new Scanner(System.in);
        App app = new App(input, System.out);
        app.Run();
        input.close();
        System.out.println("Das Programm wird beendet ...");
    }
}
