package de.maxhenkel.vanillaplus.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;

public class ServerConfig {

    public final ConfigEntry<Boolean> leashVillagers;
    public final ConfigEntry<Boolean> phantomAttacks;
    public final ConfigEntry<Boolean> creeperBlockDamage;
    public final ConfigEntry<Boolean> endermanPickUpBlocks;
    public final ConfigEntry<Boolean> cropTrampling;
    public final ConfigEntry<Boolean> controlledRaids;
    public final ConfigEntry<Boolean> newElytraDurability;
    public final ConfigEntry<Boolean> interactionCommands;

    public ServerConfig(ConfigBuilder builder) {
        leashVillagers = builder.booleanEntry("leash_villagers", true);
        phantomAttacks = builder.booleanEntry("phantom_attacks", false);
        creeperBlockDamage = builder.booleanEntry("creeper_block_damage", false);
        endermanPickUpBlocks = builder.booleanEntry("enderman_pick_up_blocks", false);
        cropTrampling = builder.booleanEntry("crop_trampling", false);
        controlledRaids = builder.booleanEntry("controlled_raids", true);
        newElytraDurability = builder.booleanEntry("new_elytra_durability", false);
        interactionCommands = builder.booleanEntry("interaction_commands", false);
    }

}
