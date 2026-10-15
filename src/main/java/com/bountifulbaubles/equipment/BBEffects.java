package com.bountifulbaubles.equipment;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import net.minecraft.EntityLivingBase;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

/** Shared effect lookups for the MITE port. All checks use Curios equipment state. */
public final class BBEffects {
    private static final Map<Item, Set<Integer>> POTION_IMMUNITY =
        new IdentityHashMap<Item, Set<Integer>>();
    private static final Set<Item> FIRE_IMMUNITY =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());

    private BBEffects() {}

    public static synchronized void addPotionImmunity(Item item, int... potionIds) {
        Set<Integer> ids = POTION_IMMUNITY.computeIfAbsent(item, ignored -> new HashSet<Integer>());
        for (int potionId : potionIds) ids.add(potionId);
    }

    public static synchronized void addFireImmunity(Item item) {
        FIRE_IMMUNITY.add(item);
    }

    public static boolean canApplyPotion(EntityLivingBase entity, int potionId) {
        return !hasEquippedItem(entity, item -> {
            Set<Integer> ids = POTION_IMMUNITY.get(item);
            return ids != null && ids.contains(potionId);
        });
    }

    public static boolean isFireImmune(EntityLivingBase entity) {
        return hasEquippedItem(entity, FIRE_IMMUNITY::contains);
    }

    private static boolean hasEquippedItem(EntityLivingBase entity, java.util.function.Predicate<Item> predicate) {
        if (entity == null) return false;
        return CuriosApi.getCuriosHelper().getEquippedCurios(entity).map(stacks -> {
            for (int index = 0; index < stacks.getSlots(); index++) {
                ItemStack stack = stacks.getStackInSlot(index);
                if (stack != null && stack.getItem() != null && predicate.test(stack.getItem())) return true;
            }
            return false;
        }).orElse(false);
    }
}
