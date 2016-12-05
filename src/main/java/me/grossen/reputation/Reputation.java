package me.grossen.reputation;

import me.grossen.reputation.language.Lang;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Reputation extends JavaPlugin {

    private Lang lang;
    private Map<UUID, PlayerData> playerDataList = new HashMap<>();

    private File dataFile;
    private FileConfiguration dataLoader;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        lang = new Lang(this);
        getCommand("reputation").setExecutor(new ReputationCommand(this));

        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        dataLoader = YamlConfiguration.loadConfiguration(dataFile);
    }

    public Lang getLang() {
        return lang;
    }

    public PlayerData getPlayerData(UUID uuid) {
        if (playerDataList.containsKey(uuid)) {
            return playerDataList.get(uuid);
        }
        PlayerData playerData = new PlayerData(this, uuid);
        playerDataList.put(uuid, playerData);
        return playerData;
    }

    public FileConfiguration getDataLoader() {
        return dataLoader;
    }

    public void saveDataFile() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    dataLoader.save(dataFile);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskAsynchronously(this);
    }
}
