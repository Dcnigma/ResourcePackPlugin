package com.dcnigma.resourcepackplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ResourcePackCommand implements CommandExecutor {

    public final List<String> resourcePackUrls;

    public ResourcePackCommand(List<String> resourcePackUrls) {
        this.resourcePackUrls = resourcePackUrls;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (resourcePackUrls != null && resourcePackUrls.size() > 0) {
                for (String resourcePackUrl : resourcePackUrls) {
                    player.setResourcePack(resourcePackUrl);
                    player.sendMessage(ChatColor.GREEN + "Resource pack loading: " + resourcePackUrl);
                }
            } else {
                player.sendMessage("Resource pack URLs are not specified for this command.");
            }
        } else {
            sender.sendMessage("This command can only be executed by a player.");
        }
        return true;
    }
}
