package com.neyuq.oddfishing.Models;

import org.bson.Document;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;

public class PlayerModel {

    private final String userName;
    private final UUID uniqueId;
    private final int level;
    private final EntityEquipment equipment;
    private final long playTime;
    private final Inventory inventory;


    public PlayerModel(Player player) {
        this.userName = player.getName();
        this.uniqueId = player.getUniqueId();
        this.level = player.getLevel();
        this.equipment = player.getEquipment();
        this.playTime = player.getPlayerTime();
        this.inventory = player.getInventory();

    }

    private Document createItemDataDocument(ItemStack item) {
        Document itemData = new Document();
        itemData.append("type", isNull(item) ? null : item.getType().name());
        itemData.append("amount", isNull(item) ? null : item.getAmount());

        if (hasEnchantments(item)) {
            itemData.append("enchantments", createEnchantmentList(item));
        }

        return itemData;
    }

    private boolean hasEnchantments(ItemStack item) {
        return item != null && !item.getEnchantments().isEmpty();
    }

    private List<Document> createEnchantmentList(ItemStack item) {
        List<Document> enchantments = new ArrayList<>();
        for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
            Document enchantmentData = new Document();
            enchantmentData.append("id", entry.getKey().getName());
            enchantmentData.append("level", entry.getValue());
            enchantments.add(enchantmentData);
        }
        return enchantments;
    }

    private List<Document> createInventoryData() {
        List<Document> inventoryData = new ArrayList<>();
        for (ItemStack item : inventory.getStorageContents()) {

            inventoryData.add(createItemDataDocument(item).append("slot", inventoryData.size() + 1));
        }
        return inventoryData;
    }

    private List<Document> createEquipmentData() {
        List<Document> equipmentData = new ArrayList<>();
        for (ItemStack armorItem : equipment.getArmorContents()) {
            equipmentData.add(createItemDataDocument(armorItem).append("slot", equipmentData.size() + 1));
        }
        equipmentData.add(createItemDataDocument(equipment.getItemInOffHand()).append("slot", equipmentData.size() + 1));
        return equipmentData;
    }

    /**
     * Maps player data to a document, including inventory and equipment.
     *
     * @return a document representing the player's data
     */
    public Document toDocument() {
        Document playerData = new Document();
        playerData.append("userName", userName);
        playerData.append("level", level);
        playerData.append("playTime", playTime);

        playerData.append("inventory", createInventoryData());
        playerData.append("equipment", createEquipmentData());

        return playerData;
    }


    public String getUserName() {
        return userName;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

}

