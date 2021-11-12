package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlusAbilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Abilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Abilities.class)
public class AbilitiesMixin implements VanillaPlusAbilities {

    private boolean canTriggerRaids;

    @Inject(method = "addSaveData", at = @At(value = "RETURN"))
    private void addSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        CompoundTag abilities = compoundTag.getCompound("abilities");
        abilities.putBoolean("canTriggerRaids", canTriggerRaids);
    }

    @Inject(method = "loadSaveData", at = @At(value = "RETURN"))
    private void loadSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        if (!compoundTag.contains("abilities", 10)) {
            return;
        }
        CompoundTag abilities = compoundTag.getCompound("abilities");
        canTriggerRaids = abilities.getBoolean("canTriggerRaids");
    }

    @Override
    public void setCanTriggerRaids(boolean canTriggerRaids) {
        this.canTriggerRaids = canTriggerRaids;
    }

    @Override
    public boolean canTriggerRaids() {
        return canTriggerRaids;
    }
}
