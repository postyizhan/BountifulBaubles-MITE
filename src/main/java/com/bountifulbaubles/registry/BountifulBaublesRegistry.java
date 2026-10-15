package com.bountifulbaubles.registry;

import com.bountifulbaubles.BountifulBaubles;
import com.bountifulbaubles.item.BBCurioItem;
import com.bountifulbaubles.item.BBItem;
import com.bountifulbaubles.equipment.BBEffects;
import huix.glacier.api.entrypoint.IGameRegistry;
import huix.glacier.api.registry.MinecraftRegistry;
import net.minecraft.Item;
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
        shield_cobalt = register("shield_cobalt", "back");
        shield_obsidian = register("shield_obsidian", "back");
        BBEffects.addFireImmunity(shield_obsidian);
        shield_ankh = register("shield_ankh", "back");
        BBEffects.addFireImmunity(shield_ankh);
        balloon = register("balloon", "necklace", "ring");
        lucky_horseshoe = register("lucky_horseshoe", "necklace", "ring");
        horseshoe_balloon = register("horseshoe_balloon", "necklace", "ring");
        amulet_sin_gluttony = register("amulet_sin_gluttony", "necklace");
        amulet_sin_pride = register("amulet_sin_pride", "necklace");
        amulet_sin_wrath = register("amulet_sin_wrath", "necklace")
            .addModifier(net.minecraft.SharedMonsterAttributes.attackDamage, "wrath", 2, 0);
        broken_heart = register("broken_heart", "necklace", "ring");
        phylactery_charm = register("phylactery_charm", "necklace", "ring");
        amulet_cross = register("amulet_cross", "necklace");
        gloves_dexterity = register("gloves_dexterity", "hands");
        gloves_digging_iron = register("gloves_digging_iron", "hands");
        gloves_digging_diamond = register("gloves_digging_diamond", "hands");
        ender_dragon_scale = registerItem("ender_dragon_scale");
        broken_black_dragon_scale = registerItem("broken_black_dragon_scale");
        magic_mirror = registerItem("magic_mirror", 1);
        potion_recall = registerItem("potion_recall");
        wormhole_mirror = registerItem("wormhole_mirror", 1);
        potion_wormhole = registerItem("potion_wormhole");
        amulet_sin_empty = registerItem("amulet_sin_empty");
        disintegration_tablet = registerItem("disintegration_tablet", 1);
        spectral_silt = registerItem("spectral_silt");
        resplendent_token = registerItem("resplendent_token");
    }

    private static BBCurioItem register(String path, String... slots) {
        return (BBCurioItem) REGISTRY.registerItem(
            BountifulBaubles.MOD_ID + ":" + path, path, new BBCurioItem(slots)
        );
    }

    private static Item registerItem(String path) {
        return registerItem(path, 64);
    }

    private static Item registerItem(String path, int maxStackSize) {
        return REGISTRY.registerItem(
            BountifulBaubles.MOD_ID + ":" + path, path, new BBItem(maxStackSize)
        );
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
