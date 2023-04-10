package me.adamwierzbanowski.optools.Listeners;

import me.adamwierzbanowski.optools.Commands.TimeBan;
import me.adamwierzbanowski.optools.OpTools;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.module.Configuration;

public class TimeBanGui implements Listener {

    @EventHandler
    public void onGuiClick(InventoryClickEvent e) {
        String guiTitle = OpTools.plugin.GetMessagePrefix() + ChatColor.translateAlternateColorCodes('&', OpTools.plugin.getConfig().getString("TimeBanGui.Title"));

        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase(guiTitle)) {
            if (e.getClickedInventory() == e.getView().getTopInventory()) {
                if (e.getCurrentItem() != null) {
                    String lenght = e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0];
                    Player target = TimeBan.guiOpen.get(p);
                    FileConfiguration config = OpTools.plugin.getConfig();
                    config.set("TimeBanGui.Bans." + target.getDisplayName() + ".BannedBy", p.getDisplayName());
                    config.set("TimeBanGui.Bans." + target.getDisplayName() + ".Lenght", lenght);
                    config.set("TimeBanGui.Bans." + target.getDisplayName() + ".Reason", TimeBan.banReasons.get(target));
                    config.set("TimeBanGui.Bans." + target.getDisplayName() + ".BanDate", System.currentTimeMillis());
                    config.set("TimeBanGui.Bans." + target.getDisplayName() + ".IsBanned", "true");
                    config.options().copyDefaults(true);
                    OpTools.plugin.saveConfig();
                    TimeBan.guiOpen.get(p).kickPlayer("You Are Banned from the server\n\nBy: " + p.getDisplayName() + "\n\nFor: " + TimeBan.banReasons.get(target) + "\n\nTime left: " + lenght + " minutes.");
                    TimeBan.banReasons.remove(target);
                    TimeBan.guiOpen.remove(p);
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGuiClose(InventoryCloseEvent e) {
        String guiTitle = OpTools.plugin.GetMessagePrefix() + ChatColor.translateAlternateColorCodes('&', OpTools.plugin.getConfig().getString("TimeBanGui.Title"));

        Player p = (Player) e.getPlayer();
        if(e.getView().getTitle() == guiTitle) {
            if (TimeBan.guiOpen.containsKey(p)) {
                TimeBan.banReasons.remove(TimeBan.guiOpen.get(p));
                TimeBan.guiOpen.remove(p);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (OpTools.plugin.getConfig().getString("TimeBanGui.Bans." + p.getDisplayName()) != null) {
            String path = "TimeBanGui.Bans." + p.getDisplayName();
            if (OpTools.plugin.getConfig().getString(path + ".IsBanned") == "true") {
                if (System.currentTimeMillis() - Double.parseDouble(OpTools.plugin.getConfig().getString(path + ".BanDate")) < Double.parseDouble(OpTools.plugin.getConfig().getString(path + ".Lenght")) * 60000) {
                    float timeLeft = (float) ((Double.parseDouble(OpTools.plugin.getConfig().getString(path + ".Lenght")) * 60000 - (System.currentTimeMillis() - Double.parseDouble(OpTools.plugin.getConfig().getString(path + ".BanDate")))) / 60000);
                    p.kickPlayer("You Are Banned from the server\n\nBy: " + OpTools.plugin.getConfig().getString(path + ".BannedBy") + "\n\nFor: " + OpTools.plugin.getConfig().getString(path + ".Reason") + "\n\nTime left: " + timeLeft + " minutes.");
                } else {
                    FileConfiguration config = OpTools.plugin.getConfig();
                    config.set(path + ".IsBanned", "false");
                    config.options().copyDefaults(true);
                    OpTools.plugin.saveConfig();
                }
            }
        }
    }
}
