package com.bountifulbaubles.registry;

import com.bountifulbaubles.BountifulBaubles;
import com.bountifulbaubles.item.BBCurioItem;
import com.bountifulbaubles.item.BBItem;
import com.bountifulbaubles.item.BBShieldItem;
import com.bountifulbaubles.item.BBTeleportItem;
import com.bountifulbaubles.item.DiggingGlovesItem;
import com.bountifulbaubles.equipment.BBEffects;
import huix.glacier.api.entrypoint.IGameRegistry;
import huix.glacier.api.registry.MinecraftRegistry;
import net.minecraft.Block;
import net.minecraft.Item;
import net.xiaoyu233.fml.api.item.IItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;

public final class BountifulBaublesRegistry implements IGameRegistry {
    private static final MinecraftRegistry REGISTRY = new MinecraftRegistry(BountifulBaubles.MOD_ID)
        .initAutoItemRegister();

    public static Item sunglasses;
    public static Item apple;
    public static Item vitamins;
    public static Item ring_overclocking;
    public static Item shulker_heart;
    public static Item ring_free_action;
    public static Item bezoar;
    public static Item black_dragon_scale;
    public static Item mixed_dragon_scale;
    public static Item ankh_charm;
    public static Item obsidian_skull;
    public static Item shield_cobalt;
    public static Item shield_obsidian;
    public static Item shield_ankh;
    public static Item balloon;
    public static Item lucky_horseshoe;
    public static Item horseshoe_balloon;
    public static Item amulet_sin_gluttony;
    public static Item amulet_sin_pride;
    public static Item amulet_sin_wrath;
    public static Item broken_heart;
    public static Item phylactery_charm;
    public static Item amulet_cross;
    public static Item gloves_dexterity;
    public static Item gloves_digging_iron;
    public static Item gloves_digging_diamond;
    public static Item ender_dragon_scale;
    public static Item broken_black_dragon_scale;
    public static Item magic_mirror;
    public static Item potion_recall;
    public static Item wormhole_mirror;
    public static Item potion_wormhole;
    public static Item amulet_sin_empty;
    public static Item disintegration_tablet;
    public static Item spectral_silt;
    public static Item resplendent_token;

