package com.starkettle.mite_ctm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(MinecraftIsTooEasyCataclysm.MOD_ID)
public class MinecraftIsTooEasyCataclysm {
    public static final String MOD_ID = "mite_ctm";
    private static final Logger LOGGER = LogManager.getLogger("Minecraft Is Too Easy:Cataclysm");

    public MinecraftIsTooEasyCataclysm(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("\n"+
                "  __  __  _                                  __  _     _____        _______              ______                        _____        _                _                        \n" +
                " |  \\/  |(_)                                / _|| |   |_   _|      |__   __|            |  ____|                   _  / ____|      | |              | |                       \n" +
                " | \\  / | _  _ __    ___   ___  _ __  __ _ | |_ | |_    | |   ___     | |  ___    ___   | |__    __ _  ___  _   _ (_)| |      __ _ | |_  __ _   ___ | | _   _  ___  _ __ ___  \n" +
                " | |\\/| || || '_ \\  / _ \\ / __|| '__|/ _` ||  _|| __|   | |  / __|    | | / _ \\  / _ \\  |  __|  / _` |/ __|| | | |   | |     / _` || __|/ _` | / __|| || | | |/ __|| '_ ` _ \\ \n" +
                " | |  | || || | | ||  __/| (__ | |  | (_| || |  | |_   _| |_ \\__ \\    | || (_) || (_) | | |____| (_| |\\__ \\| |_| | _ | |____| (_| || |_| (_| || (__ | || |_| |\\__ \\| | | | | |\n" +
                " |_|  |_||_||_| |_| \\___| \\___||_|   \\__,_||_|   \\__| |_____||___/    |_| \\___/  \\___/  |______|\\__,_||___/ \\__, |(_) \\_____|\\__,_| \\__|\\__,_| \\___||_| \\__, ||___/|_| |_| |_|\n" +
                "                                                                                                             __/ |                                       __/ |                \n" +
                "                                                                                                            |___/                                       |___/                 ");
    }
}
