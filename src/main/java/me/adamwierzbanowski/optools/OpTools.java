package me.adamwierzbanowski.optools;

import me.adamwierzbanowski.optools.Commands.God;
import me.adamwierzbanowski.optools.Commands.TimeBan;
import me.adamwierzbanowski.optools.Listeners.TimeBanGui;
import org.bukkit.ChatColor;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpTools extends JavaPlugin {

    public static OpTools plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new TimeBanGui(), this);

        getCommand("God").setExecutor(new God());
        getCommand("TimeBan").setExecutor(new TimeBan());
    }

    public String GetMessagePrefix() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.MessagePrefix")) + " ";
    }

    public String GetNoPermissionMessage() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.NoPermissionMessage"));
    }

    public String GetErrorMessage() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.ErrorMessage"));
    }
}
