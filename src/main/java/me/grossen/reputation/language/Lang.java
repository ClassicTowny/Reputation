package me.grossen.reputation.language;

import me.grossen.reputation.Reputation;
import me.grossen.reputation.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Lang {

    private Reputation plugin;

    private FileConfiguration loader;
    private String prefix;

    public Lang(Reputation plugin) {
        this.plugin = plugin;
        loadLang();
    }

    private void loadLang() {
        File langFile = new File(plugin.getDataFolder(), "language.yml");

        if (!langFile.exists()) {
            try {
                langFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        loader = YamlConfiguration.loadConfiguration(langFile);

        for (LangType langType : LangType.values()) {
            if (!loader.contains(langType.getPath())) {
                loader.set(langType.getPath(), langType.getDef());
            }
        }

        try {
            loader.save(langFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        prefix = StringUtil.format(loader.getString(LangType.PREFIX.getPath())).trim() + " ";
    }

    public void sendLang(LangType langType, CommandSender sender, boolean usePrefix, String... placeHolders) {
        String s = StringUtil.format(loader.getString(langType.getPath()));

        if (StringUtils.isEmpty(s)) {
            return;
        }

        if (placeHolders.length != 0) {
            String phTemp = "";

            for (String ph : placeHolders) {
                if (phTemp.equals("")) {
                    phTemp = ph;
                } else {
                    s = s.replace("{" + phTemp + "}", ph);
                    phTemp = "";
                }
            }
        }

        if (s.startsWith("\n")) {
            sender.sendMessage(" ");
            s = s.replaceFirst("\n", "");
        }
        boolean hasAtEnd = false;
        if (s.endsWith("\n")) {
            hasAtEnd = true;
            s = s.replace("\n", "");
        }
        sender.sendMessage(usePrefix ? prefix + s : s);
        if (hasAtEnd) {
            sender.sendMessage(" ");
        }
    }

}
