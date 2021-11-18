package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Redirect(method = "updateFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private <T extends LivingEntity> void updateFallFlying(ItemStack stack, int durability, T entity, Consumer<T> onBreak) {
        if (!VanillaPlus.SERVER_CONFIG.newElytraDurability.get()) {
            stack.hurtAndBreak(durability, entity, onBreak);
        }
    }

}
