package com.bountifulbaubles.equipment;

import com.bountifulbaubles.registry.BountifulBaublesRegistry;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.ItemStack;

/** Server-side target selection and confirmation for wormhole items. */
public final class BBWormhole {
    private static final Map<EntityPlayer, PendingRequest> REQUESTS =
        new WeakHashMap<EntityPlayer, PendingRequest>();

    private BBWormhole() {}

    public static synchronized void open(EntityPlayer player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        PendingRequest pending = REQUESTS.remove(player);
        EntityPlayer origin = pending == null ? null : pending.origin;
        if (pending != null && player.worldObj.getTotalWorldTime() - pending.tick > 400L) {
            if (origin != null) origin.addChatMessage("item.bountifulbaubles.potion_wormhole.request_expired");
            player.addChatMessage("item.bountifulbaubles.potion_wormhole.request_expired");
            origin = null;
        }
        if (origin != null && isValid(origin, player)) {
            if (!consume(origin)) {
                origin.addChatMessage("item.bountifulbaubles.potion_wormhole.noitem");
                return;
            }
            teleport(origin, player);
            origin.addChatMessage("item.bountifulbaubles.potion_wormhole.accepted");
            player.addChatMessage("item.bountifulbaubles.potion_wormhole.accepted_target");
            return;
        }

        EntityPlayer target = nearestOtherPlayer(player);
        if (target == null) {
            player.addChatMessage("item.bountifulbaubles.potion_wormhole.nootherplayers");
            return;
        }
        REQUESTS.put(target, new PendingRequest(player, player.worldObj.getTotalWorldTime()));
        player.addChatMessage("item.bountifulbaubles.potion_wormhole.request_sent");
        target.addChatMessage("item.bountifulbaubles.potion_wormhole.request");
    }

    private static EntityPlayer nearestOtherPlayer(EntityPlayer origin) {
        EntityPlayer nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (Object value : origin.worldObj.playerEntities) {
            if (!(value instanceof EntityPlayer)) continue;
            EntityPlayer candidate = (EntityPlayer) value;
            if (candidate == origin || candidate.isDead || candidate.dimension != origin.dimension) continue;
            double distance = origin.getDistanceSqToEntity(candidate);
            if (distance < nearestDistance) { nearest = candidate; nearestDistance = distance; }
        }
        return nearest;
    }

    private static boolean isValid(EntityPlayer origin, EntityPlayer target) {
        return origin != null && target != null && !origin.isDead && !target.isDead
            && origin.dimension == target.dimension && origin.ridingEntity == null
            && !origin.isSleeping() && !target.isSleeping();
    }

    private static boolean consume(EntityPlayer player) {
        if (player.inCreativeMode()) return true;
        ItemStack held = player.getHeldItemStack();
        if (held != null && held.getItem() == BountifulBaublesRegistry.potion_wormhole) {
            consumeStack(player, held, true);
            return true;
        }
        for (int index = 0; index < player.inventory.mainInventory.length; index++) {
            ItemStack stack = player.inventory.getStackInSlot(index);
            if (stack == null || stack.getItem() != BountifulBaublesRegistry.potion_wormhole) continue;
            consumeStack(player, stack, false);
            return true;
        }
        return held != null && held.getItem() == BountifulBaublesRegistry.wormhole_mirror;
    }

    private static void consumeStack(EntityPlayer player, ItemStack stack, boolean held) {
        stack.stackSize--;
        if (stack.stackSize <= 0) {
            if (held) player.setHeldItemStack(new ItemStack(Item.glassBottle));
            else player.inventory.addItemStackToInventoryOrDropIt(new ItemStack(Item.glassBottle));
        }
        if (player.inventoryContainer != null) player.inventoryContainer.detectAndSendChanges();
    }

    private static void teleport(EntityPlayer origin, EntityPlayer target) {
        origin.setPositionAndUpdate(target.posX, target.posY, target.posZ);
        origin.fallDistance = 0.0F;
    }

    private static final class PendingRequest {
        private final EntityPlayer origin;
        private final long tick;

        private PendingRequest(EntityPlayer origin, long tick) {
            this.origin = origin;
            this.tick = tick;
        }
    }
}
