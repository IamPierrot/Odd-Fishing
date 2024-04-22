package com.neyuq.oddfishing.Handlers;

import com.neyuq.oddfishing.Config.ConfigurationManager;
import com.neyuq.oddfishing.Utils.RandomMaterialSelector;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class FishingHandler extends PlayerFishEvent {
    private final RandomMaterialSelector randomMaterialSelector = RandomMaterialSelector.getInstance();
    private final ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    public FishingHandler(PlayerFishEvent event) {
        super(event.getPlayer(), event.getCaught(), event.getHook(), event.getHand(), event.getState());
    }

    public boolean isCaughtFish() {
        return getState().equals(PlayerFishEvent.State.CAUGHT_FISH) && getCaught() instanceof Item;
    }

    public void generateRandomReward() {
        if (!isCaughtFish()) return;
        Item caughtItem = (Item) getCaught();

        Material randomMaterial = randomMaterialSelector.getRandomMaterial();
        if (Objects.isNull(randomMaterial)) return;

        ItemStack itemStack = new ItemStack(randomMaterial);
        itemStack.setItemMeta(itemStack.getItemMeta());

        Objects.requireNonNull(caughtItem).setItemStack(itemStack);
    }
    public void generateRandomReward(double probability) {
        if (!isCaughtFish()) return;
        Item caughtItem = (Item) getCaught();

        Material randomMaterial = randomMaterialSelector.getMaterial(probability);
        if (Objects.isNull(randomMaterial)) return;

        ItemStack itemStack = new ItemStack(randomMaterial);
        itemStack.setItemMeta(itemStack.getItemMeta());

        Objects.requireNonNull(caughtItem).setItemStack(itemStack);
    }
}
