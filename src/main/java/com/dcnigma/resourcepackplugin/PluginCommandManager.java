package com.dcnigma.resourcepackplugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public class PluginCommandManager {

    public static void registerCommands(ResourcePackPlugin plugin) {
        // Load commands from plugin.yml
        if (plugin.pluginConfig != null) {
            ConfigurationSection pluginCommands = plugin.pluginConfig.getConfigurationSection("commands");
            if (pluginCommands != null) {
                for (String commandName : pluginCommands.getKeys(false)) {
                    ConfigurationSection commandSection = pluginCommands.getConfigurationSection(commandName);
                    if (commandSection != null) {
                        PluginCommand pluginCommand = plugin.getCommand(commandName);
                        if (pluginCommand != null) {
                            CommandExecutor commandExecutor;
                            if (commandName.equalsIgnoreCase("bannedips")) {
                                commandExecutor = new BannedIPsCommand(plugin, "banned-ips.json");
                            } else {
                                commandExecutor = plugin;
                            }

                            pluginCommand.setExecutor(commandExecutor);

                            // Set command description and usage
                            String description = commandSection.getString("description");
                            String usage = commandSection.getString("usage");
                            pluginCommand.setDescription(description);
                            pluginCommand.setUsage(usage);

                            // Set command permission
                            String permission = commandSection.getString("permission");
                            pluginCommand.setPermission(permission);
                        }
                    }
                }
            }
        }

        // Load additional commands from commands.yml
        File commandsFile = new File(plugin.getDataFolder(), "commands.yml");
        if (!commandsFile.exists()) {
            // Copy commands.yml from server's plugin folder if it doesn't exist
            try (InputStream inputStream = new FileInputStream(new File("plugins/ResourcePackPlugin/commands.yml"));
                 FileOutputStream outputStream = new FileOutputStream(commandsFile)) {
                Files.copy(inputStream, commandsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create commands.yml", e);
            }
        }

        if (commandsFile.exists()) {
            YamlConfiguration commandsConfig = YamlConfiguration.loadConfiguration(commandsFile);
            ConfigurationSection commandsSection = commandsConfig.getConfigurationSection("commands");
            if (commandsSection != null) {
                for (String commandName : commandsSection.getKeys(false)) {
                    ConfigurationSection commandSection = commandsSection.getConfigurationSection(commandName);
                    if (commandSection != null) {
                        PluginCommand pluginCommand = plugin.getCommand(commandName);
                        if (pluginCommand != null) {
                            pluginCommand.setExecutor(plugin);

                            // Set command description and usage
                            String description = commandSection.getString("description");
                            String usage = commandSection.getString("usage");
                            pluginCommand.setDescription(description);
                            pluginCommand.setUsage(usage);

                            // Set command permission
                            String permission = commandSection.getString("permission");
                            pluginCommand.setPermission(permission);
                        }
                    }
                }
            }
        }
    }
}
