package me.lenajenichen.mauvelinkbot.bungee.commands;

import me.lenajenichen.mauvelinkbot.bungee.MySQL.MySQL;
import me.lenajenichen.mauvelinkbot.bungee.api.Code_Generator;
import me.lenajenichen.mauvelinkbot.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Link_Command extends Command {

    private Main plugin;

    public static List<String> string_code = new ArrayList<String>();
    public static List<String> command_cooldown = new ArrayList<String>();
    public static List<String> help_cooldown = new ArrayList<String>();
    public Link_Command(Main name, Main plugin) {
        super("link");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if((sender instanceof ProxiedPlayer)) {
            if (args.length == 0) {
                Code_Generator code_generator = new Code_Generator();
                String code = code_generator.generate();
                ProxiedPlayer p = (ProxiedPlayer) sender;
                ResultSet islinked = MySQL.queryMySQL("SELECT is_linked FROM players WHERE playername = '" + p.getDisplayName() + "'");
                try {
                    if (islinked.next()) {
                        boolean is_linked = islinked.getBoolean("is_linked");
                        if (!command_cooldown.contains(p.getDisplayName())) {
                            if (is_linked == false) {
                                TextComponent tc_suffix = new TextComponent("§7[§9Link§7] Your private code is ");
                                tc_suffix.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, code));
                                tc_suffix.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Copy Code to Clipboard").create()));
                                tc_suffix.addExtra("§a" + code + "§7. §7This Code will expire in 5 Minutes.");
                                p.sendMessage(tc_suffix);
                                MySQL.updateQuery("UPDATE players SET code='" + code + "' WHERE playername='" + p.getDisplayName() + "' AND UUID='" + p.getUniqueId().toString().replaceAll("-", "") + "'");
                                string_code.add(code);
                                command_cooldown.add(p.getDisplayName());
                                ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
                                    public void run() {
                                        MySQL.updateQuery("UPDATE players SET code=NULL WHERE playername='" + string_code.get(0) + "'");
                                        string_code.remove(0);
                                    }
                                }, 5, TimeUnit.MINUTES);
                                ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        command_cooldown.remove(0);
                                    }
                                }, 5, TimeUnit.MINUTES);
                            } else {
                                p.sendMessage("§7[§9Link§7] §cYour account is already linked to a Discord account. \n§7[§9Link§7] §7Use §6/unlink §7if you wish to unlink your account.");
                            }
                        } else {
                            p.sendMessage("§7[§9Link§7] §cPlease wait a moment until you can use the command again.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } if (args.length == 1) {
            if(args[0].equals("help")) {
                ProxiedPlayer p = (ProxiedPlayer) sender;
                if (!help_cooldown.contains(p.getDisplayName())) {
                    p.sendMessage("§7=========[§9Link Help§7]=========");
                    p.sendMessage("§a1) §7Use /link to get your verification code.");
                    p.sendMessage("§a2) §7Go on Discord and search for the §6MauveVerify §7bot.");
                    p.sendMessage("§a3) §7Send the your code to the bot and you'll get your rank on Discord.");
                    p.sendMessage("§7=========[§9Link Help§7]=========");
                    help_cooldown.add(p.getDisplayName());
                    ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
                        @Override
                        public void run() {
                            help_cooldown.remove(0);
                        }
                    }, 10, TimeUnit.SECONDS);
                } else {
                    p.sendMessage("§7[§9Link§7] §cPlease wait a moment until you can use the command again.");
                }
            }
        }
    }
}
