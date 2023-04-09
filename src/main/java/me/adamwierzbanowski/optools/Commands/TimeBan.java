package me.adamwierzbanowski.optools.Commands;

import me.adamwierzbanowski.optools.OpTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class TimeBan implements CommandExecutor {

    public static HashMap<String, Double> banList = new HashMap<>();
    public static HashMap<String, String> reasonList = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String messagePrefix = OpTools.plugin.GetMessagePrefix();
        String noPermissionMessage = OpTools.plugin.GetNoPermissionMessage();
        String errorMessage = OpTools.plugin.GetErrorMessage();
        List<Integer> timeBanLenghts = OpTools.plugin.getConfig().getIntegerList("timeBanLenghts");

        if (sender instanceof Player p) {
            if (p.hasPermission("optools.timeban")) {
                if (args.length == 1) {
                    Inventory gui = GetGui(p, timeBanLenghts, messagePrefix);
                    p.openInventory(gui);
                }
            } else {
                p.sendMessage(noPermissionMessage);
            }
        } else {
            System.out.println(messagePrefix + errorMessage);
        }
        return true;
    }

    public Inventory GetGui(Player p, List<Integer> timeBanLenghts, String messagePrefix) {
        if (timeBanLenghts.size() == 0) {
            return null;
        }

        Inventory gui = Bukkit.createInventory(p, (int) Math.ceil(timeBanLenghts.size() / 9) * 9,  messagePrefix + ChatColor.YELLOW + "Time" + ChatColor.GREEN + "Ban");

        for (int i = 0; i < timeBanLenghts.size(); i++) {
            ItemStack banItem = new ItemStack(Material.RED_CONCRETE, Math.min(timeBanLenghts.get(i), 64));
            ItemMeta itemMeta = banItem.getItemMeta();
            itemMeta.setDisplayName(timeBanLenghts.get(i) + "" + ChatColor.RED + " Minutes." );

            banItem.setItemMeta(itemMeta);
            gui.setItem(i, banItem);
        }

        return gui;
    }
}
