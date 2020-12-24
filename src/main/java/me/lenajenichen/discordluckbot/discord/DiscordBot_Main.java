package me.lenajenichen.discordluckbot.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordBot_Main {

    public static DiscordBot_Main INSTANCE;

    private static JDA jda;

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

    @SuppressWarnings("deprecation")
    public DiscordBot_Main() throws LoginException, IllegalAccessException, SecurityException {
        INSTANCE = this;

        new JDABuilder();
        jda = JDABuilder.createDefault("NzkxMzk1MDE4OTgzMzQyMTQw.X-OiCw.gP-0Iwz__GRx4WR9c1wdf8yQ__M").build();

        jda.getPresence().setActivity(Activity.playing("test123"));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
    }

    public static void shutdown() {
        jda.getPresence().setStatus(OnlineStatus.OFFLINE);
        jda.shutdown();
    }

}
