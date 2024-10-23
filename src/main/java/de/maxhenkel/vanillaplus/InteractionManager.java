package de.maxhenkel.vanillaplus;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class InteractionManager {

    private static final Map<ServerPlayer, Integer> attackingPlayers = new WeakHashMap<>();

    public static void init() {
        ServerTickEvents.START_SERVER_TICK.register(InteractionManager::tick);
    }

    private static void tick(MinecraftServer server) {
        attackingPlayers.keySet().removeIf(serverPlayer -> !serverPlayer.isAlive() || serverPlayer.hasDisconnected());
        for (Map.Entry<ServerPlayer, Integer> entry : attackingPlayers.entrySet()) {
            handlePlayerAttack(server, entry.getKey(), entry.getValue());
        }
    }

    public static void startAttacking(ServerPlayer player, int interval) {
        attackingPlayers.put(player, interval);
    }

    public static void stopAttacking(ServerPlayer player) {
        attackingPlayers.remove(player);
    }

    private static void handlePlayerAttack(MinecraftServer server, ServerPlayer player, int interval) {
        if (server.getTickCount() % interval != 0) {
            return;
        }
        if (!player.isAlive()) {
            return;
        }
        lookingAt(player).ifPresent(entity -> {
            player.connection.handleInteract(ServerboundInteractPacket.createAttackPacket(entity, false));
        });
    }

    private static Optional<Entity> lookingAt(ServerPlayer player) {
        double reachDistance = player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
        return player.level().getEntities(player, player.getBoundingBox().inflate(reachDistance)).stream().filter(entity -> intersectsLook(player, entity, reachDistance)).min(Comparator.comparingDouble(o -> o.distanceTo(player)));
    }

    private static boolean intersectsLook(ServerPlayer player, Entity entity, double reachDistance) {
        Vec3 lookPos = player.getEyePosition();
        Vec3 lookDir = Vec3.directionFromRotation(player.getRotationVector()).normalize().scale(reachDistance);
        return doesIntersect(lookPos, lookDir, entity.getBoundingBox());
    }

    private static boolean doesIntersect(Vec3 position, Vec3 direction, AABB aabb) {
        if (position.distanceTo(aabb.getCenter()) > direction.length()) {
            return false;
        }

        double t1 = (aabb.minX - position.x) / direction.x;
        double t2 = (aabb.maxX - position.x) / direction.x;
        double t3 = (aabb.minY - position.y) / direction.y;
        double t4 = (aabb.maxY - position.y) / direction.y;
        double t5 = (aabb.minZ - position.z) / direction.z;
        double t6 = (aabb.maxZ - position.z) / direction.z;

        double tMin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        double tMax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

        return tMax >= 0 && tMin <= tMax;
    }

}
