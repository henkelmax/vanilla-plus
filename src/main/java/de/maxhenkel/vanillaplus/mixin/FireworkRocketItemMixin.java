package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin extends Item {

    private static final int MIN_DURABILITY = 2;

    public FireworkRocketItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/FireworkRocketEntity;<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V"))
    private void injected(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> ci) {
        if (!VanillaPlus.SERVER_CONFIG.newElytraDurability.get()) {
            return;
        }

        ItemStack item = player.getItemInHand(interactionHand);
        if (!(item.getItem() instanceof FireworkRocketItem)) {
            return;
        }

        int amount = 1;

        CompoundTag tag = item.getTag();
        if (tag != null && tag.contains(FireworkRocketItem.TAG_FIREWORKS, NbtType.COMPOUND)) {
            CompoundTag fireworks = tag.getCompound(FireworkRocketItem.TAG_FIREWORKS);
            if (fireworks.contains(FireworkRocketItem.TAG_FLIGHT, NbtType.NUMBER)) {
                amount = fireworks.getByte(FireworkRocketItem.TAG_FLIGHT);
            }
        }

        ItemStack elytra = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!(elytra.getItem() instanceof ElytraItem)) {
            return;
        }

        int durability = elytra.getMaxDamage() - elytra.getDamageValue();

        if (durability <= MIN_DURABILITY) {
            ci.setReturnValue(InteractionResultHolder.pass(player.getItemInHand(interactionHand)));
            return;
        }

        int maxDamage = Math.min(amount, durability - MIN_DURABILITY);
        elytra.hurtAndBreak(maxDamage, player, e -> e.broadcastBreakEvent(EquipmentSlot.CHEST));
    }

}
