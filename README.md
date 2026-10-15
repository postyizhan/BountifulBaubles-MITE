# BountifulBaubles-MITE

Minecraft 1.6.4 MITE port scaffold built with FishModLoader. The initial integration registers the Curios slots used by Bountiful Baubles and an example right-click equip ring.

## Build

Use Java 17 and run `./gradlew build`. The build expects the Curios-MITE artifact at `../Curios-MITE/build/libs/curios-mite-0.1.0.jar`; build Curios-MITE first when that file is missing. Curios is a required runtime dependency.

The example ring is only an API smoke integration. BB item behavior, assets, recipes, and gameplay effects have not been ported yet.
