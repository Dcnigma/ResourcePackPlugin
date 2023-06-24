package com.example.resourcepackplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class ResourcePackPlugin extends JavaPlugin implements CommandExecutor {

    private FileConfiguration pluginConfig;

    @Override
    public void onEnable() {
        createPluginYml();

        // Register the commands
        registerCommands();
    }

    private void createPluginYml() {
        File folder = new File(getDataFolder(), "ResourcePackPlugin");
        folder.mkdirs();

        File pluginYmlFile = new File(folder, "plugin.yml");

        if (!pluginYmlFile.exists()) {
            try (InputStream inputStream = getResource("plugin.yml");
                 FileOutputStream outputStream = new FileOutputStream(pluginYmlFile)) {
                Files.copy(inputStream, pluginYmlFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Failed to create plugin.yml", e);
            }
        }

        // Load the plugin.yml file into memory
        pluginConfig = YamlConfiguration.loadConfiguration(pluginYmlFile);
    }

    private void registerCommands() {
        ConfigurationSection commandsSection = pluginConfig.getConfigurationSection("commands");
        if (commandsSection != null) {
            // Iterate over the commands
            for (String commandName : commandsSection.getKeys(false)) {
                ConfigurationSection commandSection = commandsSection.getConfigurationSection(commandName);
                if (commandSection != null) {
                    List<String> resourcePackUrls = commandSection.getStringList("resource-pack-urls");
                    if (!resourcePackUrls.isEmpty()) {
                        // Register the command executor for each command
                        getCommand(commandName).setExecutor(this);
                    }
                }
            }
        }
    }

    private void reloadPluginYml() {
        File folder = new File(getDataFolder(), "ResourcePackPlugin");
        File pluginYmlFile = new File(folder, "plugin.yml");

        // Reload the plugin.yml file
        if (pluginYmlFile.exists()) {
            pluginConfig = YamlConfiguration.loadConfiguration(pluginYmlFile);
            getLogger().info("Reloaded plugin.yml");
        } else {
            getLogger().warning("plugin.yml not found. Run /ResourcePackPlugin reload to generate it.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("ResourcePackPlugin")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                reloadPluginYml();
                sender.sendMessage("Reloaded plugin configuration.");
                return true;
            }
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            ConfigurationSection commandSection = pluginConfig.getConfigurationSection("commands." + label);
            if (commandSection != null) {
                List<String> resourcePackUrls = commandSection.getStringList("resource-pack-urls");
                if (!resourcePackUrls.isEmpty()) {
                    for (String resourcePackUrl : resourcePackUrls) {
                        player.setResourcePack(resourcePackUrl);
                        player.sendMessage("Resource pack loading: " + resourcePackUrl);
                    }
                    return true;
                } else {
                    player.sendMessage("Resource pack URLs are not specified for this command.");
                }
            }
        }
        return false;
    }
}
