package model;

import java.sql.*;

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
}
