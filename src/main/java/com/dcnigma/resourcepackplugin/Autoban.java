package com.dcnigma.resourcepackplugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Autoban implements CommandExecutor, Listener {

    public final Map<UUID, Integer> disconnectCounts = new HashMap<>();
    public final Map<UUID, Integer> lostConnectionCounts = new HashMap<>();
    public int maxDisconnects;
    public int maxLostConnections;
    public String worldName;
    public final ResourcePackPlugin plugin;
    private PrintWriter logWriter;

    public Autoban(ResourcePackPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("autoban").setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        reloadConfig();

        try {
            File logFile = new File(plugin.getDataFolder(), "disconnect_log.txt");
            logWriter = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        // Load values from the plugin's configuration
        maxDisconnects = plugin.getConfig().getInt("autoban.maxDisconnects", 3);
        maxLostConnections = plugin.getConfig().getInt("autoban.maxLostConnections", 10);
        worldName = plugin.getConfig().getString("autoban.worldName", "Lobby");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleDisconnect(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        handleDisconnect(event.getPlayer().getUniqueId());
    }

    private void handleDisconnect(UUID playerId) {
        Player player = Bukkit.getPlayer(playerId);
        if (player == null) {
            // Player not found, it was a lost connection
            handleLostConnection(playerId);
            return;
        }

        int disconnectCount = disconnectCounts.getOrDefault(playerId, 0);
        disconnectCount++;
        disconnectCounts.put(playerId, disconnectCount);
        if (disconnectCount >= maxDisconnects) {
            player.kickPlayer(ChatColor.RED + "You have been automatically §cbanned §ffor §cto many disconnects.");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                    "ban " + player.getName() + " §cto many disconnects");
            InetAddress address = ((InetSocketAddress) player.getAddress()).getAddress();
            Bukkit.getServer().banIP(address.getHostAddress());
            Bukkit.getServer().getBanList(org.bukkit.BanList.Type.IP).addBan(address.getHostAddress(), "§cto many disconnects", null, null);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "§cBanned IP: " + address.getHostAddress());
        }

        logDisconnect(player.getAddress().getAddress().getHostAddress(), disconnectCount);
    }

    private void handleLostConnection(UUID playerId) {
        int lostConnectionCount = lostConnectionCounts.getOrDefault(playerId, 0);
        lostConnectionCount++;
        lostConnectionCounts.put(playerId, lostConnectionCount);
        if (lostConnectionCount >= maxLostConnections) {
            Player player = Bukkit.getPlayer(playerId);
            if (player != null) {
                player.kickPlayer(ChatColor.RED + "You have been automatically §cbanned §ffor excessive lost connections.");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
                        "ban " + player.getName() + " §cto many lost connections");
                InetAddress address = ((InetSocketAddress) player.getAddress()).getAddress();
                Bukkit.getServer().banIP(address.getHostAddress());
                Bukkit.getServer().getBanList(org.bukkit.BanList.Type.IP).addBan(address.getHostAddress(), "§cto many lost connections", null, null);
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Banned IP: " + address.getHostAddress());
            }
        }

        logLostConnection(playerId, lostConnectionCount);
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        // Check if the player has moved to the specified world
        if (player.getLocation().getWorld().getName().equalsIgnoreCase(worldName)) {
            UUID playerId = player.getUniqueId();

            // Reset the disconnect count for the player after 20 ticks (1 second)
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                disconnectCounts.put(playerId, 0); // Reset the disconnect count for the player to 0
                lostConnectionCounts.remove(playerId); // Reset the lost connection count for the player
            }, 20);
        }
    }
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event) {
//        Player player = event.getPlayer();
//        // Check if the player has moved to the specified world
//        if (player.getLocation().getWorld().getName().equalsIgnoreCase(worldName)) {
//            UUID playerId = player.getUniqueId();
//            // Reset the disconnect count for the player after 20 ticks (1 second)
//            Bukkit.getScheduler().runTaskLater(plugin, () -> {
//            disconnectCounts.remove(playerId); // Reset the disconnect count for the player
//            lostConnectionCounts.remove(playerId); // Reset the lost connection count for the player
//        }, 20);
//    }
//}

    private void logDisconnect(String ipAddress, int disconnectCount) {
        if (logWriter != null) {
            logWriter.println(ipAddress + "," + disconnectCount);
            logWriter.flush();
        }
    }

    private void logLostConnection(UUID playerId, int lostConnectionCount) {
        if (logWriter != null) {
            Player player = Bukkit.getPlayer(playerId);
            String playerName = player != null ? player.getName() : "Unknown";
            logWriter.println("Lost Connection: " + playerName + "," + lostConnectionCount);
            logWriter.flush();
        }
    }

    public void displayHelp(CommandSender sender) {
        HelpManager.displayHelp(sender);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPluginYml(); // Call the reloadPluginYml method from your ResourcePackPlugin class
            reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Reloaded autoban configuration.");
            return true;
        } else {
            displayHelp(sender);
            return true;
        }
    }

    public void onDisable() {
        if (logWriter != null) {
            logWriter.close();
        }
    }
}
