package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.commands.CommandCenter;
import com.ewyboy.seeddrop.config.SeedConfig;
import com.ewyboy.seeddrop.json.InfoHandler;
import com.ewyboy.seeddrop.json.JSONHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

import static com.ewyboy.seeddrop.SeedDrop.MOD_ID;

@Mod(MOD_ID)
public class SeedDrop {

    public static final String MOD_ID = "seeddrop";

    public SeedDrop() {
        makeServerSide();
        SeedConfig.setup();
        JSONHandler.setup();
        InfoHandler.setup();
        MinecraftForge.EVENT_BUS.addListener(this :: onServerStart);
    }

    //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
    private void makeServerSide() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(
                () -> FMLNetworkConstants.IGNORESERVERONLY,
                (YouCanWriteWhatEverTheFuckYouWantHere, ICreatedSlimeBlocks2YearsBeforeMojangDid) -> true)
        );
    }

    public void onServerStart(FMLServerStartingEvent event) {
        new CommandCenter(event.getServer().getCommands().getDispatcher());
    }

}
