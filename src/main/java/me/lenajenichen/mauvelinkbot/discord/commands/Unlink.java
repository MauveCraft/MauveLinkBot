package me.lenajenichen.mauvelinkbot.discord.commands;

import me.lenajenichen.mauvelinkbot.bungee.MySQL.MySQL;
import me.lenajenichen.mauvelinkbot.discord.DiscordBot_Main;
import me.lenajenichen.mauvelinkbot.discord.api.CreateDescription;
import me.lenajenichen.mauvelinkbot.discord.utils.CommandManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Unlink implements CommandManager {


    @Override
    public void performCommand(Member m, PrivateChannel channel, Message message) {

        String[] args = message.getContentDisplay().split(" ");
        if (args.length == 0) {
            ResultSet getlinked = MySQL.queryMySQL("SELECT is_linked FROM players WHERE discord_tag = '" + m.getUser().getAsTag() + "'");
            ResultSet getid = MySQL.queryMySQL("SELECT discord_id FROM players WHERE discord_tag = '" + m.getUser().getAsTag() + "'");
            try {
                if (getlinked.next()) {
                    if (getid.next()) {
                        String id = getid.getString("discord_id");
                        ResultSet getDiscordName = MySQL.queryMySQL("SELECT discord_tag FROM players WHERE discord_id = '" + id + "'");
                        String discord_tag = getDiscordName.getString("discord_tag");
                        boolean islinked = getlinked.getBoolean("is_linked");
                        if (getDiscordName.next()) {
                            if (islinked == true) {
                                //User user = getUser(id);
                                //System.out.println(user);
                                DiscordBot_Main.removeRole(discord_tag, id);
                                //CreateDescription.createDescriptionPrivateUser(user, "Your account was sucsessfully unlinked! \nTo link it again go into Minecraft and type '/link'.", "#e1343f");
                                CreateDescription.createDescriptionPrivateMember(m, "Your account was sucsessfully unlinked! \nTo link it again go into Minecraft and type '/link'.", "#00FF00", "Unlink Succsessfull!" ,"https://cdn.discordapp.com/avatars/791395018983342140/c14cf822d9a8ac9ceebc1c91e6fdf6a5.png");
                                MySQL.updateQuery("UPDATE players SET is_linked=0 WHERE discord_tag = '" + m.getUser().getAsTag() + "'");
                                MySQL.updateQuery("UPDATE players SET discord_id = '" + null + "' WHERE discord_tag = '" + m.getUser().getAsTag() + "'");
                                MySQL.updateQuery("UPDATE players SET discord_tag ='" + null + "' WHERE discord_tag ='" + m.getUser().getAsTag() + "'");
                            } else {
                                CreateDescription.createDescriptionPrivateMember(m, "Your account is currently not linked to a discord account. \nPlease type /link in Minecraft to link your account.", "#e1343f", "Error", "https://cdn.discordapp.com/avatars/791395018983342140/c14cf822d9a8ac9ceebc1c91e6fdf6a5.png");
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            CreateDescription.createDescriptionPrivateMember(m, "Command not found type /help to get more information.", "#e1343f", "Error", "https://cdn.discordapp.com/avatars/791395018983342140/c14cf822d9a8ac9ceebc1c91e6fdf6a5.png");
        }
    }
}
