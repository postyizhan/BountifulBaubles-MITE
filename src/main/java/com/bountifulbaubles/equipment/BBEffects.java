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
    private static final Set<Item> FALL_IMMUNITY =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());
    private static final Map<Item, Float> FALL_RESISTANCE =
        new IdentityHashMap<Item, Float>();
    private static final Map<Item, Float> JUMP_BOOST =
        new IdentityHashMap<Item, Float>();
    private static final Set<Item> WEB_IMMUNITY =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());

    private BBEffects() {}

    public static synchronized void addPotionImmunity(Item item, int... potionIds) {
        Set<Integer> ids = POTION_IMMUNITY.computeIfAbsent(item, ignored -> new HashSet<Integer>());
        for (int potionId : potionIds) ids.add(potionId);
    }

    public static synchronized void addFireImmunity(Item item) {
        FIRE_IMMUNITY.add(item);
    }

    public static synchronized void addFallImmunity(Item item) {
        FALL_IMMUNITY.add(item);
    }

    public static synchronized void addFallResistance(Item item, float amount) {
        FALL_RESISTANCE.put(item, amount);
    }

    public static synchronized void addJumpBoost(Item item, float amount) {
        JUMP_BOOST.put(item, amount);
    }

    public static synchronized void addWebImmunity(Item item) {
        WEB_IMMUNITY.add(item);
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

    public static boolean isFallImmune(EntityLivingBase entity) {
        return hasEquippedItem(entity, FALL_IMMUNITY::contains);
    }

    public static float reduceFallDistance(EntityLivingBase entity, float distance) {
        return Math.max(0.0F, distance - getEquippedAmount(entity, FALL_RESISTANCE));
    }

    public static float getJumpBoost(EntityLivingBase entity) {
        return getEquippedAmount(entity, JUMP_BOOST);
    }

    public static boolean isWebImmune(EntityLivingBase entity) {
        return hasEquippedItem(entity, WEB_IMMUNITY::contains);
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

    private static float getEquippedAmount(EntityLivingBase entity, Map<Item, Float> amounts) {
        if (entity == null) return 0.0F;
        return CuriosApi.getCuriosHelper().getEquippedCurios(entity).map(stacks -> {
            float total = 0.0F;
            for (int index = 0; index < stacks.getSlots(); index++) {
                ItemStack stack = stacks.getStackInSlot(index);
                if (stack != null && stack.getItem() != null) {
                    Float amount = amounts.get(stack.getItem());
                    if (amount != null) total += amount;
                }
            }
            return total;
        }).orElse(0.0F);
    }
}
