# BountifulBaubles-MITE

Minecraft 1.6.4 MITE port built with FishModLoader. The current integration registers the Curios slots used by Bountiful Baubles and the complete first-pass item ID set from the source mod. Curio items use the native Curios-MITE `ICurioItem` API and can be equipped with a right click.

All 36 registered items are available in the creative inventory's Miscellaneous tab and search, with English and Simplified Chinese names.

Shields use their original cuboid models in the inventory, hand, and as dropped items. Right click equips a shield into an available Curios slot; when all compatible slots are occupied, holding right click blocks. A compatibility mixin converts Curios attribute keys to MITE attribute names when equipping, removing, and loading accessories.

## Build

Use Java 17 and run `./gradlew build`. The build expects the Curios-MITE artifact at `../Curios-MITE/build/libs/curios-mite-0.1.0.jar`; build Curios-MITE first when that file is missing. Curios is a required runtime dependency.

The item registration and slot tags are in place. Gameplay effects, recipes, loot injection, models, and client rendering are being migrated in focused steps; plain utility items currently retain their item identity without their original Forge-only behavior.
