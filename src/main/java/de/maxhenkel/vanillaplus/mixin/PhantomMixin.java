package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.Phantom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Phantom.class)
public class PhantomMixin {

    @Redirect(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 0))
    private void registerGoals0(GoalSelector goalSelector, int priority, Goal goal) {
        if (VanillaPlus.SERVER_CONFIG.phantomAttacks.get()) {
            goalSelector.addGoal(priority, goal);
        }
    }

    @Redirect(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 1))
    private void registerGoals1(GoalSelector goalSelector, int priority, Goal goal) {
        if (VanillaPlus.SERVER_CONFIG.phantomAttacks.get()) {
            goalSelector.addGoal(priority, goal);
        }
    }

}
