package com.bountifulbaubles.item;

import net.minecraft.Item;

/** Base for BB items that are not Curios accessories. */
public class BBItem extends Item {
    public BBItem() {}

    public BBItem(int maxStackSize) {
        setMaxStackSize(maxStackSize);
    }
}
