package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Fireworks;
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

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/FireworkRocketEntity;<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V"), cancellable = true)
    private void injected(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> ci) {
        if (!VanillaPlus.SERVER_CONFIG.newElytraDurability.get()) {
            return;
        }

        ItemStack item = player.getItemInHand(interactionHand);
        if (!(item.getItem() instanceof FireworkRocketItem)) {
            return;
        }

        int amount = 1;

        Fireworks fireworksComponent = item.get(DataComponents.FIREWORKS);
        if (fireworksComponent != null) {
            amount = fireworksComponent.flightDuration();
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
        elytra.hurtAndBreak(maxDamage, player, EquipmentSlot.CHEST);
    }

}
