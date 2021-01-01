package me.lenajenichen.mauvelinkbot.bungee.commands;

import me.lenajenichen.mauvelinkbot.bungee.api.Code_Generator;
import me.lenajenichen.mauvelinkbot.Main;
import me.lenajenichen.mauvelinkbot.bungee.api.MySQLAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Link_Command extends Command {


    public Link_Command(Main name) {
        super("link");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if((sender instanceof ProxiedPlayer)) {

            Code_Generator code_generator = new Code_Generator();
            String code = code_generator.generate();

            ProxiedPlayer p = (ProxiedPlayer)sender;

            TextComponent tc_suffix = new TextComponent("§7[§9Link§7] Your private code is ");
            tc_suffix.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, code));
            tc_suffix.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Copy Code to Clipboard").create()));
            tc_suffix.addExtra("§a" + code + "§7. §7This Code will expire in 5 Minutes.");
            p.sendMessage(tc_suffix);
        }
    }
}
