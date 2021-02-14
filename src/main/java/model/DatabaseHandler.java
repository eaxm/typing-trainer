package model;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides access to the database
 */
public class DatabaseHandler {

    private static final String URL = "jdbc:sqlite:Progress.db";
    private static final String TABLE_SQL = "CREATE TABLE IF NOT EXISTS game (" +
            "id INTEGER PRIMARY KEY," +
            "wpm INTEGER NOT NULL," +
            "unixTime INTEGER" +
            ")";

    public DatabaseHandler() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement();
        stmt.execute(TABLE_SQL);

        conn.close();
    }

    public void saveProgress(int wpm, long unixTime) throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        String progressInsertSql = "INSERT INTO game (wpm, unixTime) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(progressInsertSql);
        ps.setInt(1, wpm);
        ps.setLong(2, unixTime);
        ps.executeUpdate();

        conn.close();
    }

    public List<GameEntry> getProgress() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        String progressQuerySql = "SELECT unixTime, wpm FROM game";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(progressQuerySql);

        List<GameEntry> gameEntries = new LinkedList<>();

        while(rs.next()){
            long unixTime = rs.getLong("unixTime");
            int wpm = rs.getInt("wpm");

            gameEntries.add(new GameEntry(new Date(unixTime), wpm));
        }

        return gameEntries;
    }
}
