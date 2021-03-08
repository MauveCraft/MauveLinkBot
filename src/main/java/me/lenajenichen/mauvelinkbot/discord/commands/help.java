package me.lenajenichen.mauvelinkbot.discord.commands;

import me.lenajenichen.mauvelinkbot.discord.api.CreateDescription;
import me.lenajenichen.mauvelinkbot.discord.utils.CommandManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;

public class help implements CommandManager {
    @Override
    public void performCommand(Member m, PrivateChannel channel, Message message) {

        String[] args = message.getContentDisplay().split(" ");

        if(args.length == 0) {
            CreateDescription.createDescriptionPrivateMember(m, "Commands: \n`/unlink` \n`/help`", "#00FFFF", "Help", "https://cdn.discordapp.com/avatars/791395018983342140/c14cf822d9a8ac9ceebc1c91e6fdf6a5.png");
        } else {
            CreateDescription.createDescriptionPrivateMember(m, "Command not found type /help to get more information.", "#e1343f", "Error", "https://cdn.discordapp.com/avatars/791395018983342140/c14cf822d9a8ac9ceebc1c91e6fdf6a5.png");
        }

    }
}
