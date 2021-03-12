package me.lenajenichen.mauvelinkbot.discord.utils;

import me.lenajenichen.mauvelinkbot.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.util.Tristate;
import net.md_5.bungee.api.plugin.Listener;

public class GiveDiscordRank implements Listener {
    private final Main plugin;

    public GiveDiscordRank(Main plugin, LuckPerms luckPerms) {
        this.plugin = plugin;

        EventBus eventBus = luckPerms.getEventBus();

        eventBus.subscribe(this.plugin, UserDataRecalculateEvent.class, this::onUserDataRecalculateEvent);
    }

    private void onUserDataRecalculateEvent(UserDataRecalculateEvent e) {
        User user = e.getUser();
        CachedPermissionData permissionData = user.getCachedData().getPermissionData();
        CachedMetaData metaData = user.getCachedData().getMetaData();
        Tristate checkResult = permissionData.checkPermission("test123");
        System.out.println(checkResult.asBoolean());
    }

}
