package com.bountifulbaubles.item;

import net.minecraft.Block;
import net.minecraft.BlockBreakInfo;
import net.minecraft.IDamageableItem;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.ItemTool;
import net.minecraft.Material;

public final class DiggingGlovesItem extends BBCurioItem implements IDamageableItem {
    private final ItemTool pickaxe;
    private final ItemTool axe;
    private final ItemTool spade;
    private final boolean diamondTier;
    private final Item repairItem;
    private final Material repairMaterial;

    public DiggingGlovesItem(boolean diamondTier) {
        super("hands");
        this.diamondTier = diamondTier;
        this.pickaxe = Item.pickaxeIron.getAsTool();
        this.axe = Item.axeIron.getAsTool();
        this.spade = Item.shovelIron.getAsTool();
        this.repairItem = diamondTier ? Item.diamond : Item.ingotIron;
        this.repairMaterial = diamondTier ? Material.diamond : Material.iron;
        setMaterial(repairMaterial);
        setMaxDamage(diamondTier ? 1170 : Item.pickaxeIron.getMaxDamage((net.minecraft.EnumQuality) null));
    }

    @Override
    public float getStrVsBlock(Block block, int metadata) {
        if (!isEffectiveAgainstBlock(block, metadata)) return super.getStrVsBlock(block, metadata);
        return diamondTier ? 4.0F : 2.0F;
    }

    @Override
    public boolean isEffectiveAgainstBlock(Block block, int metadata) {
        // The gloves intentionally provide the stone and iron tiers in MITE.
        int gloveHarvestLevel = diamondTier ? 3 : 2;
        if (block == null || block.getMinHarvestLevel(metadata) > gloveHarvestLevel) return false;
        return pickaxe.isEffectiveAgainstBlock(block, metadata)
            || axe.isEffectiveAgainstBlock(block, metadata)
            || spade.isEffectiveAgainstBlock(block, metadata);
    }

    @Override
    public int getNumComponentsForDurability() {
        return 1;
    }

    @Override
    public int getRepairCost() {
        return getNumComponentsForDurability() * 2;
    }

    @Override
    public int getItemEnchantability() {
        return diamondTier ? 10 : Item.pickaxeIron.getItemEnchantability();
    }

    @Override
    public Item getRepairItem() {
        return repairItem;
    }

    @Override
    public Material getMaterialForRepairs() {
        return repairMaterial;
    }

    @Override
    public boolean onBlockDestroyed(BlockBreakInfo info) {
        if (info == null || info.world == null || info.world.isRemote || info.block == null
            || info.getBlockHardness() <= 0.0F || info.isResponsiblePlayerInCreativeMode()
            || info.block.isPortable(info.world, info.getHarvester(), info.x, info.y, info.z)) {
            return false;
        }

        // BlockBreakInfo's getter reads the player's hand, while this glove is
        // held in Curios. The manager records the actual harvesting stack here.
        ItemStack stack = info.responsible_item_stack;
        if (stack == null || stack.getItem() != this
            || getStrVsBlock(info.block, info.getMetadata()) <= 1.0F) {
            return false;
        }

        stack.tryDamageItem(info.world, 1, true);
        return true;
    }

}
