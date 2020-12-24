package me.lenajenichen.discordluckbot;

import me.lenajenichen.discordluckbot.bungee.Link_Command;
import me.lenajenichen.discordluckbot.bungee.MySQL.MySQL;
import me.lenajenichen.discordluckbot.discord.DiscordBot_Main;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Main extends Plugin {

    public static File mysql_file;
    public static Configuration mysql_cfg;

    @Override
    public void onEnable() {
        MySQL.connect();
        getLogger().info("Plugin on");
        DiscordBot_Main.main();
        registerEvents();
        createMySQLConfig();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
        getLogger().info("Plugin off");
        DiscordBot_Main.shutdown();
    }

    public void registerEvents() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new Link_Command(this));
    }

    public void createMySQLConfig() {

        try {
            mysql_file = new File(getDataFolder().getPath(), "config.yml");
            mysql_cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(mysql_file);
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            if (!mysql_file.exists()) {
                mysql_file.createNewFile();
            }
            mysql_cfg.set("host", "127.0.0.1");
            mysql_cfg.set("port", "3306");
            mysql_cfg.set("database", "discordbot");
            mysql_cfg.set("username", "root");
            mysql_cfg.set("password", "1234");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
