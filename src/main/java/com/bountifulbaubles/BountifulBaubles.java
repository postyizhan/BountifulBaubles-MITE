package com.bountifulbaubles;

import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BountifulBaubles implements ModInitializer {
    public static final String MOD_ID = "bountifulbaubles";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(MOD_ID);
        LOGGER.info("BountifulBaubles-MITE initialized");
    }
}
