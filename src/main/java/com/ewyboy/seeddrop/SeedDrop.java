package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.loaders.ConfigLoader;
import com.ewyboy.seeddrop.loaders.SeedLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

import static com.ewyboy.seeddrop.reference.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = BUILD_VERSION)
public class SeedDrop {
    
    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        ConfigLoader.init(new File("config/" , MOD_NAME + ".cfg"));
        SeedLoader.registerSeeds();
    }
}
