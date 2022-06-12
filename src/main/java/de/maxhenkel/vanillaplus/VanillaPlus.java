package de.maxhenkel.vanillaplus;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.vanillaplus.command.VanillaPlusCommands;
import de.maxhenkel.vanillaplus.config.ServerConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanillaPlus implements ModInitializer {

    public static final String MODID = "vanillaplus";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static ServerConfig SERVER_CONFIG;

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(VanillaPlusCommands::register);
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            SERVER_CONFIG = ConfigBuilder.build(server.getServerDirectory().toPath().resolve("config").resolve(MODID).resolve("vanillaplus-server.properties"), ServerConfig::new);
        });
    }
}