    @Override
    public void onGameRegistry() {
        registerSlots();
        sunglasses = register("sunglasses", "head");
        BBEffects.addPotionImmunity(sunglasses, net.minecraft.Potion.blindness.id);
        apple = register("apple", "necklace", "ring");
        BBEffects.addPotionImmunity(apple, net.minecraft.Potion.hunger.id, net.minecraft.Potion.confusion.id);
        vitamins = register("vitamins", "necklace", "ring");
        BBEffects.addPotionImmunity(vitamins, net.minecraft.Potion.weakness.id, net.minecraft.Potion.digSlowdown.id);
        ring_overclocking = register("ring_overclocking", "ring")
            .addModifier(net.minecraft.SharedMonsterAttributes.movementSpeed, "overclocking", 0.07, 2);
        BBEffects.addPotionImmunity(ring_overclocking, net.minecraft.Potion.moveSlowdown.id);
        shulker_heart = register("shulker_heart", "necklace", "ring");
        ring_free_action = register("ring_free_action", "ring");
        BBEffects.addPotionImmunity(ring_free_action, net.minecraft.Potion.moveSlowdown.id);
        BBEffects.addWebImmunity(ring_free_action);
        bezoar = register("bezoar", "necklace", "ring");
        BBEffects.addPotionImmunity(bezoar, net.minecraft.Potion.poison.id);
        black_dragon_scale = register("black_dragon_scale", "necklace", "ring");
        BBEffects.addPotionImmunity(black_dragon_scale, net.minecraft.Potion.wither.id);
        mixed_dragon_scale = register("mixed_dragon_scale", "necklace", "ring");
        BBEffects.addPotionImmunity(mixed_dragon_scale, net.minecraft.Potion.poison.id, net.minecraft.Potion.wither.id);
        ankh_charm = register("ankh_charm", "necklace", "ring");
        BBEffects.addPotionImmunity(ankh_charm, net.minecraft.Potion.blindness.id, net.minecraft.Potion.hunger.id,
            net.minecraft.Potion.confusion.id, net.minecraft.Potion.weakness.id, net.minecraft.Potion.digSlowdown.id,
            net.minecraft.Potion.moveSlowdown.id, net.minecraft.Potion.poison.id, net.minecraft.Potion.wither.id);
        obsidian_skull = register("obsidian_skull", "necklace", "ring");
        BBEffects.addFireImmunity(obsidian_skull);
        BBEffects.addFireDamageResistance(obsidian_skull, 0.5F);
        shield_cobalt = registerCurio("shield_cobalt", new BBShieldItem(1008, Item.ingotIron, net.minecraft.Material.iron))
            .addModifier(net.minecraft.SharedMonsterAttributes.knockbackResistance, "cobalt_shield", 10, 0);
        shield_obsidian = registerCurio("shield_obsidian", new BBShieldItem(1344, Item.getItem(Block.obsidian), net.minecraft.Material.obsidian))
            .addModifier(net.minecraft.SharedMonsterAttributes.knockbackResistance, "obsidian_shield", 10, 0);
        BBEffects.addFireImmunity(shield_obsidian);
        BBEffects.addFireDamageResistance(shield_obsidian, 0.5F);
        shield_ankh = registerCurio("shield_ankh", new BBShieldItem(1680, Item.getItem(Block.obsidian), net.minecraft.Material.obsidian))
            .addModifier(net.minecraft.SharedMonsterAttributes.knockbackResistance, "ankh_shield", 10, 0);
        BBEffects.addFireImmunity(shield_ankh);
        BBEffects.addFireDamageResistance(shield_ankh, 0.5F);
        BBEffects.addPotionImmunity(shield_ankh, net.minecraft.Potion.blindness.id, net.minecraft.Potion.hunger.id,
            net.minecraft.Potion.confusion.id, net.minecraft.Potion.weakness.id, net.minecraft.Potion.digSlowdown.id,
            net.minecraft.Potion.moveSlowdown.id, net.minecraft.Potion.poison.id, net.minecraft.Potion.wither.id);
        balloon = register("balloon", "necklace", "ring");
        BBEffects.addFallResistance(balloon, 4.0F);
        BBEffects.addJumpBoost(balloon, 0.2F);
        lucky_horseshoe = register("lucky_horseshoe", "necklace", "ring");
        BBEffects.addFallImmunity(lucky_horseshoe);
        horseshoe_balloon = register("horseshoe_balloon", "necklace", "ring");
        BBEffects.addFallImmunity(horseshoe_balloon);
        BBEffects.addJumpBoost(horseshoe_balloon, 0.2F);
        amulet_sin_gluttony = register("amulet_sin_gluttony", "necklace");
        BBEffects.addFastFood(amulet_sin_gluttony);
        amulet_sin_pride = register("amulet_sin_pride", "necklace");
        BBEffects.addStepAssist(amulet_sin_pride);
        amulet_sin_wrath = register("amulet_sin_wrath", "necklace")
            .addModifier(net.minecraft.SharedMonsterAttributes.attackDamage, "wrath", 2, 0);
        broken_heart = register("broken_heart", "necklace", "ring");
        BBEffects.addBrokenHeart(broken_heart);
        phylactery_charm = register("phylactery_charm", "necklace", "ring");
        BBEffects.addPhylactery(phylactery_charm);
        amulet_cross = register("amulet_cross", "necklace");
        gloves_dexterity = register("gloves_dexterity", "hands");
        gloves_digging_iron = registerCurio("gloves_digging_iron", new DiggingGlovesItem(false));
        gloves_digging_diamond = registerCurio("gloves_digging_diamond", new DiggingGlovesItem(true));
        ender_dragon_scale = registerItem("ender_dragon_scale");
        broken_black_dragon_scale = registerItem("broken_black_dragon_scale");
        magic_mirror = registerItem("magic_mirror", new BBTeleportItem(false));
        potion_recall = registerItem("potion_recall", new BBTeleportItem(true));
        wormhole_mirror = registerItem("wormhole_mirror", new BBTeleportItem(false));
        potion_wormhole = registerItem("potion_wormhole", new BBTeleportItem(true));
        amulet_sin_empty = registerItem("amulet_sin_empty");
        disintegration_tablet = registerItem("disintegration_tablet", 1);
        spectral_silt = registerItem("spectral_silt");
        resplendent_token = registerItem("resplendent_token");
    }

    private static BBCurioItem register(String path, String... slots) {
        return registerCurio(path, new BBCurioItem(slots));
    }

    private static BBCurioItem registerCurio(String path, BBCurioItem item) {
        item = (BBCurioItem) REGISTRY.registerItem(
            BountifulBaubles.MOD_ID + ":" + path, path, item
        );
        ((IItem) item).setItemTextureName(BountifulBaubles.MOD_ID + ":" + path);
        return item;
    }

    private static Item registerItem(String path) {
        return registerItem(path, 64);
    }

    private static Item registerItem(String path, int maxStackSize) {
        return registerItem(path, new BBItem(maxStackSize));
    }

    private static Item registerItem(String path, Item item) {
        item = REGISTRY.registerItem(BountifulBaubles.MOD_ID + ":" + path, path, item);
        ((IItem) item).setItemTextureName(BountifulBaubles.MOD_ID + ":" + path);
        return item;
    }

    private static void registerSlots() {
        for (SlotTypePreset preset : new SlotTypePreset[] {
            SlotTypePreset.HEAD, SlotTypePreset.NECKLACE, SlotTypePreset.BACK,
            SlotTypePreset.BODY, SlotTypePreset.HANDS, SlotTypePreset.CHARM
        }) {
            CuriosApi.registerSlotType(preset.getMessageBuilder().build());
        }
        CuriosApi.registerSlotType(SlotTypePreset.RING.getMessageBuilder().size(2).build());
    }
}
