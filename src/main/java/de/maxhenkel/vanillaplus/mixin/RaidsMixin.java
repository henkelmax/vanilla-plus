package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import de.maxhenkel.vanillaplus.VanillaPlusAbilities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Raids.class)
public class RaidsMixin {

    @Inject(method = "createOrExtendRaid", at = @At(value = "HEAD"), cancellable = true)
    private void createOrExtendRaid(ServerPlayer serverPlayer, CallbackInfoReturnable<Raid> cir) {
        if (!VanillaPlus.SERVER_CONFIG.controlledRaids.get()) {
            return;
        }
        if (((VanillaPlusAbilities) serverPlayer.getAbilities()).canTriggerRaids()) {
            return;
        }
        cir.setReturnValue(null);
    }

}
