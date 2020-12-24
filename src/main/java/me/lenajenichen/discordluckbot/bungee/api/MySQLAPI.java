package me.lenajenichen.discordluckbot.bungee.api;

import me.lenajenichen.discordluckbot.bungee.MySQL.MySQL;
import net.md_5.bungee.api.ChatColor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLAPI {

    public static String getMessage(String Message_name, String language) {
        try {
            MySQL.connect();
            PreparedStatement st = MySQL.message_database.prepareStatement("SELECT * FROM messages WHERE `Message_name` LIKE " + "'" +  Message_name + "'"  +" AND `language` LIKE "  + "'" + language + "'");
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return rs.getString("Message");
            }
        } catch(SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return "[Error] Message " + "'" + Message_name + "'" + " not found! \n[Error] Please contact Admin.";
    }

    public static String getMessageTranslateColorCodes(String Message_name, String language) {
        try {
            MySQL.connect();
            PreparedStatement st = MySQL.message_database.prepareStatement("SELECT * FROM messages WHERE `Message_name` LIKE " + "'" +  Message_name + "'"  +" AND `language` LIKE "  + "'" + language + "'");
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return rs.getString(ChatColor.translateAlternateColorCodes('&', "Message"));
            }
        } catch(SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return "[Error] Message " + "'" + Message_name + "'" + " not found! \n[Error] Please contact Admin.";
    }

    public static String getMessageName(String Message_Name) {
        try {
            MySQL.connect();
            PreparedStatement st = MySQL.message_database.prepareStatement("SELECT * FROM `messages` WHERE `Message_name` = " + "'" + Message_Name + "'");
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return rs.getString("Message_Name");
            }
        } catch (SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return "[Error] Could not find " + Message_Name + " in Database";
    }

    public static String multiMessageWithoutColor(String Message_name, String language, String stringToChange, String newMessage) {
        String message = getMessage(Message_name, language).replace(stringToChange, newMessage);

        return message;
    }

    public static String multiMessageWithColor(String Message_name, String language, String stringToChange, String newMessage) {
        String message = getMessageTranslateColorCodes(Message_name, language).replace(stringToChange, newMessage);

        return message;
    }

    public static Integer getID(String Name) {
        try {
            MySQL.connect();
            PreparedStatement st = MySQL.player_database.prepareStatement("SELECT * FROM `players` WHERE `Player_Name` LIKE " + "'" + Name + "'");
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return Integer.valueOf("[Error] Could not find an ID you were looking for.");
    }

    public static String getName(int ID) {
        try {
            MySQL.connect();
            PreparedStatement st = MySQL.player_database.prepareStatement("SELECT * FROM `players` WHERE `ID` = " + "'" + ID + "'");
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return rs.getString("Player_Name");
            }
        } catch (SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return "[Error] Could not find the Name you were looking for.";
    }

    public static String getUUIDByName(String Name) {
        try {
            MySQL.connect();
            PreparedStatement st = MySQL.player_database.prepareStatement("SELECT * FROM `players` WHERE `Player_Name` LIKE " + "'" + Name + "'");
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return rs.getString("UUID");
            }
        } catch (SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return "[Error] Could not find the UUID you were looking for.";
    }

    public static String getLanguageByName(String Name) {
        try {
            MySQL.connect();
            PreparedStatement st = MySQL.player_database.prepareStatement("SELECT * FROM `players` WHERE `Player_Name` LIKE " + "'" + Name + "'");
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return rs.getString("Language");
            }
        } catch (SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return "[Error] Could not find the Player you were looking for.";
    }

    public static String getLanguageByUUID(String UUID) {
        try {
            MySQL.connect();
            PreparedStatement st = MySQL.player_database.prepareStatement("SELECT * FROM `players` WHERE `UUID` LIKE " + "'" + UUID + "'");
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return rs.getString("Language");
            }
        } catch (SQLException e) {
            MySQL.disconnect();
            e.printStackTrace();
        }
        return "[Error] Could not find the Player you were looking for.";
    }

}
