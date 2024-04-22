package com.neyuq.oddfishing.Utils;

import com.neyuq.oddfishing.Config.FishConfig;
import com.neyuq.oddfishing.Models.MaterialConfigModel;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RandomMaterialSelector {

    private static final RandomMaterialSelector instance = new RandomMaterialSelector();
    private final FishConfig fishConfig = FishConfig.getInstance();
    private final NavigableMap<Double, Material> materialProbabilities = new TreeMap<>();
    private final Map<Material, Double> materialMap = new HashMap<>();
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
        materialMap.put(material, probability);
    }

    public synchronized void setMaterial(Material material, double newProbability) {
        Double oldProbability = materialMap.getOrDefault(material, null);
        if (oldProbability != null) {
            materialProbabilities.remove(oldProbability);
            materialMap.remove(material);
        }

        add(material, newProbability);
    }

    private synchronized void addDefaults() {
        fishConfig.setConfigFile(defaultRewards());
        addMaterialFromConfig(fishConfig.getRewardList());

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

    private void addMaterialFromConfig(List<MaterialConfigModel> rewardConfig) {
        for (MaterialConfigModel material : rewardConfig) {
           setMaterial(Material.valueOf(material.getMaterial()), material.getProbability());
        }
    }

    @Override
    public String toString() {
        return materialProbabilities.toString();
    }
}
