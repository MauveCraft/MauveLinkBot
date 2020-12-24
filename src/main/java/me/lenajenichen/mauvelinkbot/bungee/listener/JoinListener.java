package me.lenajenichen.mauvelinkbot.bungee.listener;

import me.lenajenichen.mauvelinkbot.bungee.MySQL.MySQL;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

    @EventHandler
    public void postLoginEvent(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if(MySQL.mysql_connected == false) {
            p.sendMessage("");
            p.sendMessage("");
            p.sendMessage("");
        }
    }

}
