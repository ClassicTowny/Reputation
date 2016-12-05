package me.grossen.reputation;

import me.grossen.reputation.language.LangType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReputationCommand implements CommandExecutor {

    private Reputation plugin;

    public ReputationCommand(Reputation plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLang().sendLang(LangType.COMMAND_ONLY_PLAYER, sender, true);
            return true;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            plugin.getLang().sendLang(LangType.COMMAND_HELP, player, true);
            return true;
        }

        if (args[0].equals("check")) {
            if (!player.hasPermission("reputation.check")) {
                plugin.getLang().sendLang(LangType.NO_PERMISSION, player, false);
                return true;
            }
            if (args.length == 1) {
                plugin.getLang().sendLang(LangType.COMMAND_NO_ARGS, player, true, "command", "/reputation check <player>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                plugin.getLang().sendLang(LangType.COMMAND_CHECK_PLAYER_NOT_ONLINE, player, true);
                return true;
            }
            PlayerData playerData = plugin.getPlayerData(target.getUniqueId());
            plugin.getLang().sendLang(LangType.COMMAND_CHECK_SUCCESS, player, true, "player", target.getName(), "good", String.valueOf(playerData.getGoodReputation()), "bad", String.valueOf(playerData.getBadReputation()));
        } else if (args[0].equals("set")) {
            if (!player.hasPermission("reputation.set")) {
                plugin.getLang().sendLang(LangType.NO_PERMISSION, player, false);
                return true;
            }
            if (args.length == 1) {
                plugin.getLang().sendLang(LangType.COMMAND_NO_ARGS, player, true, "command", "/reputation set <player> <+/->");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                plugin.getLang().sendLang(LangType.COMMAND_SET_PLAYER_NOT_ONLINE, player, true);
                return true;
            }

            if (target.getUniqueId().equals(player.getUniqueId())) {
                plugin.getLang().sendLang(LangType.COMMAND_SET_NOT_SELF, player, true);
                return true;
            }
            PlayerData playerData = plugin.getPlayerData(target.getUniqueId());

            if (args.length == 2) {
                plugin.getLang().sendLang(LangType.COMMAND_NO_ARGS, player, true, "command", "/reputation set <player> <+/->");
                return true;
            }
            char type = args[2].charAt(0);

            if ((type != '+') && (type != '-')) {
                plugin.getLang().sendLang(LangType.COMMAND_SET_INVALID_TYPE, player, true);
                return true;
            }

            if (!playerData.hasReceivedFrom(player.getUniqueId())) {
                if (type == '+') {
                    plugin.getLang().sendLang(LangType.COMMAND_SET_SUCCESS_GOOD_NEW, player, true, "player", target.getName());
                } else {
                    plugin.getLang().sendLang(LangType.COMMAND_SET_SUCCESS_BAD_NEW, player, true, "player", target.getName());
                }
            } else {
                char oldType = playerData.getReceivedFromType(player.getUniqueId());
                if (oldType == type) {
                    if (type == '+') {
                        plugin.getLang().sendLang(LangType.COMMAND_SET_SUCCESS_GOOD_ALREADY, player, true, "player", target.getName());
                    } else {
                        plugin.getLang().sendLang(LangType.COMMAND_SET_SUCCESS_BAD_ALREADY, player, true, "player", target.getName());
                    }
                    return true;
                } else {
                    if (type == '+') {
                        plugin.getLang().sendLang(LangType.COMMAND_SET_SUCCESS_GOOD_CHANGE, player, true, "player", target.getName());
                    } else {
                        plugin.getLang().sendLang(LangType.COMMAND_SET_SUCCESS_BAD_CHANGE, player, true, "player", target.getName());
                    }
                }
            }
            playerData.addReputation(player.getUniqueId(), type);
        } else {
            plugin.getLang().sendLang(LangType.COMMAND_HELP, player, true);
            return true;
        }

        return true;
    }

}
