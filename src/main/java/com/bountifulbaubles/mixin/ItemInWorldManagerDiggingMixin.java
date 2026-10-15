package com.bountifulbaubles.mixin;

import com.bountifulbaubles.equipment.DiggingEquipment;
import net.minecraft.Block;
import net.minecraft.BlockBreakInfo;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemInWorldManager;
import net.minecraft.ItemStack;
import net.minecraft.ServerPlayer;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInWorldManager.class)
public abstract class ItemInWorldManagerDiggingMixin {
    @Shadow public World theWorld;
    @Shadow public ServerPlayer thisPlayerMP;
    @Unique private final ThreadLocal<java.util.Deque<DiggingContext>> bountifulbaubles$diggingContexts =
        ThreadLocal.withInitial(java.util.ArrayDeque::new);

    @Inject(method = "tryHarvestBlock(III)Z", at = @At("HEAD"))
    private void bountifulbaubles$findGlove(int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        Block block = theWorld.getBlock(x, y, z);
        ItemStack glove = block == null ? null : DiggingEquipment.getApplicableGlove(
            thisPlayerMP, block, theWorld.getBlockMetadata(x, y, z));
        bountifulbaubles$diggingContexts.get().push(new DiggingContext(glove));
    }

    @Redirect(
        method = "tryHarvestBlock(III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/BlockBreakInfo;setHarvestedBy(Lnet/minecraft/EntityLivingBase;)Lnet/minecraft/BlockBreakInfo;",
            ordinal = 1
        )
    )
    private BlockBreakInfo bountifulbaubles$setGloveAsHarvesterStack(
        BlockBreakInfo info, EntityLivingBase harvester) {
        BlockBreakInfo harvested = info.setHarvestedBy(harvester);
        ItemStack glove = bountifulbaubles$currentGlove();
        if (glove != null) {
            harvested.responsible_item_stack = glove;
        }
        return harvested;
    }

    @Redirect(
        method = "tryHarvestBlock(III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/ServerPlayer;getHeldItemStack()Lnet/minecraft/ItemStack;",
            ordinal = 2
        )
    )
    private ItemStack bountifulbaubles$damageGloveInsteadOfHeldItem(ServerPlayer player) {
        ItemStack glove = bountifulbaubles$currentGlove();
        return glove == null
            ? player.getHeldItemStack()
            : glove;
    }

    @Inject(method = "tryHarvestBlock(III)Z", at = @At("RETURN"))
    private void bountifulbaubles$clearGlove(int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        java.util.Deque<DiggingContext> contexts = bountifulbaubles$diggingContexts.get();
        if (!contexts.isEmpty()) contexts.pop();
        if (contexts.isEmpty()) bountifulbaubles$diggingContexts.remove();
    }

    @Unique
    private ItemStack bountifulbaubles$currentGlove() {
        java.util.Deque<DiggingContext> contexts = bountifulbaubles$diggingContexts.get();
        return contexts.isEmpty() ? null : contexts.peek().glove;
    }

    @Unique
    private static final class DiggingContext {
        private final ItemStack glove;

        private DiggingContext(ItemStack glove) {
            this.glove = glove;
        }
    }
}
