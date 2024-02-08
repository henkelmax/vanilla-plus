package de.maxhenkel.vanillaplus.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.MinMax;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.vanillaplus.InteractionManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

@Command("interaction")
public class InteractionCommands {

    @Command("attack")
    public void attack(CommandContext<CommandSourceStack> context, @Name("interval") @MinMax(min = "1", max = "200") Optional<Integer> interval) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        InteractionManager.startAttacking(player, interval.orElse(20));
    }

    @Command({"attack", "stop"})
    public void stopAttack(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        InteractionManager.stopAttacking(player);
    }

}
