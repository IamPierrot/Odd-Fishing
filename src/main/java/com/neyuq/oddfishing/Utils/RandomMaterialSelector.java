package com.neyuq.oddfishing.Utils;

import com.google.common.util.concurrent.AtomicDouble;
import com.neyuq.oddfishing.Models.MaterialConfigModel;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RandomMaterialSelector {

    private static final RandomMaterialSelector instance = new RandomMaterialSelector();
    private final ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private final NavigableMap<Double, Material> materialProbabilities = new TreeMap<>();
    private final double maxRate = 100.0;
    private RandomMaterialSelector() {
        addDefaults();
    }

    public static RandomMaterialSelector getInstance() {
        return instance;
    }

    public @Nullable Material getRandomMaterial() {
        double randomValue = (Math.random() * maxRate);
        return materialProbabilities.floorEntry(randomValue).getValue();
    }

    public @Nullable Material getMaterial(double probability) {
        return materialProbabilities.floorEntry(probability).getValue();
    }

    public double getRandomProbability() {
        return (Math.random() * maxRate);
    }

    private synchronized void clear() {
        materialProbabilities.clear();
    }

    private synchronized void add(Material material, double probability) {
        if (probability <= 0) {
            return;
        }
        materialProbabilities.put(probability, material);
    }

    public synchronized void setMaterial(Material material, double newProbability) {
        Double oldProbability = getKeyByValue(material);
        if (oldProbability != null) {
            materialProbabilities.remove(oldProbability);
        }

        add(material, newProbability);
    }

    private Double getKeyByValue(Material value) {
        for (Map.Entry<Double, Material> entry : materialProbabilities.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private synchronized void addDefaults() {
        FileConfiguration materialConfig = configurationManager.getConfig("fish-event.yml");

        List<?> rewardConfig = materialConfig.getMapList("reward");
        if (rewardConfig.isEmpty()) {
            materialConfig.set("reward", convertRewardsToMapList(defaultRewards()));
            configurationManager.saveConfig("fish-event.yml");
        }

        rewardConfig = materialConfig.getMapList("reward");
        addMaterialFromConfig(rewardConfig);

        setMaterial(null, 60); // Add a chance for no material (optional)
    }

    private List<MaterialConfigModel> defaultRewards() {
        List<MaterialConfigModel> defaultReward = new ArrayList<>();
        defaultReward.add(new MaterialConfigModel(Material.COBBLESTONE.toString(), 70));
        defaultReward.add(new MaterialConfigModel(Material.DIAMOND.toString(), 10));
        defaultReward.add(new MaterialConfigModel(Material.GOLD_INGOT.toString(), 12));
        defaultReward.add(new MaterialConfigModel(Material.IRON_INGOT.toString(), 15));
        return defaultReward;
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

    private void addMaterialFromConfig(List<?> rewardConfig) {
        for (Object rewardObject : rewardConfig) {
            if (rewardObject instanceof Map) {
                // Convert the map to a MaterialConfigModel object
                MaterialConfigModel reward = configurationManager.parseRewardEntry((Map<String, Object>) rewardObject);

                if (reward != null) {
                    // Use the reward information (material, probability)
                    setMaterial(Material.valueOf(reward.getMaterial()), reward.getProbability());
                }
            }
        }
    }
//    private double calculateTotalProbability() {
//        // This method calculates the sum of all probabilities in the map
//        AtomicDouble totalProbability = new AtomicDouble(0.0);
//        materialProbabilities.forEach((probability, material) -> totalProbability.addAndGet(probability));
//        return totalProbability.get();
//    }

    @Override
    public String toString() {
        return materialProbabilities.toString();
    }
}
