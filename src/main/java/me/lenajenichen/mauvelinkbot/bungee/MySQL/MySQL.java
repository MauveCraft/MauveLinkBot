package me.lenajenichen.mauvelinkbot.bungee.MySQL;

import java.sql.*;

public class MySQL {

    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String passwort;
    public static Connection player_database;
    public static Statement stmt;

    public static void connect()
    {
        player_database = null;
        if (!isConnected()) {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                player_database = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC&&useUnicode=true&autoReconnect=true", username, passwort);
                stmt = player_database.createStatement();
                System.out.println("MySQL ist Verbunden!");
            }
            catch (SQLException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        } else {
            System.out.println("Es besteht bereits eine Verbindung.");
        }
    }

    public static void disconnect()
    {
        if (isConnected()) {
            try
            {
                player_database.close();
                System.out.println("MySQL Verbindung getrennt!");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected()
    {
        return player_database != null;
    }

    public static void createTable()
    {
        try
        {
            MySQL.connect();
            player_database.prepareStatement("CREATE TABLE IF NOT EXISTS players(playername VARCHAR (16), UUID VARCHAR (36) NOT NULL, discord_tag VARCHAR(37), discord_id VARCHAR(19), is_linked BOOLEAN, code VARCHAR(16), rank VARCHAR(64))").executeUpdate();
            MySQL.disconnect();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void updateQuery(String sqlCommand) {
        try {
            MySQL.connect();
            stmt.executeUpdate(sqlCommand);
            System.out.println("Der SQL Command wurde erfolgreich ausgefürt!");
            MySQL.disconnect();
        } catch (SQLException e) {
            System.out.println("Der SQL Command konnte nicht ausgeführt werden.");
            e.printStackTrace();
            MySQL.disconnect();
        }
    }

    public static ResultSet queryMySQL(String sqlCommand) {
        try {
            MySQL.connect();
            //stmt = message_database.createStatement();
            return stmt.executeQuery(sqlCommand);
        } catch (SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return null;
    }

}
