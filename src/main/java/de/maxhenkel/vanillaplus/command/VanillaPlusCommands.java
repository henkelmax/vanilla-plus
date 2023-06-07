package de.maxhenkel.vanillaplus.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.maxhenkel.vanillaplus.VanillaPlusAbilities;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class VanillaPlusCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        LiteralArgumentBuilder<CommandSourceStack> literalBuilder = Commands.literal("vanillaplus");

        literalBuilder.then(Commands.literal("raids").then(Commands.argument("enabled", BoolArgumentType.bool()).executes((context) -> {
            ServerPlayer player = context.getSource().getPlayerOrException();
            boolean enabled = BoolArgumentType.getBool(context, "enabled");
            ((VanillaPlusAbilities) player.getAbilities()).setCanTriggerRaids(enabled);
            if (enabled) {
                context.getSource().sendSuccess(() -> Component.literal("Successfully enabled raids for ").append(player.getDisplayName()), false);
            } else {
                context.getSource().sendSuccess(() -> Component.literal("Successfully disabled raids for ").append(player.getDisplayName()), false);
            }
            return 1;
        })));

        literalBuilder.then(Commands.literal("raids").executes((context) -> {
            ServerPlayer player = context.getSource().getPlayerOrException();
            if (((VanillaPlusAbilities) player.getAbilities()).canTriggerRaids()) {
                context.getSource().sendSuccess(() -> Component.literal("Raids are enabled for ").append(player.getDisplayName()), false);
            } else {
                context.getSource().sendSuccess(() -> Component.literal("Raids are disabled for ").append(player.getDisplayName()), false);
            }
            return 1;
        }));

        dispatcher.register(literalBuilder);
    }

}
