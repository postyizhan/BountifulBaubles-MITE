package com.bountifulbaubles.item;

import net.minecraft.DamageSource;
import net.minecraft.EntityPlayer;
import net.minecraft.EntityDamageResult;
import net.minecraft.EnumItemInUseAction;
import net.minecraft.IDamageableItem;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.Material;

/** Curio shields that also use MITE's built-in blocking action when held. */
public final class BBShieldItem extends BBCurioItem implements IDamageableItem {
    private final Item repairItem;
    private final Material repairMaterial;

    public BBShieldItem(int durability, Item repairItem, Material repairMaterial) {
        super("back");
        this.repairItem = repairItem;
        this.repairMaterial = repairMaterial;
        setMaterial(repairMaterial);
        setMaxDamage(durability);
    }

    @Override
    public boolean onItemRightClick(EntityPlayer player, float partialTick, boolean ctrlDown) {
        return player.setHeldItemInUse();
    }

    @Override
    public EnumItemInUseAction getItemInUseAction(ItemStack stack, EntityPlayer player) {
        return EnumItemInUseAction.BLOCK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public int getNumComponentsForDurability() {
        return 1;
    }

    @Override
    public int getRepairCost() {
        return 2;
    }

    @Override
    public int getItemEnchantability() {
        return 9;
    }

    @Override
    public Item getRepairItem() {
        return repairItem;
    }

    @Override
    public Material getMaterialForRepairs() {
        return repairMaterial;
    }

    public static void damageOnBlock(EntityPlayer player, EntityDamageResult result) {
        if (player == null || result == null || player.onClient() || player.inCreativeMode()) return;

        ItemStack active = player.getItemInUse();
        if (active == null || active != player.getHeldItemStack()
            || !(active.getItem() instanceof BBShieldItem)) {
            return;
        }

        result.applyHeldItemDamageResult(active.tryDamageItem(DamageSource.generic, 1, player));
    }
}
