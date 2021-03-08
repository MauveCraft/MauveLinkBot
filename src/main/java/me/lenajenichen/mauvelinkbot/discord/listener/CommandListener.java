package me.lenajenichen.mauvelinkbot.discord.listener;

import me.lenajenichen.mauvelinkbot.discord.DiscordBot_Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent e) {
        String message = e.getMessage().getContentDisplay();
        Member member = e.getMessage().getMember();
        if(message.startsWith("/")) {
            String[] args = message.substring(1).split(" ");

            if(args.length == 0 ) {
                if(!DiscordBot_Main.INSTANCE.getManager().perform(args[0], member, e.getChannel(), e.getMessage())) {

                }
            }
        }
    }
}
