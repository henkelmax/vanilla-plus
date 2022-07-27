package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import de.maxhenkel.vanillaplus.VanillaPlusAbilities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net/minecraft/world/effect/MobEffects$1")
public class BadOmenMobEffectMixin {

    @Redirect(method = "applyEffectTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/raid/Raids;createOrExtendRaid(Lnet/minecraft/server/level/ServerPlayer;)Lnet/minecraft/world/entity/raid/Raid;"))
    private Raid registerGoals(Raids raids, ServerPlayer player) {
        if (!VanillaPlus.SERVER_CONFIG.controlledRaids.get()) {
            return raids.createOrExtendRaid(player);
        }
        if (((VanillaPlusAbilities) player.getAbilities()).canTriggerRaids()) {
            return raids.createOrExtendRaid(player);
        } else {
            return null;
        }
    }

}
