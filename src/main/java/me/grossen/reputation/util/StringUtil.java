package me.grossen.reputation.util;

import org.bukkit.ChatColor;

public class StringUtil {

    public static String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
