package com.bountifulbaubles.registry;

import com.bountifulbaubles.BountifulBaubles;
import com.bountifulbaubles.item.ExampleRing;
import huix.glacier.api.entrypoint.IGameRegistry;
import huix.glacier.api.registry.MinecraftRegistry;
import net.minecraft.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;

public final class BountifulBaublesRegistry implements IGameRegistry {
    private static final MinecraftRegistry REGISTRY = new MinecraftRegistry(BountifulBaubles.MOD_ID)
        .initAutoItemRegister();

    public static Item EXAMPLE_RING;

    @Override
    public void onGameRegistry() {
        registerSlots();
        EXAMPLE_RING = REGISTRY.registerItem(
            BountifulBaubles.MOD_ID + ":example_ring", "exampleRing", new ExampleRing()
        );
    }

    private static void registerSlots() {
        for (SlotTypePreset preset : new SlotTypePreset[] {
            SlotTypePreset.HEAD, SlotTypePreset.NECKLACE, SlotTypePreset.BACK,
            SlotTypePreset.BODY, SlotTypePreset.HANDS, SlotTypePreset.CHARM
        }) {
            CuriosApi.registerSlotType(preset.getMessageBuilder().build());
        }
        CuriosApi.registerSlotType(SlotTypePreset.RING.getMessageBuilder().size(2).build());
    }
}
