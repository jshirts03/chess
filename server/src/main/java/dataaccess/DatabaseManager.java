package dataaccess;

import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static String databaseName;
    private static String dbUsername;
    private static String dbPassword;
    private static String connectionUrl;

    /*
     * Load the database information for the db.properties file.
     */
    static {
        loadPropertiesFromResources();
    }

    /**
     * Creates the database if it does not already exist.
     */
    static public void createDatabase() throws DataAccessException {
        var statement = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        executeStatement(statement);
    }


    public static void executeStatement(String statement) throws DataAccessException {
        try (var conn = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("something went wrong with DB", ex);
        }
    }

    // Create tables if not exist
    // Once service boots up, create a DB manager object, create database if not exists (first)
    // create table as part of constructor as well, but that's a method in this class, with table name as parameter
    // THIS IS IN THE CONSTRUCTOR OF DAO's ^^^
    //
    // create table method
    //Auth Table
    // Id, UserId (linked), AuthToken

    //User Table
    // Id, Username, email, password(hashed)

    //Game Table
    // Id, GameData (JSON string)

    public static void createTables() throws DataAccessException {
        String createUserTableStatement = """
                CREATE TABLE IF NOT EXISTS chess.users(
                id INT NOT NULL AUTO_INCREMENT,
                username VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                PRIMARY KEY (id));
                """;
        String createAuthTableStatement = """
                CREATE TABLE IF NOT EXISTS chess.auth(
                id INT NOT NULL AUTO_INCREMENT,
                username VARCHAR(255) NOT NULL,
                authtoken VARCHAR(255) NOT NULL,
                PRIMARY KEY (id));
                """;
        String createGameTableStatement = """
                CREATE TABLE IF NOT EXISTS chess.games(
                id INT NOT NULL AUTO_INCREMENT,
                gameid INT NOT NULL,
                whiteusername VARCHAR(255),
                blackusername VARCHAR(255),
                gamename VARCHAR(255) NOT NULL,
                game VARCHAR(255) NOT NULL,
                PRIMARY KEY (id));
                """;
        executeStatement(createUserTableStatement);
        executeStatement(createAuthTableStatement);
        executeStatement(createGameTableStatement);
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DatabaseManager.getConnection()) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            //do not wrap the following line with a try-with-resources
            var conn = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
            conn.setCatalog(databaseName);
            return conn;
        } catch (SQLException ex) {
            throw new DataAccessException("failed to get connection", ex);
        }
    }

    private static void loadPropertiesFromResources() {
        try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            if (propStream == null) {
                throw new Exception("Unable to load db.properties");
            }
            Properties props = new Properties();
            props.load(propStream);
            loadProperties(props);
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties", ex);
        }
    }

    private static void loadProperties(Properties props) {
        databaseName = props.getProperty("db.name");
        dbUsername = props.getProperty("db.user");
        dbPassword = props.getProperty("db.password");

        var host = props.getProperty("db.host");
        var port = Integer.parseInt(props.getProperty("db.port"));
        connectionUrl = String.format("jdbc:mysql://%s:%d", host, port);
    }

}
