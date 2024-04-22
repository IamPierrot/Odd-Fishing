package com.neyuq.oddfishing.Commands;

import com.neyuq.oddfishing.Models.MaterialConfigModel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CommandFish implements ICommand {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            List<MaterialConfigModel> materialConfigModels = fishConfig.getRewardList();

            player.sendMessage("===========OddFishing===========");
            for (MaterialConfigModel material : materialConfigModels) {
                player.sendMessage("- " + material.getMaterial() + " : " + material.getProbability());
            }
            player.sendMessage("===========OddFishing===========");
        } else if (commandSender instanceof ConsoleCommandSender) {
            ConsoleCommandSender logger = (ConsoleCommandSender) commandSender;

            List<MaterialConfigModel> materialConfigModels = fishConfig.getRewardList();

            logger.sendMessage("===========OddFishing===========");
            for (MaterialConfigModel material : materialConfigModels) {
                logger.sendMessage("- " + material.getMaterial() + " : " + material.getProbability());
            }
            logger.sendMessage("===========OddFishing===========");
        }

        return true;
    }

}
