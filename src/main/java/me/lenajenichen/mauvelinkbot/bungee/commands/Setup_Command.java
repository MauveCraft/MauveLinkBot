package me.lenajenichen.mauvelinkbot.bungee.commands;

import dev.wolveringer.bungeeutil.BungeeUtil;
import dev.wolveringer.bungeeutil.inventory.Inventory;
import dev.wolveringer.bungeeutil.item.ItemBuilder;
import me.lenajenichen.mauvelinkbot.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Setup_Command extends Command {

    public Setup_Command(Main name) {
        super("setup");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer)sender;
            if(p.hasPermission("")) {
                Inventory inv = new Inventory(9, "§6Setup");
                inv.setItem(0, new ItemBuilder().id(1).name("Test-Item").listener((click -> {
                    click.getPlayer().sendMessage("Clicked!");
                })).build());
            }
        }
    }

}
