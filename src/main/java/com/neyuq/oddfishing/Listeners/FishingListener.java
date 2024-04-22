package com.neyuq.oddfishing.Listeners;

import com.neyuq.oddfishing.Handlers.FishingHandler;
import com.neyuq.oddfishing.Utils.RandomMaterialSelector;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

public class FishingListener implements Listener {

    private final Logger console;
    private final RandomMaterialSelector randomSystem;


    public FishingListener(Logger c) {
        console = c;
        randomSystem = RandomMaterialSelector.getInstance();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFish(PlayerFishEvent event) {
        FishingHandler fishingHandler = new FishingHandler(event);

        ItemStack fishingRod = event.getPlayer().getInventory().getItemInMainHand();

        if (fishingHandler.isCaughtFish() && fishingHandler.getHook().getApplyLure() && fishingRod.containsEnchantment(Enchantment.LURE)) {

            int lureLevel = fishingRod.getEnchantmentLevel(Enchantment.LURE);
            double randomRate = randomSystem.getRandomProbability() - (lureLevel * 3.5);

            fishingHandler.generateRandomReward(randomRate);
        } else fishingHandler.generateRandomReward();
    }
}

