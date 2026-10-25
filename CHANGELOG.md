**v1.21.1 - 2.4.0**
* Ported to 1.21.1 for NeoForge and Fabric
* Existing `SeedDrops.json` configs and the `/seeddrop` commands work unchanged
* Short grass drops seeds again (the block was renamed from `grass` in 1.20.3)
* Shears are detected with the common `c:tools/shear` tag
* #13: Seed Drop only swaps the vanilla wheat seeds and adds its own drops, so drops other mods add to grass (like Farmer's Delight straw) are kept. Covered by game tests now
* Unknown or misspelled items in `SeedDrops.json` are skipped, they used to stop every entry after them from dropping
* Removed the empty `SeedDrop.toml` server config, it had no settings

**v1.20.x - 2.3.0**
* PR #14 by [gatekeep06](https://github.com/gatekeep06): fix grass drop modifier

**v1.20.x - 2.2.0**  
* Ported to 1.20 & 1.20.1
