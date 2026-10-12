package io.github.mooy1.infinitylib.commands;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun5.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun5.core.services.localization.AddonMessageService;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;

@ParametersAreNonnullByDefault
final class InfoCommand extends SubCommand {

    private final SlimefunAddon addon;
    private final String slimefunVersion;

    InfoCommand(SlimefunAddon addon) {
        super("info", "commands.info.description", "Gives addon and slimefun version and discord links", false);
        this.addon = addon;
        Slimefun slimefun = Slimefun.instance();
        this.slimefunVersion = slimefun == null ? "null" : slimefun.getPluginVersion();
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        AddonMessageService messages = Slimefun.getAddonMessageService();
        JavaPlugin plugin = addon.getJavaPlugin();

        sender.sendMessage("");
        messages.sendMessage(sender, plugin, "commands.info.title",
                "&b%addon% Info", line -> line.replace("%addon%", addon.getName()));
        messages.sendMessage(sender, plugin, "commands.info.slimefun-version",
                "&bSlimefun Version: &7%version%", line -> line.replace("%version%", slimefunVersion));
        messages.sendMessage(sender, plugin, "commands.info.slimefun-discord",
                "&bSlimefun Discord: &7discord.gg/CbBYZBEWdR");
        messages.sendMessage(sender, plugin, "commands.info.addon-version",
                "&bAddon Version: &7%version%", line -> line.replace("%version%", addon.getPluginVersion()));
        messages.sendMessage(sender, plugin, "commands.info.addon-community",
                "&bAddon Community: &7discord.gg/CbBYZBEWdR");
        messages.sendMessage(sender, plugin, "commands.info.github",
                "&bGithub: &7%url%", line -> line.replace("%url%", addon.getBugTrackerURL()));
        sender.sendMessage("");
    }

    @Override
    protected void complete(CommandSender sender, String[] args, List<String> tabs) {

    }

}

