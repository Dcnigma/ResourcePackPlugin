package com.dcnigma.resourcepackplugin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class BannedIPsCommand implements CommandExecutor {

    private final ResourcePackPlugin plugin;
    private final String bannedIPFilePath;

    public BannedIPsCommand(ResourcePackPlugin plugin, String bannedIPFilePath) {
        this.plugin = plugin;
        this.bannedIPFilePath = bannedIPFilePath;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "==== Banned IPs ==========================");
        File bannedIPFile = new File(plugin.getServer().getWorldContainer(), bannedIPFilePath);

        if (!bannedIPFile.exists()) {
            sender.sendMessage(ChatColor.RED + "The banned-ips.json file does not exist.");
            plugin.getLogger().warning("The banned-ips.json file does not exist.");
            return true;
        }

        try (FileReader reader = new FileReader(bannedIPFile)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                Gson gson = new Gson();
                List<String> bannedIPs = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    String ip = jsonObject.get("ip").getAsString();
                    String reason = jsonObject.get("reason").getAsString();
                    String expires = jsonObject.get("expires").getAsString();
                    Date created = parseDate(jsonObject.get("created").getAsString());

                    bannedIPs.add(ip);
                    sender.sendMessage(ChatColor.YELLOW + "");
                    sender.sendMessage(ChatColor.RED + ip + " Reason: " + reason + " Expires: " + expires + " Created: " + created);
                    sender.sendMessage(ChatColor.YELLOW + "");
                    sender.sendMessage(ChatColor.YELLOW + "==========================================");
                }
                plugin.getLogger().info("Banned IP addresses loaded successfully.");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid format in banned-ips.json file.");
                plugin.getLogger().warning("Invalid format in banned-ips.json file.");
            }
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Failed to load banned-ips.json file.");
            plugin.getLogger().log(Level.SEVERE, "Failed to load banned-ips.json file.", e);
        }

        sender.sendMessage(ChatColor.YELLOW + "==========================================");
        return true;
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
