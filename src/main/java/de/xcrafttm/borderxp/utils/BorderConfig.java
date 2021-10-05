package de.xcrafttm.borderxp.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class BorderConfig {

    private final File nameFile;
    private final YamlConfiguration nameConfig;

    public BorderConfig() {

        File folder = new File("plugins/BorderXP");
        nameFile = new File(folder.getPath(), "config.yml");

        nameConfig = YamlConfiguration.loadConfiguration(nameFile);
        asksDefault();
    }

    private void asksDefault() {
        this.askForExists(nameFile, nameConfig, "villagetoggle", "false");
    }

    private void askForExists(File file, YamlConfiguration config, String key, Object value) {
        if (file != null && config != null) {
            if (!config.contains(key)) {
                config.set(key, value);
                saveFile(file, config);
            }
        }
    }
    private void saveFile(File file, YamlConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String askInConfigString(YamlConfiguration config, String key) {
        if (config != null) {
            if (config.contains(key)) {
                return Objects.requireNonNull(config.getString(key)).replace('&', '§');
            }
        }
        return null;
    }

    public void setTrue() {
        nameConfig.set("villagetoggle", "true");
        saveFile(nameFile, nameConfig);
    }
    public void setFalse() {
        nameConfig.set("villagetoggle", "false");
        saveFile(nameFile, nameConfig);
    }
    public String getVillageToggle() {
        return this.askInConfigString(nameConfig, "villagetoggle");
    }
}
