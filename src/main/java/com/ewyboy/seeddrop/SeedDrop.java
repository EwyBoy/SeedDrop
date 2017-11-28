package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.loaders.ConfigLoader;
import com.ewyboy.seeddrop.loaders.SeedLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static com.ewyboy.seeddrop.SeedDrop.MOD_ID;
import static com.ewyboy.seeddrop.SeedDrop.MOD_NAME;

@Mod(modid = MOD_ID, name = MOD_NAME, acceptableRemoteVersions = "*")
public class SeedDrop {

    public static final String MOD_ID = "seeddrop";
    public static final String MOD_NAME = "SeedDrop";

    public static Logger LOGGER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        ConfigLoader.init(new File("config/" , MOD_NAME + ".cfg"));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        SeedLoader.registerSeeds();
    }
}
