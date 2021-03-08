package me.lenajenichen.mauvelinkbot.bungee.commands;

import me.lenajenichen.mauvelinkbot.Main;
import me.lenajenichen.mauvelinkbot.bungee.MySQL.MySQL;
import me.lenajenichen.mauvelinkbot.bungee.listener.RankUpdate;
import me.lenajenichen.mauvelinkbot.discord.DiscordBot_Main;
import me.lenajenichen.mauvelinkbot.discord.api.CreateDescription;
import me.lenajenichen.mauvelinkbot.discord.events.CodeReceivedEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Unlink_Command extends Command {

    private Main plugin;

    public static List<String> command_cooldown = new ArrayList<String>();

    public Unlink_Command(Main name, Main plugin) {
        super("unlink");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (!command_cooldown.contains(p.getDisplayName())) {
                if (args.length == 0) {
                    ResultSet getlinked = MySQL.queryMySQL("SELECT is_linked FROM players WHERE playername= '" + p.getDisplayName() + "'");
                    ResultSet getid = MySQL.queryMySQL("SELECT discord_id FROM players WHERE playername = '" + p.getDisplayName() + "'");
                    ResultSet getDiscordName = MySQL.queryMySQL("SELECT discord_tag FROM players WHERE playername = '" + p.getDisplayName() + "'");
                    try {
                        if (getlinked.next()) {
                            if (getid.next()) {
                                if(getDiscordName.next()) {
                                    String id = getid.getString("discord_id");
                                    String discord_tag = getDiscordName.getString("discord_tag");
                                    boolean islinked = getlinked.getBoolean("is_linked");
                                    if (islinked == true) {
                                        //User user = getUser(id);
                                        //System.out.println(user);
                                        DiscordBot_Main.removeRole(discord_tag, id);
                                        //CreateDescription.createDescriptionPrivateUser(user, "Your account was sucsessfully unlinked! \nTo link it again go into Minecraft and type '/link'.", "#e1343f");
                                        p.sendMessage("§7[§9Link§7] §aYour account is now successfully unlinked! \n§7[§9Link§7] If you wish to link your account again type '/link'.");
                                        MySQL.updateQuery("UPDATE players SET discord_tag ='" + null + "' WHERE playername ='" + p.getDisplayName() + "'");
                                        MySQL.updateQuery("UPDATE players SET is_linked=0 WHERE playername = '" + p.getDisplayName() + "'");
                                        MySQL.updateQuery("UPDATE players SET discord_id = '" + null + "' WHERE playername = '" + p.getDisplayName() + "'");
                                    } else {
                                        p.sendMessage("§7[§9Link§7] Your account is currently not linked to an discord account. \n§7[§9Link§7] Please type /link to link your account.");
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    p.sendMessage("§7[§9Link§7] Your account is §calready linked§7.");
                }
            } else {
                p.sendMessage("§7[§9Link§7] §cPlease wait a moment until you can use the command again.");
            }
        }
    }
}
