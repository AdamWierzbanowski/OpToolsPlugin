package me.adamwierzbanowski.optools;

import me.adamwierzbanowski.optools.Commands.God;
import me.adamwierzbanowski.optools.Commands.TimeBan;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpTools extends JavaPlugin {

    public static OpTools plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("God").setExecutor(new God());
        getCommand("TimeBan").setExecutor(new TimeBan());
    }

    public String GetMessagePrefix() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.messagePrefix")) + " ";
    }

    public String GetNoPermissionMessage() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.noPermissionMessage"));
    }
}
