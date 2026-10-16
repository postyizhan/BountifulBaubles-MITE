package com.bountifulbaubles.equipment;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.AttributeInstance;
import net.minecraft.AttributeModifier;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.SharedMonsterAttributes;
import net.minecraft.Potion;
import net.minecraft.PotionEffect;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

/** Shared effect lookups for the MITE port. All checks use Curios equipment state. */
public final class BBEffects {
    private static final java.util.UUID MAX_HEALTH_DRAIN_ID =
        java.util.UUID.fromString("554f3929-4193-4ae5-a4da-4b528a89ca32");
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
    private static final Set<Item> STEP_ASSIST =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());
    private static final Set<Item> FAST_FOOD =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());
    private static final Map<Item, Float> FIRE_DAMAGE_MULTIPLIER =
        new IdentityHashMap<Item, Float>();
    private static final Set<Item> BROKEN_HEART =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());
    private static final Set<Item> PHYLACTERY =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());
    private static final Set<Item> LEVITATION_IMMUNITY =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());
    private static final Set<Item> EXTENDED_IFRAMES =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());
    private static final Set<Item> FAST_TOOL_SWITCHING =
        Collections.newSetFromMap(new IdentityHashMap<Item, Boolean>());

    private static final UUID SINFUL_DAMAGE_ID =
        UUID.fromString("d0b248eb-3abb-4584-9cc5-2aaa06146300");
    private static Potion sinfulPotion;
    private static final Map<EntityPlayer, Long> LAST_BROKEN_HEART_RECOVERY =
        new java.util.WeakHashMap<EntityPlayer, Long>();
    private static final Map<EntityLivingBase, Integer> ORIGINAL_IFRAMES =
        new java.util.WeakHashMap<EntityLivingBase, Integer>();

    private static final float VANILLA_STEP_HEIGHT = 0.6F;
    private static final float ASSISTED_STEP_HEIGHT = 1.24993F;
    private static final float SNEAKING_STEP_HEIGHT = 0.60007F;

    private BBEffects() {}

    /** Registers the one custom effect used by the sin pendants. */
    public static synchronized void initialize() {
        if (sinfulPotion != null) return;
        int id = -1;
        for (int candidate = 24; candidate < Potion.potionTypes.length; candidate++) {
            if (Potion.potionTypes[candidate] == null) { id = candidate; break; }
        }
        if (id < 0) throw new IllegalStateException("No free potion id for bountifulbaubles:sinful");
        sinfulPotion = new SinfulPotion(id);
        Potion.potionTypes[id] = sinfulPotion;
    }

    public static Potion getSinfulPotion() {
        initialize();
        return sinfulPotion;
    }

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

    public static synchronized void addStepAssist(Item item) {
        STEP_ASSIST.add(item);
    }

    public static synchronized void addFastFood(Item item) {
        FAST_FOOD.add(item);
    }

    public static synchronized void addFireDamageResistance(Item item, float multiplier) {
        FIRE_DAMAGE_MULTIPLIER.put(item, multiplier);
    }

    public static synchronized void addBrokenHeart(Item item) {
        BROKEN_HEART.add(item);
    }

    public static synchronized void addPhylactery(Item item) {
        PHYLACTERY.add(item);
    }

    public static synchronized void addLevitationImmunity(Item item) {
        LEVITATION_IMMUNITY.add(item);
    }

    public static synchronized void addExtendedInvulnerability(Item item) {
        EXTENDED_IFRAMES.add(item);
    }

    public static synchronized void addFastToolSwitching(Item item) {
        FAST_TOOL_SWITCHING.add(item);
    }

    public static boolean canApplyPotion(EntityLivingBase entity, int potionId) {
        return !hasEquippedItem(entity, item -> {
            Set<Integer> ids = POTION_IMMUNITY.get(item);
            return ids != null && ids.contains(potionId);
        });
    }

    public static boolean hasFastToolSwitching(EntityLivingBase entity) {
        return hasEquippedItem(entity, FAST_TOOL_SWITCHING::contains);
    }

    public static void updateInvulnerabilityFrames(EntityLivingBase entity) {
        if (entity == null) return;
        if (hasEquippedItem(entity, EXTENDED_IFRAMES::contains)) {
            ORIGINAL_IFRAMES.putIfAbsent(entity, entity.maxHurtResistantTime);
            entity.maxHurtResistantTime = Math.max(entity.maxHurtResistantTime, 36);
        } else {
            Integer original = ORIGINAL_IFRAMES.remove(entity);
            if (original != null) entity.maxHurtResistantTime = original.intValue();
        }
    }

    public static void updatePlayer(EntityPlayer player) {
        if (player == null || player.worldObj == null || player.worldObj.isRemote) return;
        if (hasEquippedItem(player, item -> item == BountifulBaublesRegistryAccess.PRIDE)) {
            if (player.getHealth() >= player.getMaxHealth()) {
                player.addPotionEffect(new PotionEffect(getSinfulPotion().getId(), Integer.MAX_VALUE, 0, false));
            } else if (player.isPotionActive(getSinfulPotion().getId())) {
                player.removePotionEffect(getSinfulPotion().getId());
            }
        } else if (player.isPotionActive(getSinfulPotion().getId())) {
            player.removePotionEffect(getSinfulPotion().getId());
        }

        long day = player.worldObj.getTotalWorldTime() / 24000L;
        Long last = LAST_BROKEN_HEART_RECOVERY.get(player);
        if (last == null || last.longValue() != day) {
            LAST_BROKEN_HEART_RECOVERY.put(player, day);
            recoverBrokenHeart(player);
        }
    }

    public static void onFoodEaten(EntityPlayer player, ItemStack stack) {
        if (player == null || stack == null || !hasEquippedItem(player, item -> item == BountifulBaublesRegistryAccess.GLUTTONY)) return;
        if (stack.getItem() != null && stack.getItem().isEatable(stack)) {
            player.addPotionEffect(new PotionEffect(getSinfulPotion().getId(), 200, 0, true));
        }
    }

    public static void onCriticalHit(EntityPlayer player) {
        if (player != null && hasEquippedItem(player, item -> item == BountifulBaublesRegistryAccess.WRATH)) {
            player.addPotionEffect(new PotionEffect(getSinfulPotion().getId(), 120, 3, true));
        }
    }

    public static void onWakeUp(EntityPlayer player) {
        if (player != null && !player.worldObj.isRemote) recoverBrokenHeart(player);
    }

    private static void recoverBrokenHeart(EntityPlayer player) {
        if (!hasEquippedItem(player, BROKEN_HEART::contains) && !hasEquippedItem(player, PHYLACTERY::contains)) return;
        AttributeInstance maxHealth = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        if (maxHealth == null) return;
        AttributeModifier previous = maxHealth.getModifier(MAX_HEALTH_DRAIN_ID);
        if (previous == null) return;
        double restored = previous.getAmount() + 2.0D;
        maxHealth.removeModifier(previous);
        if (restored < 0.0D) {
            maxHealth.applyModifier(new AttributeModifier(MAX_HEALTH_DRAIN_ID,
                "Broken Heart max health drain", restored, 0).setSaved(true));
            player.addChatMessage("item.bountifulbaubles.broken_heart.partial_heal");
        } else {
            player.addChatMessage("item.bountifulbaubles.broken_heart.full_heal");
        }
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

    public static void updateStepHeight(net.minecraft.EntityPlayer player) {
        if (hasEquippedItem(player, STEP_ASSIST::contains)) {
            player.stepHeight = player.isSneaking() ? SNEAKING_STEP_HEIGHT : ASSISTED_STEP_HEIGHT;
        } else if (player.stepHeight == ASSISTED_STEP_HEIGHT || player.stepHeight == SNEAKING_STEP_HEIGHT) {
            player.stepHeight = VANILLA_STEP_HEIGHT;
        }
    }

    public static boolean hasFastFood(EntityLivingBase entity) {
        return hasEquippedItem(entity, FAST_FOOD::contains);
    }

    public static void reduceFireDamage(EntityLivingBase entity, net.minecraft.Damage damage) {
        if (entity == null || damage == null || !damage.isFireDamage() || damage.isLavaDamage()) return;
        float multiplier = getEquippedMinimum(entity, FIRE_DAMAGE_MULTIPLIER, 1.0F);
        if (multiplier > 0.0F && multiplier < 1.0F) damage.scaleAmount(multiplier);
    }

    public static boolean preventDeath(EntityPlayer player, float healthAfter) {
        if (player == null || player.onClient()) return false;
        boolean phylactery = hasEquippedItem(player, PHYLACTERY::contains);
        if (!phylactery && !hasEquippedItem(player, BROKEN_HEART::contains)) return false;

        AttributeInstance maxHealth = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        if (maxHealth == null || maxHealth.getAttributeValue() + healthAfter < 1.0D) return false;

        player.setHealth(Float.MIN_VALUE);
        applyMaxHealthDrain(player, healthAfter);
        if (phylactery && player.worldObj.isOverworld()) {
            com.bountifulbaubles.item.BBTeleportItem.teleportToSpawn(player, player.worldObj);
        }
        return true;
    }

    private static void applyMaxHealthDrain(EntityPlayer player, float delta) {
        AttributeInstance maxHealth = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        if (maxHealth == null || delta >= 0.0F) return;
        AttributeModifier previous = maxHealth.getModifier(MAX_HEALTH_DRAIN_ID);
        double previousAmount = previous == null ? 0.0D : previous.getAmount();
        if (previous != null) maxHealth.removeModifier(previous);
        maxHealth.applyModifier(new AttributeModifier(MAX_HEALTH_DRAIN_ID,
            "Broken Heart max health drain", previousAmount + delta, 0).setSaved(true));
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

    private static float getEquippedMinimum(EntityLivingBase entity, Map<Item, Float> amounts, float defaultValue) {
        if (entity == null) return defaultValue;
        return CuriosApi.getCuriosHelper().getEquippedCurios(entity).map(stacks -> {
            float minimum = defaultValue;
            for (int index = 0; index < stacks.getSlots(); index++) {
                ItemStack stack = stacks.getStackInSlot(index);
                if (stack != null && stack.getItem() != null) {
                    Float amount = amounts.get(stack.getItem());
                    if (amount != null && amount < minimum) minimum = amount;
                }
            }
            return minimum;
        }).orElse(defaultValue);
    }

    /** Registry indirection keeps the effect class independent of item initialization order. */
    public static final class BountifulBaublesRegistryAccess {
        public static Item PRIDE;
        public static Item GLUTTONY;
        public static Item WRATH;
        private BountifulBaublesRegistryAccess() {}
    }

    private static final class SinfulPotion extends Potion {
        private SinfulPotion(int id) {
            super(id, false, 0x101317);
            setPotionName("potion.bountifulbaubles.sinful");
            func_111184_a(SharedMonsterAttributes.attackDamage, SINFUL_DAMAGE_ID.toString(), 0.25D, 2);
        }
    }
}
