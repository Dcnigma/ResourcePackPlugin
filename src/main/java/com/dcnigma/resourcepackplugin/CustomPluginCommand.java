package com.dcnigma.resourcepackplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class CustomPluginCommand extends Command {

    public final PluginCommand pluginCommand;

    public CustomPluginCommand(PluginCommand pluginCommand) {
        super(pluginCommand.getName());
        this.pluginCommand = pluginCommand;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return pluginCommand.execute(sender, commandLabel, args);
    }
}
