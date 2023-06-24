package com.example.resourcepackplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public class ResourcePackPlugin extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        createPluginYml();

        // Register the command
        getCommand("loadpack").setExecutor(this);
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
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Access the resource-pack-url from plugin.yml
            String resourcePackUrl = getDescription().getCommands().get("loadpack").get("resource-pack-url").toString();

            if (resourcePackUrl != null) {
                // Print the value of resourcePackUrl for debugging
              //  getLogger().info("Resource pack URL: " + resourcePackUrl);

                player.setResourcePack(resourcePackUrl);
                player.sendMessage("Resource pack loading: " + resourcePackUrl);
                return true;
            } else {
                player.sendMessage("Resource pack URL is not specified in the configuration.");
            }
        }
        return false;
    }
}
