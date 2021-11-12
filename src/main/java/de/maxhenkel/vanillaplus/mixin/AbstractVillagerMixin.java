package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.minecraft.world.entity.npc.AbstractVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin {

    @Inject(method = "canBeLeashed", at = @At(value = "HEAD"), cancellable = true)
    private void canBeLeashed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(VanillaPlus.SERVER_CONFIG.leashVillagers.get());
    }

}
