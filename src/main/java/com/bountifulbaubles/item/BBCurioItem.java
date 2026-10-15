package com.bountifulbaubles.item;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.EntityLivingBase;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/** Base item for BB accessories whose first integration point is Curios-MITE. */
public class BBCurioItem extends Item implements ICurioItem {
    private final Set<String> slotTypes;

    public BBCurioItem(String... slotTypes) {
        setMaxStackSize(1);
        LinkedHashSet<String> types = new LinkedHashSet<String>(Arrays.asList(slotTypes));
        this.slotTypes = Collections.unmodifiableSet(types);
    }

    public Set<String> getSlotTypes() {
        return slotTypes;
    }

    @Override
    public ICurio getCurio(final ItemStack stack) {
        return new ICurio() {
            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            public boolean canRightClickEquip() {
                return true;
            }

            @Override
            public void onEquip(String identifier, int index, EntityLivingBase entity) {
                BBCurioItem.this.onEquip(identifier, index, entity, stack);
            }

            @Override
            public void onUnequip(String identifier, int index, EntityLivingBase entity) {
                BBCurioItem.this.onUnequip(identifier, index, entity, stack);
            }

            @Override
            public void curioTick(String identifier, int index, EntityLivingBase entity) {
                BBCurioItem.this.curioTick(identifier, index, entity, stack);
            }
        };
    }

    protected void onEquip(String identifier, int index, EntityLivingBase entity, ItemStack stack) {}

    protected void onUnequip(String identifier, int index, EntityLivingBase entity, ItemStack stack) {}

    protected void curioTick(String identifier, int index, EntityLivingBase entity, ItemStack stack) {}
}
