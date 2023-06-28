package com.dcnigma.resourcepackplugin;

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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.io.FileInputStream;

public class ResourcePackPlugin extends JavaPlugin implements CommandExecutor {

    public FileConfiguration pluginConfig;
    public List<String> bannedIPs;
    public Autoban autoban;
    public String bannedIPFilePath = "banned-ips.json";

    @Override
    public void onEnable() {
        getCommand("bannedips").setExecutor(new BannedIPsCommand(this, bannedIPFilePath));
        PluginFileManager.createPluginFiles(this);
        PluginCommandManager.registerCommands(this);
        bannedIPs = new ArrayList<>();
        BannedIPManager.loadBannedIPs(this);
        autoban = new Autoban(this); // Instantiate the Autoban class and pass the instance of ResourcePackPlugin
        getServer().getPluginManager().registerEvents(autoban, this);
    }

    public void reloadPluginYml() {
        PluginFileManager.reloadPluginYml(this);
    }

    public void displayHelp(CommandSender sender) {
        HelpManager.displayHelp(sender);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ResourcePackPlugin")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                reloadPluginYml();
                sender.sendMessage(ChatColor.GREEN + "Reloaded plugin configuration.");
                return true;
            } else {
                displayHelp(sender);
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("ResourcePackPluginHelp")) {
            displayHelp(sender);
            return true;
        }

        String commandName = command.getName(); // Retrieve the actual command name
        ConfigurationSection commandSection = pluginConfig.getConfigurationSection("commands." + commandName);
        if (commandSection != null) {
            List<String> resourcePackUrls = commandSection.getStringList("resourcePackUrls");
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
        } else if (command.getName().equalsIgnoreCase("bannedips")) {
            BannedIPsCommand bannedIPsCommand = new BannedIPsCommand(this, bannedIPFilePath);
            return bannedIPsCommand.onCommand(sender, command, label, args);
        }

        return false;
    }

    public List<String> getBannedIPs() {
        return bannedIPs;
    }

    public void loadPluginYml() {
        File pluginYmlFile = new File(getDataFolder(), "plugin.yml");
        if (!pluginYmlFile.exists()) {
            try (InputStream inputStream = new FileInputStream(new File("plugins/ResourcePackPlugin/plugin.yml"));
            //try (InputStream inputStream = getResource("plugin.yml");
                 FileOutputStream outputStream = new FileOutputStream(pluginYmlFile)) {
                Files.copy(inputStream, pluginYmlFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Failed to create plugin.yml", e);
            }
        }

        if (pluginYmlFile.exists()) {
            pluginConfig = YamlConfiguration.loadConfiguration(pluginYmlFile);
        }
    }
}
