package me.lenajenichen.mauvelinkbot.discord.api;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class CreateDescription {

    public static void createDescriptionMessage(String message, String colorcode, TextChannel channel) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(message);
        builder.setColor(Color.decode(colorcode));
        channel.sendMessage(builder.build()).queue();
    }

    public static void createDescriptionImageMessage(String message, String colorcode, TextChannel channel, String url,
                                                     String name) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(message);
        builder.setColor(Color.decode(colorcode));
        builder.setAuthor(name, null, url);
        channel.sendMessage(builder.build()).queue();
    }

    public static void createDescriptionTitleAuthorMessage(String message, String colorcode, TextChannel channel,
                                                           String url, String Title, String name) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(message);
        builder.setColor(Color.decode(colorcode));
        builder.setTitle(Title, url);
        builder.setAuthor(name, null, url);
        channel.sendMessage(builder.build()).queue();
    }

    public static void createDescriptionPrivateMember(Member m, String message, String colorcode, String name, String url) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(message);
        builder.setColor(Color.decode(colorcode));
        builder.setAuthor(name, null, url);
        m.getUser().openPrivateChannel().queue(channel -> {
            channel.sendMessage(builder.build()).queue();
        });
    }

    public static void createDescriptionPrivateUser(User user, String message, String colorcode) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(message);
        builder.setColor(Color.decode(colorcode));
        user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(builder.build()).queue();
        });
    }

    public static void createDescriptionPrivateUserAll(User user, String message, String colorcode, String url, String name) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(message);
        builder.setColor(Color.decode(colorcode));
        builder.setAuthor(name, null, url);
        user.openPrivateChannel().queue(channel -> {
            channel.sendMessage(builder.build()).queue();
        });
    }

}
