package me.lenajenichen.mauvelinkbot.discord.utils;

import me.lenajenichen.mauvelinkbot.discord.commands.Unlink;
import me.lenajenichen.mauvelinkbot.discord.commands.help;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;

import java.util.concurrent.ConcurrentHashMap;

public class Manager {

    public ConcurrentHashMap<String, CommandManager> commands;

    public Manager() {
        this.commands = new ConcurrentHashMap<>();

        this.commands.put("unlink", new Unlink());
        this.commands.put("help", new help());
    }

    public boolean perform(String command, Member m, PrivateChannel channel, Message message) {

        CommandManager cmd;
        if((cmd = this.commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(m, channel, message);
            return true;
        }
        return false;
    }

}
