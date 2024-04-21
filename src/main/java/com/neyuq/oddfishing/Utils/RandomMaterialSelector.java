package com.neyuq.playermanager.Utils;

import org.bukkit.Material;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomMaterialSelector {
    private final NavigableMap<Double, Material> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    public RandomMaterialSelector() {
        this.random = new Random();
    }

    public void add(Material material, double probability) {
        if (probability <= 0) return;
        total += probability;
        map.put(total, material);
    }

    public Material getRandomMaterial() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
