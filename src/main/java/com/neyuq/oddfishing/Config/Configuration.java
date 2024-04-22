package com.neyuq.oddfishing.Config;

import com.neyuq.oddfishing.Utils.RandomMaterialSelector;
import org.bukkit.configuration.file.FileConfiguration;

abstract class Configuration {
    ConfigurationManager configManager = ConfigurationManager.getInstance();
    RandomMaterialSelector randomMaterialSelector = RandomMaterialSelector.getInstance();

    abstract void save();
    abstract FileConfiguration getConfig();
    abstract void loadConfig();
}
