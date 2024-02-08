package de.maxhenkel.vanillaplus.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.vanillaplus.VanillaPlusAbilities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

@Command("vanillaplus")
public class VanillaPlusCommands {

    @Command("raids")
    public void raids(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        if (((VanillaPlusAbilities) player.getAbilities()).canTriggerRaids()) {
            context.getSource().sendSuccess(() -> Component.literal("Raids are enabled for ").append(player.getDisplayName()), false);
        } else {
            context.getSource().sendSuccess(() -> Component.literal("Raids are disabled for ").append(player.getDisplayName()), false);
        }
    }

    @Command("raids")
    public void raids(CommandContext<CommandSourceStack> context, @Name("enabled") boolean enabled) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ((VanillaPlusAbilities) player.getAbilities()).setCanTriggerRaids(enabled);
        if (enabled) {
            context.getSource().sendSuccess(() -> Component.literal("Successfully enabled raids for ").append(player.getDisplayName()), false);
        } else {
            context.getSource().sendSuccess(() -> Component.literal("Successfully disabled raids for ").append(player.getDisplayName()), false);
        }
    }

}
