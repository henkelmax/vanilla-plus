package de.maxhenkel.vanillaplus;

import de.maxhenkel.admiral.MinecraftAdmiral;
import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.vanillaplus.command.VanillaPlusCommands;
import de.maxhenkel.vanillaplus.config.ServerConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VanillaPlus implements ModInitializer {

    public static final String MODID = "vanillaplus";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static ServerConfig SERVER_CONFIG;

    @Override
    public void onInitialize() {
        Path configFolder = Paths.get(".", "config").resolve(MODID);
        SERVER_CONFIG = ConfigBuilder.builder(ServerConfig::new).path(configFolder.resolve("vanillaplus-server.properties")).build();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            MinecraftAdmiral.builder(dispatcher, registryAccess)
                    .addCommandClasses(VanillaPlusCommands.class)
                    .build();
        });
    }
}
