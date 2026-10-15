package com.bountifulbaubles.item;

import net.minecraft.Item;
import net.xiaoyu233.fml.reload.utils.IdUtil;

/** Base for BB items that are not Curios accessories. */
public class BBItem extends Item {
    private static final String DEFAULT_TEXTURE = "bountifulbaubles:placeholder";

    public BBItem() {
        super(IdUtil.getNextItemID(), DEFAULT_TEXTURE);
    }

    public BBItem(int maxStackSize) {
        this();
        setMaxStackSize(maxStackSize);
    }
}
