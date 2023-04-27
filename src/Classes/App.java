package Classes;

import java.io.PrintStream;
import java.util.Scanner;

public class App {
    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private int figureNr;
//    private Figure figure;

    public int size;

    // Konstruktor
    // input wird verwendet um Daten vom Benutzer einzulesen (verwendet scanner)
    // output wird verwendet um Text auf der Konsole auszugeben (verwendet sysout)
    public App(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }


    //die Gameloop
    public void Run() {
        initialize();
        printState();

        while (!exit) {
            readUserInput();
            updateState();
            printState();
        }
    }

    private void initialize() {
        //TODO: Initialisierungen hier durchführen
    }

    private void readUserInput() {
        //TODO: Alle Eingaben der Benutzer einlesen
//        inputFigure();
//        if (figureNr != 0) {
//            inputSize();
//        }
    }

    private void updateState() {
        //TODO: Benutzereingaben verarbeiten

    }

    private void printState() {
        //TODO: Ausgabe des aktuellen Zustands
//        if (exit == true) {
//            System.out.println("Goodbye!");
//        } else if (figure != null) {
//            output.println(figure);
//        }
    }

}