package de.xcrafttm.borderxp.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class BorderConfig {

    private final File file;
    private final YamlConfiguration config;

    public BorderConfig() {

        File folder = new File("plugins/BorderXP");
        file = new File(folder.getPath(), "config.yml");

        config = YamlConfiguration.loadConfiguration(file);
        asksDefault();
    }

    private void asksDefault() {
        this.askForExists(file, config, "villagetoggle", "false");
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
        config.set("villagetoggle", "true");
        saveFile(file, config);
    }
    public void setFalse() {
        config.set("villagetoggle", "false");
        saveFile(file, config);
    }
    public String getVillageToggle() {
        return this.askInConfigString(config, "villagetoggle");
    }
}
