package me.lenajenichen.mauvelinkbot.bungee.listener;

import me.lenajenichen.mauvelinkbot.Main;
import me.lenajenichen.mauvelinkbot.bungee.MySQL.MySQL;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinListener implements Listener {

    @EventHandler
    public void onPostLoginEvent(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        ResultSet getUser = MySQL.queryMySQL("SELECT playername FROM players WHERE playername = '" + p.getDisplayName() + "'");
        try {
            if (getUser.next()) {
                MySQL.updateQuery("UPDATE players SET playername='" + p.getDisplayName() +"' WHERE UUID='" + p.getUniqueId().toString().replaceAll("-", "") + "'");
            } else {
                MySQL.updateQuery("INSERT INTO players(playername, UUID, discord_tag, is_linked, code, rank) VALUES('" + p.getDisplayName() + "', '" + p.getUniqueId().toString().replaceAll("-", "") + "', '" + " " + "', " + false + ", '" + " " + "', '" + Main.api.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup() + "')");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
