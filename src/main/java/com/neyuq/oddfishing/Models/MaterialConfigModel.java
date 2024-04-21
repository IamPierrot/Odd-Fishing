package com.neyuq.oddfishing.Models;


import java.util.Map;

public class MaterialConfigModel {
    private final String material;
    private final double probability;

    public MaterialConfigModel(String material, double probability) {
        this.material = material;
        this.probability = probability;
    }

    public String getMaterial() {
        return material;
    }
    public double getProbability() {
        return probability;
    }
}
