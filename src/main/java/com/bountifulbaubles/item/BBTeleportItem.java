package com.bountifulbaubles.item;

import net.minecraft.ChunkCoordinates;
import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.World;

/** Shared use flow for BB mirrors and recall potions on the 1.6.4 item API. */
public final class BBTeleportItem extends BBItem {
    private final boolean consumesStack;

    public BBTeleportItem(boolean consumesStack) {
        super(1);
        this.consumesStack = consumesStack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 16;
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, float partialTick, boolean ctrlDown) {
        return player.setHeldItemInUse();
    }

    @Override
    public void onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (player.onServer()) {
            teleportToSpawn(player, world);
            if (consumesStack && !player.inCreativeMode()) {
                player.convertOneOfHeldItem(new ItemStack(Item.glassBottle));
            }
        }
        super.onItemUseFinish(stack, world, player);
    }

    public static void teleportToSpawn(EntityPlayer player, World world) {
        ChunkCoordinates target = player.getBedLocation();
        if (target == null) target = world.getSpawnPoint();
        if (target != null) {
            player.setPositionAndUpdate(target.posX + 0.5D, target.posY + 1.0D, target.posZ + 0.5D);
            player.fallDistance = 0.0F;
        }
    }
}
