package com.dcnigma.resourcepackplugin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class BannedIPManager {

    public static void loadBannedIPs(ResourcePackPlugin plugin) {
        plugin.getLogger().info("Loading banned-ips.json file...");

        File bannedIPFile = new File(plugin.getDataFolder(), "banned-ips.json");

        if (!bannedIPFile.exists()) {
            plugin.getLogger().warning("The banned-ips.json file does not exist.");
            return;
        }

        try (FileReader reader = new FileReader(bannedIPFile)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                Gson gson = new Gson();
                for (JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    String ip = jsonObject.get("ip").getAsString();
                    String reason = jsonObject.get("reason").getAsString();
                    String expires = jsonObject.get("expires").getAsString();
                    Date created = parseDate(jsonObject.get("created").getAsString());

                    // Add the banned IP to your plugin's data structure or perform any other necessary actions
                    plugin.getBannedIPs().add(ip);

                    plugin.getLogger().info("Banned IP loaded: " + ip + " Reason: " + reason + " Expires: " + expires + " Created: " + created);
                }
                plugin.getLogger().info("Banned IP addresses loaded successfully.");
            } else {
                plugin.getLogger().warning("Invalid format in banned-ips.json file.");
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load banned-ips.json file.", e);
        }
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
