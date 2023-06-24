package com.example.resourcepackplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
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
import java.util.logging.Level;

public class ResourcePackPlugin extends JavaPlugin implements CommandExecutor {

    private FileConfiguration pluginConfig;

    @Override
    public void onEnable() {
        createPluginYml();
        registerCommands();
    }

    private void createPluginYml() {
        File folder = getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }

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

        File commandsFile = new File(folder, "commands.yml");

        if (!commandsFile.exists()) {
            try (InputStream inputStream = getResource("commands.yml");
                 FileOutputStream outputStream = new FileOutputStream(commandsFile)) {
                Files.copy(inputStream, commandsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Failed to create commands.yml", e);
            }
        }
    }
    private void registerCommands() {
    File commandsFile = new File(getDataFolder(), "commands.yml");
    if (commandsFile.exists()) {
        YamlConfiguration commandsConfig = YamlConfiguration.loadConfiguration(commandsFile);
        ConfigurationSection commandsSection = commandsConfig.getConfigurationSection("commands");
        if (commandsSection != null) {
            for (String commandName : commandsSection.getKeys(false)) {
                ConfigurationSection commandSection = commandsSection.getConfigurationSection(commandName);
                if (commandSection != null) {
                    PluginCommand pluginCommand = getCommand(commandName);
                    if (pluginCommand != null) {
                        pluginCommand.setExecutor(this);

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



    private void reloadPluginYml() {
        File folder = getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }
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

        String commandName = command.getName(); // Retrieve the actual command name
        ConfigurationSection commandSection = pluginConfig.getConfigurationSection("commands." + commandName);
        if (commandSection != null) {
            List<String> resourcePackUrls = commandSection.getStringList("resource-pack-urls");
            if (!resourcePackUrls.isEmpty()) {
                for (String resourcePackUrl : resourcePackUrls) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        player.setResourcePack(resourcePackUrl);
                        player.sendMessage(ChatColor.GREEN + "Resource pack loading: " + resourcePackUrl);
                    } else {
                        sender.sendMessage("This command can only be executed by a player.");
                    }
                }
                return true;
            } else {
                sender.sendMessage("Resource pack URLs are not specified for this command.");
            }
        }
        // Existing code...

            return false;
        }

        }
