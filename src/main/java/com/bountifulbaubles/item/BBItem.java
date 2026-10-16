package com.bountifulbaubles.item;

import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import net.minecraft.EntityPlayer;
import net.minecraft.Slot;
import net.minecraft.StatCollector;
import java.util.List;
import net.xiaoyu233.fml.reload.utils.IdUtil;

/** Shared MITE material and crafting defaults for all BB items. */
public class BBItem extends Item {
    private static final String DEFAULT_TEXTURE = "bountifulbaubles:placeholder";

    public BBItem() {
        super(IdUtil.getNextItemID(), DEFAULT_TEXTURE);
        setMaterial(Material.stone);
    }

    public BBItem(int maxStackSize) {
        this();
        setMaxStackSize(maxStackSize);
    }

    @Override
    public float getCraftingDifficultyAsComponent(ItemStack stack) {
        float difficulty = super.getCraftingDifficultyAsComponent(stack);
        if (difficulty >= 0.0F) return difficulty;
        // Crafted items retain their recipe cost; loot-only items have no crafting work to inherit.
        return getLowestCraftingDifficultyToProduce();
    }

    @Override
    public float getLowestCraftingDifficultyToProduce() {
        float difficulty = super.getLowestCraftingDifficultyToProduce();
        return difficulty == Float.MAX_VALUE ? 0.0F : difficulty;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip,
                                boolean advanced, Slot slot) {
        super.addInformation(stack, player, tooltip, advanced, slot);
        String base = getUnlocalizedName(stack) + ".tooltip.";
        for (int index = 0; index < 10; index++) {
            String key = base + index;
            if (!StatCollector.func_94522_b(key)) break;
            tooltip.add(StatCollector.translateToLocal(key));
        }
        if (advanced) {
            for (int index = 0; index < 10; index++) {
                String key = base + index + "s";
                if (!StatCollector.func_94522_b(key)) continue;
                tooltip.add(StatCollector.translateToLocal(key));
            }
        }
    }
}
