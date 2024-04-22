package com.neyuq.oddfishing.Models;

import org.bukkit.Material;

public class MaterialConfigModel {
    private final String material;
    private final double probability;

    private MaterialConfigModel(String material, double probability) {
        this.material = material;
        this.probability = probability;
    }


    public static MaterialConfigModel createModel(Material material, double probability) {
        return new MaterialConfigModel(material.toString(), probability);
    }
    public static MaterialConfigModel createModel(String material, double probability) {
        return new MaterialConfigModel(material, probability);
    }


    public String getRawMaterial() {
        return material;
    }

    public double getProbability() {
        return probability;
    }

    public Material getMaterial() {
        return Material.valueOf(getRawMaterial());
    }
}
