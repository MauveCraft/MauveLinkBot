package me.lenajenichen.mauvelinkbot.discord;

import me.lenajenichen.mauvelinkbot.bungee.commands.Unlink_Command;
import me.lenajenichen.mauvelinkbot.bungee.listener.RankUpdate;
import me.lenajenichen.mauvelinkbot.discord.events.CodeReceivedEvent;
import me.lenajenichen.mauvelinkbot.discord.utils.CommandManager;
import me.lenajenichen.mauvelinkbot.discord.utils.Manager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;

import javax.security.auth.login.LoginException;

public class DiscordBot_Main {

    public static Manager manager;

    public static DiscordBot_Main INSTANCE;
    public static JDA jda;

    public static void main() {
        try {
            new DiscordBot_Main();
        } catch (LoginException le) {
            le.printStackTrace();
        } catch (IllegalAccessException li) {
            li.printStackTrace();
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }

    public DiscordBot_Main() throws LoginException, IllegalAccessException, SecurityException {
        INSTANCE = this;

        new JDABuilder();
        jda = JDABuilder.createDefault("NzkxMzk1MDE4OTgzMzQyMTQw.X-OiCw.ojlfxeT5iuuNYg1OGHhVunr4wD0").build();

        manager = new Manager();

        jda.addEventListener(new CodeReceivedEvent());
        //jda.addEventListener(new Manager());
        jda.getPresence().setActivity(Activity.playing("test123"));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
    }

    public static void shutdown() {
        jda.getPresence().setStatus(OnlineStatus.OFFLINE);
        jda.shutdown();
    }

    public static void removeRole(String user, String id) {
        System.out.println(user);
        System.out.println(jda);
        jda.getGuildById("791273863530020894").removeRoleFromMember(id, jda.getRoleById(RankUpdate.getCurrentRank(user))).complete();
    }

    public Manager getManager() {
        return manager;
    }

}
