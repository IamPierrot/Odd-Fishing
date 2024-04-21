package com.neyuq.oddfishing.Events;

import com.neyuq.oddfishing.Utils.ConfigurationManager;
import com.neyuq.oddfishing.Utils.RandomMaterialSelector;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PlayerFishingEvent extends PlayerFishEvent implements Cancellable {
    private final RandomMaterialSelector randomMaterialSelector = RandomMaterialSelector.getInstance();
    private final ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    public PlayerFishingEvent(@NotNull Player player, @Nullable Entity entity, @NotNull FishHook hookEntity, @Nullable EquipmentSlot hand, @NotNull PlayerFishEvent.State state) {
        super(player, entity, hookEntity, hand, state);
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
}
