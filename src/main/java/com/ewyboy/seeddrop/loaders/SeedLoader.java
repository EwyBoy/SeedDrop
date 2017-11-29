package com.ewyboy.seeddrop.loaders;

import com.ewyboy.seeddrop.SeedDrop;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by EwyBoy
 **/
public class SeedLoader {
    public static void registerSeeds() {

        // Short-circuit if the number of entries is not even.
        if ((ConfigLoader.customDrops.length & 1) != 0) {
            SeedDrop.LOGGER.error("Mismatched number of entries, should be paired. Please see config file for format.");
            return;
        }

        for (int i = 0; i < ConfigLoader.customDrops.length; i += 2) {

            String configStringItem = ConfigLoader.customDrops[i];
            String[] splitStringItem = configStringItem.split(":");

            // Check that the split string array has either two or three elements.
            if (splitStringItem.length < 2
                    || splitStringItem.length > 3) {
                SeedDrop.LOGGER.error("Invalid item string syntax: '" + configStringItem
                        + "', should be '<domain>:<id>', or '<domain>:<id>:<meta>'");
                continue;
            }

            int meta = 0;

            if (splitStringItem.length == 3) {
                // A meta value was supplied, try to parse it and log an error if we can't.

                try {
                    meta = Integer.parseInt(splitStringItem[2]);

                } catch (NumberFormatException e) {
                    SeedDrop.LOGGER.error("Invalid item meta value: " + splitStringItem[2]);
                    continue;
                }
            }

            Item item = Item.getByNameOrId(splitStringItem[0] + ":" + splitStringItem[1]);

            // Log an error if the item was not found.
            if (item == null) {
                SeedDrop.LOGGER.error("Unable to locate item: " + splitStringItem[0] + ":" + splitStringItem[1]);
                continue;
            }

            String configStringWeight = ConfigLoader.customDrops[i + 1];

            try {
                int weight = Integer.parseInt(configStringWeight);
                MinecraftForge.addGrassSeed(new ItemStack(item, 1, meta), weight);

            } catch (NumberFormatException e) {
                SeedDrop.LOGGER.error("Invalid weight value: " + configStringWeight);
            }
        }
    }
}
