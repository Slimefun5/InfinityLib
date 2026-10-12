package io.github.mooy1.infinitylib.commands;

import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun5.core.services.localization.AddonMessageService;
import io.github.thebusybiscuit.slimefun5.implementation.Slimefun;

@ParametersAreNonnullByDefault
final class HelpCommand extends SubCommand {

    private final ParentCommand command;

    HelpCommand(ParentCommand command) {
        super("help", "commands.help.description", "Displays this", false);
        this.command = command;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        JavaPlugin addon = AbstractAddon.instance();
        AddonMessageService messages = Slimefun.getAddonMessageService();
        Player viewer = sender instanceof Player ? (Player) sender : null;

        sender.sendMessage("");
        messages.sendMessage(sender, addon, "commands.help.header",
                "&7----------&b /%cmd% Help &7----------",
                line -> line.replace("%cmd%", command.fullName()));
        sender.sendMessage("");

        for (SubCommand sub : command.available(sender)) {
            String description = resolveDescription(messages, viewer, addon, sub);
            messages.sendMessage(sender, addon, "commands.help.line",
                    "/%sub%&e - %description%",
                    line -> line.replace("%sub%", sub.fullName()).replace("%description%", description));
        }

        sender.sendMessage("");
    }

    private static String resolveDescription(AddonMessageService messages, @Nullable Player viewer, JavaPlugin addon, SubCommand sub) {
        String key = sub.descriptionKey();
        return key == null ? sub.description() : messages.getMessage(viewer, addon, key, sub.description());
    }

    @Override
    public void complete(CommandSender sender, String[] args, List<String> tabs) {

    }

}
