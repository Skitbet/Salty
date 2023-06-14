package com.skitbet.salty.shared;

import org.bukkit.ChatColor;
import com.skitbet.salty.bungee.SaltyBungee;
import com.skitbet.salty.spigot.SaltyPlugin;

public class Logger {

    public static void info(String message) {
        if (Salty.isSpigot()) {
            message = "&e&lSalty Spigot &8&l| " + message;
            SaltyPlugin.INSTANCE.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }else {
            message = "&b&lSalty Bungee &8&l| " + message;
            SaltyBungee.INSTANCE.getProxy().getLogger().info(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message));
        }
    }

}
