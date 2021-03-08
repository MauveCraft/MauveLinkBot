package me.lenajenichen.mauvelinkbot.discord.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;


public interface CommandManager {

    public void performCommand(Member m, PrivateChannel channel, Message message);

}
