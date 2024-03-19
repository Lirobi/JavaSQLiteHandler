package fr.lilianbischung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DataBaseHandler
 * Handles database connections and queries.
 * Provides methods to open and close connections,
 * execute queries, and process results.
 *
 * @author Lilian Bischung
 * @version 1.0.0
 *
 *
 * @requirements org.sqlite.JDBC
 * @requirements org.slf4j
 *
 */

@SuppressWarnings("all")

public class DataBaseHandler {

    private final String url;
    private Connection conn = null;

    // ----------------------------------------------------
    // CONSTRUCTOR
    /**
     * DataBaseHandler constructor
     *
     * @param filename loads the database from the specified filename
     *
     * @since 1.0.0
     * @author Lilian Bischung
     *
     * @see java.sql.Connection
     */
    @SuppressWarnings("all")

    public DataBaseHandler(String filename) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.url = "jdbc:sqlite:" + filename;
        try {
            this.conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------

    // CLOSE CONNECTION

    /**
     * closeConnection closes the connection to the database
     *
     * @return boolean true if the connection was closed, false otherwise
     *
     * @since 1.0.0
     * @author Lilian Bischung
     *
     */
    @SuppressWarnings("all")

    public boolean closeConnection() {
        try {
            this.conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----------------------------------------------------

    // COMMANDS SENDERS

    /**
     * sendCommand
     *
     * @param sql the SQL command to send
     *
     * @return void
     *
     * @since 1.0.0
     * @author Lilian Bischung
     *
     *
     * @see java.sql.Statement
     *
     */
    @SuppressWarnings("all")

    public void sendCommand(String sql) {
        try {
            this.conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * sendCommandWithResultSet
     *
     * @param sql
     *
     * @return ResultSet the result of the SQL command
     *
     * @throws SQLException
     *
     * @since 1.0.0
     * @author Lilian Bischung
     *
     *
     * @see java.sql.Statement
     */
    @SuppressWarnings("all")

    public ResultSet sendCommandWithResultSet(String sql) throws SQLException {
        return conn.createStatement().executeQuery(sql);
    }
    // ----------------------------------------------------

    // CREATORS

    /**
     * createSimpleTable
     *
     *
     *
     * @param tableName the name of the table to create, String
     * @param columns   "<name>, <type>, [options]", Example : new String[] {"id
     *                  INTEGER PRIMARY KEY", "name TEXT", "capacity INTEGER"}
     *
     * @return void
     *
     * @since 1.0.0
     * @author Lilian Bischung
     *
     *
     * @see fr.lilianbischung.DataBaseHandler#sendCommand(String sql)
     */
    @SuppressWarnings("all")
    public void createSimpleTable(String tableName, String[] columns) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
        for (int i = 0; i < columns.length; i++) {
            sql += columns[i];
            if (i < columns.length - 1) {
                sql += ", ";
            }
        }
        sendCommand(sql + ");");
    }

    // ----------------------------------------------------

    // SELECTORS

    /**
     * simpleSelect select from a table
     *
     * @param tableName  the name of the table to select from
     * @param columns    the columns to select, Example : new String[] { "id",
     *                   "name", "capacity" }
     * @param conditions the conditions to select from the table, Example : "name =
     *                   'John'")
     *
     * @return ResultSet the result of the SQL command
     *
     * @throws SQLException
     * @since 1.0.0
     * @author Lilian Bischung
     *
     *
     * @see fr.lilianbischung.DataBaseHandler#sendCommand(String sql)
     *
     */
    @SuppressWarnings("all")

    public ResultSet simpleSelect(String tableName, String[] columns, String[] conditions) throws SQLException {
        String sql = "SELECT ";
        for (int i = 0; i < columns.length; i++) {
            sql += columns[i];
            if (i < columns.length - 1) {
                sql += ", ";
            }
        }
        sql += " FROM " + tableName;
        if (conditions != null) {
            sql += " WHERE ";
            for (int i = 0; i < conditions.length; i++) {
                sql += conditions[i] + " ";
                if (i != conditions.length - 1) {
                    sql += "AND ";
                }
            }
        }
        return sendCommandWithResultSet(sql + ";");
    }

    // ----------------------------------------------------

    // GETTERS
    /**
     * getDatabaseUrl
     *
     *
     * @return String the database url
     *
     * @since 1.0.0
     * @author Lilian Bischung
     *
     */
    public String getDatabaseUrl() {
        return this.url;
    }

    /**
     * getConnection
     *
     * @return Connection the database connection
     *
     * @since 1.0.0
     * @author Lilian Bischung
     *
     */

    public Connection getConnection() {
        return this.conn;
    }
    // ----------------------------------------------------
}