package me.grossen.reputation.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.grossen.reputation.Reputation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StringUtil {

    public static String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String format(String s, Player player) {
        if (!Reputation.isPlaceholderAPIInstalled()) {
            return format(s);
        } else {
            try {
                s = PlaceholderAPI.setPlaceholders(player, s);
            } catch (Exception ex) {
                Bukkit.getLogger().warning("[Reputation] Cannot set PlaceHolders.");
            }
            return format(s);
        }
    }

}
