package com.bountifulbaubles.equipment;

public final class MiningContext {
    private final int x;
    private final int y;
    private final int z;
    private final boolean includeHeldItem;

    public MiningContext(int x, int y, int z, boolean includeHeldItem) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.includeHeldItem = includeHeldItem;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean includesHeldItem() {
        return includeHeldItem;
    }
}
