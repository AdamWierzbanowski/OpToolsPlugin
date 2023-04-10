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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class TimeBan implements CommandExecutor {

    public static HashMap<Player, Player> guiOpen = new HashMap<>();
    public static HashMap<Player, String> banReasons = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String messagePrefix = OpTools.plugin.GetMessagePrefix();
        String noPermissionMessage = OpTools.plugin.GetNoPermissionMessage();
        String errorMessage = OpTools.plugin.GetErrorMessage();
        String guiTitle = ChatColor.translateAlternateColorCodes('&', OpTools.plugin.getConfig().getString("TimeBanGui.Title"));
        List<Integer> timeBanLenghts = OpTools.plugin.getConfig().getIntegerList("TimeBanGui.TimeBanLenghts");

        if (sender instanceof Player p) {
            if (p.hasPermission("optools.timeban")) {
                if (args.length == 2) {
                    if (timeBanLenghts.size() != 0) {
                        if (Bukkit.getPlayerExact(args[0]) != null) {
                            CreateGui(p, timeBanLenghts, guiTitle, messagePrefix);
                            guiOpen.put(p, Bukkit.getPlayerExact(args[0]));
                            banReasons.put(Bukkit.getPlayerExact(args[0]), args[1]);
                        } else {
                            p.sendMessage(errorMessage);
                        }
                    } else {
                        p.sendMessage(errorMessage);
                    }
                } else {
                    p.sendMessage(errorMessage);
                }
            } else {
                p.sendMessage(noPermissionMessage);
            }
        } else {
            System.out.println(messagePrefix + errorMessage);
        }
        return true;
    }

    public void CreateGui(Player p, List<Integer> timeBanLenghts, String title, String messagePrefix) {
        if (timeBanLenghts.size() == 0) {
            return;
        }

        Inventory gui = Bukkit.createInventory(p, ((int) (timeBanLenghts.size() / 9 + 1)) * 9,  messagePrefix + title);

        for (int i = 0; i < timeBanLenghts.size(); i++) {
            //Create item
            ItemStack banItem;
            if (timeBanLenghts.get(i) < 30) {
                banItem = new ItemStack(Material.LIME_CONCRETE, 1);
            } else if (timeBanLenghts.get(i) < 120) {
               banItem = new ItemStack(Material.YELLOW_CONCRETE, 1);
            } else if (timeBanLenghts.get(i) < 300) {
                banItem = new ItemStack(Material.ORANGE_CONCRETE, 1);
            } else {
                banItem = new ItemStack(Material.RED_CONCRETE, 1);
            }

            //Set item meta
            ItemMeta itemMeta = banItem.getItemMeta();
            if (timeBanLenghts.get(i) != 1) {
                itemMeta.setDisplayName(timeBanLenghts.get(i) + " " + ChatColor.RED + "Minutes.");
            } else {
                itemMeta.setDisplayName("1" + " " + ChatColor.RED + "Minute.");
            }

            //Apply meta and put item into inventory
            banItem.setItemMeta(itemMeta);
            gui.setItem(i, banItem);
        }
        p.openInventory(gui);
    }
}
