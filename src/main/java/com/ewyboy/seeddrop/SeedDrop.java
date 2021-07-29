package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.commands.CommandCenter;
import com.ewyboy.seeddrop.config.SeedConfig;
import com.ewyboy.seeddrop.json.InfoHandler;
import com.ewyboy.seeddrop.json.JSONHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import static com.ewyboy.seeddrop.SeedDrop.MOD_ID;

@Mod(MOD_ID)
public class SeedDrop {

    public static final String MOD_ID = "seeddrop";

    public SeedDrop() {
        ignoreServerOnly();
        SeedConfig.setup();
        JSONHandler.setup();
        InfoHandler.setup();
        MinecraftForge.EVENT_BUS.addListener(this :: registerCommands);
    }

    //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
    private void ignoreServerOnly() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () ->
                new IExtensionPoint.DisplayTest(() -> "You Can Write Whatever The Fuck You Want Here", (YouCanWriteWhatEverTheFuckYouWantHere, ICreatedSlimeBlocks2YearsBeforeMojangDid) -> ICreatedSlimeBlocks2YearsBeforeMojangDid)
        );
    }

    public void registerCommands(RegisterCommandsEvent event) {
        new CommandCenter(event.getDispatcher());
    }

}
