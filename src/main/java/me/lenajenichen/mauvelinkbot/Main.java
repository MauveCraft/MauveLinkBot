package me.lenajenichen.mauvelinkbot;

import me.lenajenichen.mauvelinkbot.bungee.api.MySQLAPI;
import me.lenajenichen.mauvelinkbot.bungee.commands.Link_Command;
import me.lenajenichen.mauvelinkbot.bungee.MySQL.MySQL;
import me.lenajenichen.mauvelinkbot.bungee.listener.JoinListener;
import me.lenajenichen.mauvelinkbot.discord.DiscordBot_Main;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main extends Plugin {

    public static File mysql_file;
    public static Configuration mysql_cfg;

    @Override
    public void onEnable() {
        getLogger().info("Plugin on");
        createMySQLConfig();
        readMySQLData();
        MySQL.createTable();
        DiscordBot_Main.main();
        registerEvents();
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
        pm.registerListener(this, new JoinListener());
    }

    public static Main getPlugin(Class<Main> mainClass) {
        return Main.getPlugin(Main.class);
    }

    public void createMySQLConfig() {
        if(!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();

            mysql_file = new File(this.getDataFolder(), "mysql.yml");

            try {
                if(!mysql_file.exists()) {
                    Files.copy(this.getResourceAsStream("mysql.yml"), mysql_file.toPath());
                }
                mysql_cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(mysql_file);
                mysql_cfg.set("host", "127.0.0.1");
                mysql_cfg.set("port", "3306");
                mysql_cfg.set("database", "discordbot");
                mysql_cfg.set("username", "root");
                mysql_cfg.set("password", "1234");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(mysql_cfg, mysql_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readMySQLData() {
        try {
            Configuration mysql_cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "mysql.yml"));
            MySQL.host = mysql_cfg.getString("host");
            MySQL.port = mysql_cfg.getString("port");
            MySQL.database = mysql_cfg.getString("database");
            MySQL.username = mysql_cfg.getString("username");
            MySQL.passwort = mysql_cfg.getString("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
