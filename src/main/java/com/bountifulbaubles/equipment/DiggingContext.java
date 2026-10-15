package com.bountifulbaubles.equipment;

import net.minecraft.ItemStack;

public final class DiggingContext {
    private final ItemStack glove;

    public DiggingContext(ItemStack glove) {
        this.glove = glove;
    }

    public ItemStack getGlove() {
        return glove;
    }
}
