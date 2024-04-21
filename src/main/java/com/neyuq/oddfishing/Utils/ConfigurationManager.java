package com.neyuq.oddfishing.Utils;

import com.neyuq.oddfishing.Models.MaterialConfigModel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationManager {
    private static final HashMap<String, FileConfiguration> configMap = new HashMap<>();
    private static ConfigurationManager instance;
    private final JavaPlugin plugin;

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

    public void saveConfigs() {
        if (configMap.isEmpty()) return;
        for (String fileName : configMap.keySet()) {
            saveConfig(fileName);
        }
    }

    public MaterialConfigModel parseRewardEntry(Map<String, Object> rewardMap) {
        try {
            String materialString = (String) rewardMap.get("material");
            double probability = (Double) rewardMap.get("probability");
            return new MaterialConfigModel(materialString, probability);
        } catch (Exception e) {
            System.out.println("Error parsing reward entry: " + e.getMessage());
            return null;
        }
    }
    public List<MaterialConfigModel> parseRewardList(List<?> reward) {
        List<MaterialConfigModel> result = new ArrayList<>();

        for (Object rewardObject : reward) {
            if (rewardObject instanceof Map) {
                assert false;
                result.add(parseRewardEntry((Map<String, Object>) rewardObject));
            }
        }
        return result;
    }

}
