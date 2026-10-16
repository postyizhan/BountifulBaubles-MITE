package com.bountifulbaubles.item;

import net.minecraft.Damage;
import net.minecraft.DamageSource;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.World;

/** The phylactery can be worn, or held and used as an emergency recall. */
public final class PhylacteryCharmItem extends BBCurioItem {
    public PhylacteryCharmItem() { super("necklace", "ring"); }

    @Override
    protected boolean canRightClickEquip() { return false; }

    @Override
    public boolean onItemRightClick(EntityPlayer player, float partialTick, boolean ctrlDown) {
        return player.setHeldItemInUse();
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) { return 16; }

    @Override
    public void onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (player != null && world != null && player.onServer()) {
            if (world.isOverworld()) {
                BBTeleportItem.teleportToSpawn(player, world);
                player.attackEntityFrom(new Damage(DamageSource.generic, 7.0F));
            } else {
                player.addChatMessage("item.bountifulbaubles.magic_mirror.wrongdim");
            }
        }
        super.onItemUseFinish(stack, world, player);
    }
}
