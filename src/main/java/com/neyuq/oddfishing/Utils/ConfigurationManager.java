package com.neyuq.playermanager.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigurationManager {
    private static ConfigurationManager instance;
    private final JavaPlugin plugin;
    private static final HashMap<String, FileConfiguration> configMap = new HashMap<>();

    private ConfigurationManager(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public static ConfigurationManager getInstance(JavaPlugin plugin) {
        if (instance == null) new ConfigurationManager(plugin);
        return instance;
    }
    public static @NotNull ConfigurationManager getInstance() {
        if (instance == null) throw new Error("plugin has not been initialized, try to put plugin parameter!");
        return instance;
    }

    // Load or create a config file
    public void loadConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        configMap.put(fileName, config);
    }

    // Get a specific config
    public FileConfiguration getConfig(String fileName) {
        return configMap.getOrDefault(fileName, null);
    }

    // Save a specific config
    public void saveConfig(String fileName) {
        FileConfiguration config = getConfig(fileName);
        if (config != null) {
            try {
                config.save(new File(plugin.getDataFolder(), fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     public void exampleUsage() {
     FileConfiguration mainConfig = getConfig("config.yml");
     if (mainConfig != null) {
     String playerName = mainConfig.getString("player-name");
     // Do something with playerName...
     }

     FileConfiguration customConfig = getConfig("custom-config.yml");
     if (customConfig != null) {
     // Read or modify customConfig...
     saveConfig("custom-config.yml"); // Save changes
     }
     }*/
}
