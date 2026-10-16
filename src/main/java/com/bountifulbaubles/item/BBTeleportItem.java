package com.bountifulbaubles.item;

import com.bountifulbaubles.equipment.BBWormhole;
import net.minecraft.ChunkCoordinates;
import net.minecraft.EntityPlayer;
import net.minecraft.EnumItemInUseAction;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.World;

/** MITE use implementation for the four recall and wormhole items. */
public final class BBTeleportItem extends BBItem {
    public enum Kind { MAGIC_MIRROR, RECALL_POTION, WORMHOLE_MIRROR, WORMHOLE_POTION }
    private final Kind kind;

    public BBTeleportItem(boolean consumesStack) {
        this(consumesStack ? Kind.RECALL_POTION : Kind.MAGIC_MIRROR);
    }

    public BBTeleportItem(Kind kind) {
        super(1);
        this.kind = kind;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 16;
    }

    @Override
    public EnumItemInUseAction getItemInUseAction(ItemStack stack, EntityPlayer player) {
        return kind == Kind.MAGIC_MIRROR || kind == Kind.WORMHOLE_MIRROR
            ? EnumItemInUseAction.EAT : EnumItemInUseAction.DRINK;
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, float partialTick, boolean ctrlDown) {
        boolean recall = kind == Kind.MAGIC_MIRROR || kind == Kind.RECALL_POTION
            || (kind == Kind.WORMHOLE_MIRROR && !player.isSneaking());
        if (recall && !player.worldObj.isOverworld()) {
            player.addChatMessage("item.bountifulbaubles.magic_mirror.wrongdim");
            return false;
        }
        return player.setHeldItemInUse();
    }

    @Override
    public void onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (player != null && world != null && player.onServer()) {
            switch (kind) {
                case MAGIC_MIRROR:
                case RECALL_POTION:
                    if (world.isOverworld()) teleportToSpawn(player, world);
                    else player.addChatMessage("item.bountifulbaubles.magic_mirror.wrongdim");
                    break;
                case WORMHOLE_MIRROR:
                    if (player.isSneaking()) BBWormhole.open(player);
                    else if (world.isOverworld()) teleportToSpawn(player, world);
                    else player.addChatMessage("item.bountifulbaubles.magic_mirror.wrongdim");
                    break;
                case WORMHOLE_POTION:
                    BBWormhole.open(player);
                    break;
                default:
                    break;
            }
            if (kind == Kind.RECALL_POTION && !player.inCreativeMode()) {
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
