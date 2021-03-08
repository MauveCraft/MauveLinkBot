package me.lenajenichen.mauvelinkbot.discord.events;

import me.lenajenichen.mauvelinkbot.bungee.MySQL.MySQL;
import me.lenajenichen.mauvelinkbot.bungee.listener.RankUpdate;
import me.lenajenichen.mauvelinkbot.discord.api.CreateDescription;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CodeReceivedEvent extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent e) {
        User user = e.getMessage().getPrivateChannel().getUser();
        ResultSet islinked = MySQL.queryMySQL("SELECT is_linked FROM players WHERE code = '" + e.getMessage().getContentRaw() + "' AND is_linked = 0");
        //ResultSet rank = MySQL.queryMySQL("SELECT rank FROM players WHERE discord_tag = '" + user.getAsTag() + "'");
        if(!e.getMessage().getPrivateChannel().getUser().isBot()) {
            if(!e.getMessage().getPrivateChannel().getUser().equals("M:MauveVerify#3926")) {
                try {
                    if (islinked.next()) {
                        boolean is_linked = islinked.getBoolean("is_linked");
                        //String current_rank = rank.getString("rank");
                        if (is_linked == false) {
                            MySQL.updateQuery("UPDATE players SET discord_tag = '" + user.getAsTag() + "' WHERE code = '" + e.getMessage().getContentRaw() + "'");
                            MySQL.updateQuery("UPDATE players SET is_linked=1 WHERE discord_tag = '" + user.getAsTag() + "'");
                            MySQL.updateQuery("UPDATE players SET discord_id = '" + user.getId() + "' WHERE discord_tag = '" + user.getAsTag() + "'");
                            ResultSet userid = MySQL.queryMySQL("SELECT discord_id FROM players WHERE discord_tag = '" + user.getAsTag() + "'");
                            try {
                                if (userid.next()) {
                                    String id = userid.getString("discord_id");
                                    e.getJDA().getGuildById("791273863530020894").addRoleToMember(id, e.getJDA().getRoleById(RankUpdate.getCurrentRank(user.getAsTag()))).complete();
                                    CreateDescription.createDescriptionPrivateUserAll(user, "Your account was linked successfully! \n Your rank will be updated soon on discord.", "#00FF00", "https://cdn.discordapp.com/avatars/791395018983342140/c14cf822d9a8ac9ceebc1c91e6fdf6a5.png", "Link sucsessfull!");
                                } else {

                                }
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                        } else {
                            CreateDescription.createDescriptionPrivateUser(user, "Your account is already linked! Use /unlink if you wish to unlink your account.", "#e1343f");
                        }
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            } else {
                CreateDescription.createDescriptionPrivateUser(user, "Error 404", "#e1343f");
            }
        } else {
            CreateDescription.createDescriptionPrivateUser(user, "Error 404", "#e1343f");
        }
    }

}
