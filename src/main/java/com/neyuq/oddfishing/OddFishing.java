package com.neyuq.oddfishing;

import com.neyuq.oddfishing.Commands.CommandFish;
import com.neyuq.oddfishing.Listeners.FishingListener;
import com.neyuq.oddfishing.Config.ConfigurationManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class OddFishing extends JavaPlugin {

    private Logger console;
    private ConfigurationManager configurationManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        console = getServer().getLogger();

        // Load Configuration
        configurationManager = ConfigurationManager.getInstance(this);

        // Add listener and events
        LoadListenerAndEvents();
        // load Command
        RegisterCommand();
    }

    @Override
    public void onDisable() {
        configurationManager.saveConfigs();

        console.log(Level.OFF,"§cPlugin đã sao lưu config và ngắt kết nối với database!");
    }


    private void LoadListenerAndEvents() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new FishingListener(console), this);
    }

    private void RegisterCommand() {
        Objects.requireNonNull(getCommand("fish")).setExecutor(new CommandFish());
    }
}
