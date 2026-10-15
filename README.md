# BountifulBaubles-MITE

Minecraft 1.6.4 MITE port built with FishModLoader. The current integration registers the Curios slots used by Bountiful Baubles and the complete first-pass item ID set from the source mod. Curio items use the native Curios-MITE `ICurioItem` API and can be equipped with a right click.

## Build

Use Java 17 and run `./gradlew build`. The build expects the Curios-MITE artifact at `../Curios-MITE/build/libs/curios-mite-0.1.0.jar`; build Curios-MITE first when that file is missing. Curios is a required runtime dependency.

The item registration and slot tags are in place. Gameplay effects, recipes, loot injection, models, and client rendering are being migrated in focused steps; plain utility items currently retain their item identity without their original Forge-only behavior.
