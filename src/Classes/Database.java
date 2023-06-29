package Classes;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import static Classes.Game.getRound;

import static Classes.Game.players;

public class Database {

    private static final String CREATETABLE = "CREATE TABLE Rounds (Player varchar(100) NOT NULL, Round int NOT NULL, Points int NOT NULL, CONSTRAINT PK_Rounds PRIMARY KEY (Player, Round));";
    private static final String INSERT_TEMPLATE = "INSERT INTO Rounds (Player, Round, Points) VALUES ('%1s', %3d, %4d);";
    private static final String SELECT_BYPLAYERANDROUND = "SELECT Player, SUM(Points) AS Points FROM Rounds WHERE Player = '%1s' AND Round = %3d AND Points = %4d;";


    public Database() {

    }


    public static void createDatabase() {

        try {
            SqliteClient client = new SqliteClient("demodatabase.sqlite");
            if (client.tableExists("Rounds")) {
                client.executeStatement("DROP TABLE Rounds;");
            }
            client.executeStatement(CREATETABLE);
            client.executeStatement(String.format(INSERT_TEMPLATE, players.get(0).getName(), getRound(), players.get(0).getPlayerPoints()));
            client.executeStatement(String.format(INSERT_TEMPLATE, players.get(1).getName(), getRound(), players.get(1).getPlayerPoints()));
            client.executeStatement(String.format(INSERT_TEMPLATE, players.get(2).getName(), getRound(), players.get(2).getPlayerPoints()));
            client.executeStatement(String.format(INSERT_TEMPLATE, players.get(3).getName(), getRound(), players.get(3).getPlayerPoints()));

            ArrayList<HashMap<String,
                    String>> results = client.executeQuery(String.format(SELECT_BYPLAYERANDROUND, players.get(0).getName(), getRound(), players.get(0).getPlayerPoints()));

            for (HashMap<String, String> map : results) {
                System.out.println(map.get("Player") +
                        " hat derzeit: " + map.get("Points") +
                        " Punkte");
            }
        } catch (SQLException ex) {
            System.out.println("Ups! Something went wrong: "
                    + ex.getMessage());
        }
    }

    public static void recordWinnerOfRoundInDB() {
        Player winner = null;
        for (Player player : players) {
            if (player.isWinner()) {
                winner = player;
                break;
            }
        }

        if (winner != null) {
            try {
                SqliteClient client = new SqliteClient("demodatabase.sqlite");
                String updateQuery = String.format("UPDATE Rounds SET Points = %d WHERE Player = '%s' AND Round = %d;", winner.getPlayerPoints(), winner.getName(), getRound());
                client.executeStatement(updateQuery);
                System.out.println("Points for the winner have been updated in the database.");
            } catch (SQLException ex) {
                System.out.println("Oops! Something went wrong: "
                        + ex.getMessage());
            }
        }
    }
}
