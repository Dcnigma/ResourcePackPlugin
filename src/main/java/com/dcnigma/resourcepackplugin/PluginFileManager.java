package com.dcnigma.resourcepackplugin;

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

public class PluginFileManager {

    public static void createPluginFiles(ResourcePackPlugin plugin) {
        File folder = plugin.getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File pluginYmlFile = new File(folder, "plugin.yml");
        if (!pluginYmlFile.exists()) {
            createFileFromResource(plugin, "plugin.yml", pluginYmlFile);
        } else {
            plugin.pluginConfig = YamlConfiguration.loadConfiguration(pluginYmlFile);
        }

        File commandsFile = new File(folder, "commands.yml");
        if (!commandsFile.exists()) {
            createFileFromResource(plugin, "commands.yml", commandsFile);
        }
    }

    public static void reloadPluginYml(ResourcePackPlugin plugin) {
        File pluginYmlFile = new File(plugin.getDataFolder(), "plugin.yml");

        // Reload the plugin.yml file
        if (pluginYmlFile.exists()) {
            plugin.pluginConfig = YamlConfiguration.loadConfiguration(pluginYmlFile);
            plugin.getLogger().info("Reloaded plugin.yml");
        } else {
            plugin.getLogger().warning("plugin.yml not found, made a new one!");
            createFileFromResource(plugin, "plugin.yml", pluginYmlFile);
        }
    }

    public static void createFileFromResource(ResourcePackPlugin plugin, String resourceName, File targetFile) {
        try (InputStream inputStream = plugin.getResource(resourceName);
             FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to create " + resourceName, e);
        }
    }
}
