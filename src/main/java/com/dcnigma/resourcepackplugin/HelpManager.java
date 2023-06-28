package com.dcnigma.resourcepackplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpManager {

    public static void displayHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "==== ResourcePackPlugin Help =======================");
        sender.sendMessage(ChatColor.YELLOW + "by dcnigma");
        sender.sendMessage(ChatColor.YELLOW + "==== Autoban Minecraft server checkers =============");
        sender.sendMessage(ChatColor.RED + "The Commands:");
        sender.sendMessage(ChatColor.YELLOW + "");
        sender.sendMessage(ChatColor.YELLOW + "/ResourcePackPlugin reload - Reload the plugin configuration");
        sender.sendMessage(ChatColor.YELLOW + "/ResourcePackPlugin Help - Display this help message");
        sender.sendMessage(ChatColor.YELLOW + "");
        sender.sendMessage(ChatColor.YELLOW + "");
        sender.sendMessage(ChatColor.RED + "/loadpack1 - Loads ResourcePack Url in Plugin.xml file");
        sender.sendMessage(ChatColor.RED + "/loadpack2 - Loads ResourcePack Url in Plugin.xml file");
        sender.sendMessage(ChatColor.RED + "/loadpack3 - Loads ResourcePack Url in Plugin.xml file");
        sender.sendMessage(ChatColor.RED + "/loadpack4 - Loads ResourcePack Url in Plugin.xml file");
        sender.sendMessage(ChatColor.RED + "/loadpack5 - Loads ResourcePack Url in Plugin.xml file");
        sender.sendMessage(ChatColor.YELLOW + "");
        sender.sendMessage(ChatColor.YELLOW + "");
        sender.sendMessage(ChatColor.RED + "/bannedips - Display the banned ips from banned-ips.json");
        sender.sendMessage(ChatColor.RED + "/Autoban reload - Reload Max Disconnects in config.xml");
        sender.sendMessage(ChatColor.YELLOW + "");
        sender.sendMessage(ChatColor.YELLOW + "====================================================");
    }
}
