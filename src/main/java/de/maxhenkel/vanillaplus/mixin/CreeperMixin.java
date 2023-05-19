package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Creeper.class)
public class CreeperMixin {

    @Redirect(method = "explodeCreeper", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;", ordinal = 0))
    private Explosion explodeCreeper(Level level, @Nullable Entity entity, double x, double y, double z, float strength, Level.ExplosionInteraction explosionInteraction) {
        if (VanillaPlus.SERVER_CONFIG.creeperBlockDamage.get()) {
            return level.explode(entity, x, y, z, strength, explosionInteraction);
        } else {
            return level.explode(entity, x, y, z, strength, Level.ExplosionInteraction.NONE);
        }
    }

}
