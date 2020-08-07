//
// Entry point for iSpy Spigot Minecraft plugin
// Copyright 2020 Jericho Crosby <jericho.crosby227@gmail.com>
//

package jericraft.chalwk;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ispy extends JavaPlugin {
    protected ispyFiles logger = null;
    public HashMap<Player, Integer> iSpyEnabled = new HashMap();

    public void onEnable() {
        this.logger = new ispyFiles();
        this.getServer().getPluginManager().registerEvents(new ispyPlayers(this), this);
        new ispyPlayers(this);
        this.saveDefaultConfig();

        final String enable = this.getConfig().getString("OnPluginEnable");
        System.out.println(enable);
    }

    public void onDisable() {
        final String disable = this.getConfig().getString("OnPluginDisable");
        System.out.println(disable);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("ispy")) {
            if (sender instanceof Player) {
                String prefix = this.getConfig().getString("Prefix");
                if (sender.hasPermission("ispy.cmds")) {
                    if (args.length >= 1) {

                        if (args[0].equalsIgnoreCase("cmd")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m==========================="));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("HelpCommandsLabel")));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m==========================="));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("HelpInfo")));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("HelpCmd")));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("HelpVersion")));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("HelpReload")));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("HelpAllow")));
                        } else if (args[0].equalsIgnoreCase("version")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m==========================="));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&liSpy &8/ &7Version: &a" + this.getDescription().getVersion()));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m==========================="));
                        } else if (args[0].equalsIgnoreCase("reload")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + this.getConfig().getString("ReloadPlugin")));
                            this.reloadConfig();

                        } else if (args[0].equalsIgnoreCase("enable")) {

                            // Check if iSpy is already enabled:
                            if (this.iSpyEnabled.containsKey(sender)) {
                                String msg = this.getConfig().getString("alreadyEnabled");
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s%s", prefix, msg)));

                            } else {
                                // Enable iSpy:
                                this.iSpyEnabled.remove(sender);
                                String msg = this.getConfig().getString("MessagesEnabled");
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s%s", prefix, msg)));
                            }
                        } else if (args[0].equalsIgnoreCase("disable")) {

                            // Check if iSpy is already disabled:
                            if (this.iSpyEnabled.containsKey(sender)) {
                                String msg = this.getConfig().getString("alreadyDisabled");
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s%s", prefix, msg)));

                            } else {
                                // Disable iSpy:
                                this.iSpyEnabled.put((Player) sender, null);
                                String msg = this.getConfig().getString("MessagesDisabled");
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s%s", prefix, msg)));
                            }

                        } else {
                            args[0].equalsIgnoreCase("signs");
                        }

                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m==========================="));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&liSpy &8/ &7Version: &a" + this.getDescription().getVersion()));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m==========================="));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("HelpCommands")));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + this.getConfig().getString("DontHavePerm")));
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
