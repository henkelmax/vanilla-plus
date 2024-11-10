package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Inventory.class)
public class InventoryMixin {

    @Redirect(method = "dropAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private ItemEntity drop(Player instance, ItemStack itemStack, boolean bl, boolean bl2) {
        ItemEntity dropped = instance.drop(itemStack, bl, bl2);
        if (!VanillaPlus.SERVER_CONFIG.protectDeathItems.get()) {
            return dropped;
        }
        if (dropped == null) {
            return null;
        }
        dropped.setInvulnerable(true);
        dropped.setUnlimitedLifetime();
        return dropped;
    }

}
