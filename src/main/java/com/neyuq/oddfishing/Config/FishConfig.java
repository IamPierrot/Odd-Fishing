package com.neyuq.oddfishing.Config;

import com.neyuq.oddfishing.Models.MaterialConfigModel;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FishConfig extends Configuration {
    private final String configFile = "fish.yml";
    public static FishConfig instance = new FishConfig();

    private FishConfig() {
        loadConfig();
    }

    public static FishConfig getInstance() {
        return instance;
    }

    @Override
    public void save() {
        configManager.saveConfig(configFile);
    }

    @Override
    FileConfiguration getConfig() {
        return configManager.getConfig(configFile);
    }

    @Override
    void loadConfig() {
        configManager.loadConfig(configFile);
    }

    public void setConfigFile(List<MaterialConfigModel> rewards) {
        if (getConfig().getMapList("reward").isEmpty()) getConfig().set("reward", convertRewardsToMapList(rewards));
        else {
            for (MaterialConfigModel material : rewards) {
                getConfig().getMapList("reward").add(convertRewardToMap(material));
            }
        }
        save();
    }

    public List<MaterialConfigModel> getRewardList() {
        return parseRewardList(getConfig().getMapList("reward"));
    }

    private List<Map<String, Object>> convertRewardsToMapList(List<MaterialConfigModel> rewards) {
        List<Map<String, Object>> convertedConfigReward = new ArrayList<>();
        for (MaterialConfigModel material : rewards) {
            Map<String, Object> rewardEntry = new HashMap<>();
            rewardEntry.put("material", material.getMaterial());
            rewardEntry.put("probability", material.getProbability());
            convertedConfigReward.add(rewardEntry);
        }
        return convertedConfigReward;
    }
    private Map<String, Object> convertRewardToMap(MaterialConfigModel material) {
        Map<String, Object> rewardEntry = new HashMap<>();
        rewardEntry.put("material", material.getMaterial());
        rewardEntry.put("probability", material.getProbability());
        return rewardEntry;
    }

    private MaterialConfigModel parseRewardEntry(Map<String, Object> rewardMap) {
        try {
            String materialString = (String) rewardMap.get("material");
            double probability = (double) rewardMap.get("probability");
            return MaterialConfigModel.createModel(materialString, probability);
        } catch (Exception e) {
            System.out.println("Error parsing reward entry: " + e.getMessage());
            return null;
        }
    }

    private List<MaterialConfigModel> parseRewardList(List<?> reward) {
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
