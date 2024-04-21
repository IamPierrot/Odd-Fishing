package com.neyuq.oddfishing.Commands;

import com.neyuq.oddfishing.Utils.ConfigurationManager;
import com.neyuq.oddfishing.Utils.RandomMaterialSelector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

interface ICommand extends CommandExecutor {
    ConfigurationManager configManger = ConfigurationManager.getInstance();
    RandomMaterialSelector materialSelector = RandomMaterialSelector.getInstance();

    @Override
    boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings);
}
