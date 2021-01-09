package me.lenajenichen.mauvelinkbot.bungee.MySQL;

import me.lenajenichen.mauvelinkbot.Main;

import java.sql.*;

public class MySQL {

    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String passwort;
    public static Connection message_database;
    public static Connection player_database;
    public static Statement stmt;

    public static void connect()
    {
        if (!isConnected()) {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                //stmt = message_database.createStatement();
                message_database = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC&&useUnicode=true&autoReconnect=true", username, passwort);
                System.out.println("MySQL ist Verbunden!");
            }
            catch (SQLException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect()
    {
        if (isConnected()) {
            try
            {
                message_database.close();
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
        return message_database != null;
    }

    public static void createTable()
    {
        try
        {
            MySQL.connect();
            message_database.prepareStatement("CREATE TABLE IF NOT EXISTS messages(Message_name VARCHAR(32), Message VARCHAR(100), messagelanguage VARCHAR(2));").executeUpdate();
            message_database.prepareStatement("CREATE TABLE IF NOT EXISTS players(playername VARCHAR (16), UUID VARCHAR (36), discord_tag VARCHAR(37), is_linked BOOLEAN, code VARCHAR(16))").executeUpdate();
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
            System.out.println("MySQL Connected!");
            stmt.execute(sqlCommand);
            System.out.println("Der SQL Command wurde erfolgreich ausgefürt!");
            MySQL.disconnect();
        } catch (SQLException e) {
            System.out.println("Der SQL Command konnte nicht ausgeführt werden.");
            e.printStackTrace();
        }
    }

    public static ResultSet queryPurchases(String sqlCommand) {
        try {
            MySQL.connect();
            stmt = message_database.createStatement();
            return stmt.executeQuery(sqlCommand);
        } catch (SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return null;
    }

}
