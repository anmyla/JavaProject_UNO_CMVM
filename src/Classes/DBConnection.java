package Classes;

import java.sql.*;
import java.util.List;


public class DBConnection {

    //Datenbankverbindung herstellen
    private static Connection con = null;

    public DBConnection(String dbName) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:uno.db");
    }

    //Diese Methode erstellt eine Tabelle mit dem Namen uno ODER löscht diese falls vorhanden
    //Tabelle hat 2 Spalten Player & Points
    public static void createTable() {
        String create = "CREATE TABLE uno (player varchar (255) PRIMARY KEY, points int)";
        String drop = "DROP TABLE IF EXISTS uno";

        try {
            Statement statement = con.createStatement(); //Mit dem Statement-Objekt können SQL Anweisungen an die Datenbank gesendet werden
            statement.executeUpdate(drop);
            statement.executeUpdate(create);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //Diese Methode fügt Punkte für jeden Spieler hinzu

    public static void hinzufügenOrUpdaten(List<Player> players) {

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM uno"); //Results werden im ResultSet Objekt gespeichert
            if (resultSet.next()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
