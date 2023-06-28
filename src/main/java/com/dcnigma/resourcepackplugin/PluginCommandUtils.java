package com.dcnigma.resourcepackplugin;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class PluginCommandUtils {

    public static void setResourcePackUrls(PluginCommand pluginCommand, List<String> resourcePackUrls) {
        // Set the executor of the plugin command to a new instance of ResourcePackCommand
        pluginCommand.setExecutor(new ResourcePackCommand(resourcePackUrls));
    }
}
