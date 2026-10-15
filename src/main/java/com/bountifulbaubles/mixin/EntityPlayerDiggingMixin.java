package com.bountifulbaubles.mixin;

import com.bountifulbaubles.equipment.DiggingEquipment;
import net.minecraft.Block;
import net.minecraft.Enchantment;
import net.minecraft.EnchantmentHelper;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import java.util.ArrayDeque;
import java.util.Deque;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerDiggingMixin {
    @Unique private final ThreadLocal<Deque<MiningContext>> bountifulbaubles$miningContexts =
        ThreadLocal.withInitial(ArrayDeque::new);

    @Inject(method = "getCurrentPlayerStrVsBlock(IIIZ)F", at = @At("HEAD"))
    private void bountifulbaubles$beginMiningContext(int x, int y, int z, boolean includeHeldItem,
                                                     CallbackInfoReturnable<Float> cir) {
        bountifulbaubles$miningContexts.get().push(new MiningContext(x, y, z, includeHeldItem));
    }

    @Redirect(
        method = "getCurrentPlayerStrVsBlock(IIIZ)F",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/EntityPlayer;getHeldItem()Lnet/minecraft/Item;"
        )
    )
    private Item bountifulbaubles$useDiggingGlove(EntityPlayer player) {
        Item heldItem = player.getHeldItem();
        MiningContext context = bountifulbaubles$currentMiningContext();
        if (context == null || !context.includeHeldItem) return heldItem;

        Block block = player.worldObj.getBlock(context.x, context.y, context.z);
        int metadata = player.worldObj.getBlockMetadata(context.x, context.y, context.z);
        ItemStack glove = DiggingEquipment.getApplicableGlove(player, block, metadata);
        return glove == null ? heldItem : glove.getItem();
    }

    @Redirect(
        method = "getCurrentPlayerStrVsBlock(IIIZ)F",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/EnchantmentHelper;getEfficiencyModifier(Lnet/minecraft/EntityLivingBase;)I"
        )
    )
    private int bountifulbaubles$addGloveEfficiency(EntityLivingBase entity) {
        int level = EnchantmentHelper.getEfficiencyModifier(entity);
        MiningContext context = bountifulbaubles$currentMiningContext();
        if (context == null || !context.includeHeldItem || !(entity instanceof EntityPlayer)) return level;

        EntityPlayer player = (EntityPlayer) entity;
        Block block = player.worldObj.getBlock(context.x, context.y, context.z);
        ItemStack glove = DiggingEquipment.getApplicableGlove(
            player, block, player.worldObj.getBlockMetadata(context.x, context.y, context.z));
        return glove == null ? level : level + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency, glove);
    }

    @Inject(method = "getCurrentPlayerStrVsBlock(IIIZ)F", at = @At("RETURN"))
    private void bountifulbaubles$endMiningContext(int x, int y, int z, boolean includeHeldItem,
                                                   CallbackInfoReturnable<Float> cir) {
        Deque<MiningContext> contexts = bountifulbaubles$miningContexts.get();
        if (!contexts.isEmpty()) contexts.pop();
        if (contexts.isEmpty()) bountifulbaubles$miningContexts.remove();
    }

    @Unique
    private MiningContext bountifulbaubles$currentMiningContext() {
        Deque<MiningContext> contexts = bountifulbaubles$miningContexts.get();
        return contexts.isEmpty() ? null : contexts.peek();
    }

    @Unique
    private static final class MiningContext {
        private final int x;
        private final int y;
        private final int z;
        private final boolean includeHeldItem;

        private MiningContext(int x, int y, int z, boolean includeHeldItem) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.includeHeldItem = includeHeldItem;
        }
    }
}
