package de.maxhenkel.vanillaplus.mixin;

import de.maxhenkel.vanillaplus.VanillaPlus;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void setPos(double x, double y, double z) {
        if (!isInvulnerable()) {
            super.setPos(x, y, z);
            return;
        }
        if (!VanillaPlus.SERVER_CONFIG.protectDeathItems.get()) {
            super.setPos(x, y, z);
            return;
        }

        int minY = level().getMinBuildHeight();
        if (y < minY + 1D) {
            y = minY + 1D;
            setNoGravity(true);
            Vec3 d = getDeltaMovement();
            setDeltaMovement(d.x, 0D, d.z);
        } else {
            setNoGravity(false);
        }
        super.setPos(x, y, z);
    }
}
