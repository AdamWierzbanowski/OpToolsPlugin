package me.adamwierzbanowski.optools.Commands;

import me.adamwierzbanowski.optools.OpTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class God implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String messagePrefix = OpTools.plugin.GetMessagePrefix();
        String noPermissionMessage = OpTools.plugin.GetNoPermissionMessage();

        if (sender instanceof Player p) {
            //Player
            if (args.length == 0) {
                if (p.hasPermission("optools.god.self")) {
                    GodMode(p, messagePrefix);
                } else {
                    p.sendMessage(messagePrefix + noPermissionMessage);
                }

                    //target
                } else if (args.length == 1) {
                if (p.hasPermission("optools.god.others")) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target == null) {

                    } else {
                        GodMode(target, messagePrefix);
                    }
                } else {
                    p.sendMessage(messagePrefix + noPermissionMessage);
                }
            }
        }
        return true;
    }

    private void GodMode(Player p, String messagePrefix) {
        if (p.isInvulnerable()) {
            p.setInvulnerable(false);
            p.sendMessage(messagePrefix + ChatColor.YELLOW + "God Mode is" + ChatColor.DARK_RED + " Disabled");
        } else {
            p.setInvulnerable(true);
            p.sendMessage(messagePrefix + ChatColor.YELLOW + "God Mode is" + ChatColor.DARK_GREEN + " Enabled");
        }
    }
}
