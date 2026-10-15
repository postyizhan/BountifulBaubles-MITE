package com.bountifulbaubles.item;

import net.minecraft.Item;
import net.minecraft.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public final class ExampleRing extends Item implements ICurioItem {
    public ExampleRing() {
        setMaxStackSize(1);
    }

    @Override
    public ICurio getCurio(ItemStack stack) {
        return new ICurio() {
            @Override
            public boolean canRightClickEquip() {
                return true;
            }
        };
    }
}
