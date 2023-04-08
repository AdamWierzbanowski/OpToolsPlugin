package me.adamwierzbanowski.optools;

import me.adamwierzbanowski.optools.Commands.God;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpTools extends JavaPlugin {

    public static OpTools plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("God").setExecutor(new God());
    }
}
