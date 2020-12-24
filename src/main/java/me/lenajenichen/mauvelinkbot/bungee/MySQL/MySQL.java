package me.lenajenichen.mauvelinkbot.bungee.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    public static String host;
    public static String host2;
    public static String port;
    public static String port2;
    public static String database;
    public static String database2;
    public static String username;
    public static String username2;
    public static String passwort;
    public static String passwort2;
    public static Connection message_database;
    public static Connection player_database;

    public static void connect()
    {
        if (!isConnected()) {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                message_database = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC&&useUnicode=true&autoReconnect=true", username, passwort);
                player_database = DriverManager.getConnection("jdbc:mysql://" + host2 + ":" + port2 + "/" + database2 + "?useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC&&useUnicode=true&autoReconnect=true", username2, passwort2);
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
        return message_database != null;
    }

    public static void createTable()
    {
        try
        {
            message_database.prepareStatement("CREATE TABLE IF NOT EXISTS messages(Message_name VARCHAR(32), Message VARCHAR(100), messagelanguage VARCHAR(2));").executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void createTable2() {
        try {
            player_database.prepareStatement("CREATE TABLE IF NOT EXISTS players(ID INT NOT NULL PRIMARY KEY AUTOINCREMENT, playername VARCHAR(16), UUID VARCHAR(36), playerlanguage VARCHAR(2), rank VARCHAR(32));").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
