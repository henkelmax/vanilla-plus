package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FarmlandBlock.class)
public class FarmBlockMixin {

    @Redirect(method = "fallOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/FarmlandBlock;turnToDirt(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"))
    private void fallOn(@Nullable Entity sourceEntity, BlockState state, Level level, BlockPos pos) {
        if (VanillaPlus.SERVER_CONFIG.cropTrampling.get()) {
            FarmlandBlock.turnToDirt(sourceEntity, state, level, pos);
        }
    }

}
