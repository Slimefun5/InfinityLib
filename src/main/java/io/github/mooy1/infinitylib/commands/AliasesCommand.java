package io.github.mooy1.infinitylib.commands;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;

@ParametersAreNonnullByDefault
final class AliasesCommand extends SubCommand {

    private final String cmdName;
    private final String aliases;

    AliasesCommand(PluginCommand command) {
        super("aliases", "commands.aliases.description", "lists the available aliases for this command", false);
        this.cmdName = command.getName();
        this.aliases = command.getAliases().toString();
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        JavaPlugin plugin = AbstractAddon.instance();
        Slimefun.getAddonMessageService().sendMessage(sender, plugin, "commands.aliases.message",
                "Aliases for /%cmd%: %aliases%",
                line -> line.replace("%cmd%", cmdName).replace("%aliases%", aliases));
    }

    @Override
    protected void complete(CommandSender sender, String[] args, List<String> completions) {

    }

}
