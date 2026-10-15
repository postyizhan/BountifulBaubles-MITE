package com.bountifulbaubles.equipment;

import com.bountifulbaubles.item.DiggingGlovesItem;
import net.minecraft.Block;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.EquippedCurio;

public final class DiggingEquipment {
    private DiggingEquipment() {}

    public static ItemStack getApplicableGlove(EntityPlayer player, Block block, int metadata) {
        if (player == null || block == null) return null;

        ItemStack held = player.getHeldItemStack();
        if (held != null && (held.getItem().isTool()
            || held.getItem() instanceof DiggingGlovesItem
            || held.getStrVsBlock(block, metadata) > 1.0F)) return null;

        EquippedCurio equipped = CuriosApi.getCuriosHelper().findEquippedCurio(
            stack -> stack.getItem() instanceof DiggingGlovesItem
                && ((DiggingGlovesItem) stack.getItem()).isEffectiveAgainstBlock(block, metadata),
            player
        ).orElse(null);
        return equipped == null ? null : equipped.getStack();
    }
}
