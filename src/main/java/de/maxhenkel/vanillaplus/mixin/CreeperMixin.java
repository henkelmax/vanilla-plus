package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Creeper.class)
public class CreeperMixin {

    @Redirect(method = "explodeCreeper", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)V"))
    private void explodeCreeper(ServerLevel instance, Entity entity, double x, double y, double z, float strength, Level.ExplosionInteraction explosionInteraction) {
        if (VanillaPlus.SERVER_CONFIG.creeperBlockDamage.get()) {
            instance.explode(entity, x, y, z, strength, explosionInteraction);
        } else {
            instance.explode(entity, x, y, z, strength, Level.ExplosionInteraction.NONE);
        }
    }

}
