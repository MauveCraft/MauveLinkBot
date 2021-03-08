package me.lenajenichen.mauvelinkbot.bungee.listener;

import me.lenajenichen.mauvelinkbot.bungee.MySQL.MySQL;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankUpdate {

    /*
    Ranks:
        Owner: 762400988296118292
        Admin: 726757683998359603

        Mod: 756695330430517348
        Dev: 763485694982619217
        Builder: 762399402551672886
        Zeronia: 787414435857039370

        Staff: 768002675685982228

        Visitor: 800719559727644672
        Member: 783384968994226206
        Committed: 783448546966765578
     */

    public static String getCurrentRank(String user) {
        ResultSet rank = MySQL.queryMySQL("SELECT rank FROM players WHERE discord_tag = '" + user + "' AND is_linked=1");
        try {
            if(rank.next()) {
                String current_rank = rank.getString("rank");
                switch (current_rank) {
                    case "default":
                        return "817078929294622741";
                    case "Member":
                        return "783384968994226206";
                    case "Comitted":
                        return "783448546966765578";
                    case "Zeronia":
                        return "787414435857039370";
                    case "Builder":
                        return "762399402551672886";
                    case "Dev":
                        return "763485694982619217";
                    case "Mod":
                        return "756695330430517348";
                    case "Admin":
                        return "726757683998359603";
                    case "Owner":
                        return "762400988296118292";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
