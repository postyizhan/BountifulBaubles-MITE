package com.bountifulbaubles.registry;

import com.bountifulbaubles.BountifulBaubles;
import com.bountifulbaubles.item.BBCurioItem;
import com.bountifulbaubles.item.BBItem;
import com.bountifulbaubles.item.BBShieldItem;
import com.bountifulbaubles.item.BBTeleportItem;
import com.bountifulbaubles.item.DiggingGlovesItem;
import com.bountifulbaubles.item.PhylacteryCharmItem;
import com.bountifulbaubles.equipment.BBEffects;
import huix.glacier.api.entrypoint.IGameRegistry;
import huix.glacier.api.registry.MinecraftRegistry;
import net.minecraft.Block;
import net.minecraft.CreativeTabs;
import net.minecraft.Item;
import net.minecraft.Material;
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
        BBEffects.initialize();
        registerSlots();
        sunglasses = register("sunglasses", Material.glass, "head");
        BBEffects.addPotionImmunity(sunglasses, net.minecraft.Potion.blindness.id);
        apple = register("apple", Material.fruit, "necklace", "ring");
        BBEffects.addPotionImmunity(apple, net.minecraft.Potion.hunger.id, net.minecraft.Potion.confusion.id);
        vitamins = register("vitamins", Material.glass, "necklace", "ring");
        BBEffects.addPotionImmunity(vitamins, net.minecraft.Potion.weakness.id, net.minecraft.Potion.digSlowdown.id);
        ring_overclocking = register("ring_overclocking", Material.iron, "ring")
            .addModifier(net.minecraft.SharedMonsterAttributes.movementSpeed, "overclocking", 0.07, 2);
        BBEffects.addPotionImmunity(ring_overclocking, net.minecraft.Potion.moveSlowdown.id);
        shulker_heart = register("shulker_heart", Material.meat, "necklace", "ring");
        BBEffects.addPotionImmunity(shulker_heart, 25);
        ring_free_action = register("ring_free_action", Material.iron, "ring");
        BBEffects.addPotionImmunity(ring_free_action, net.minecraft.Potion.moveSlowdown.id, 25);
        BBEffects.addWebImmunity(ring_free_action);
        bezoar = register("bezoar", Material.stone, "necklace", "ring");
        BBEffects.addPotionImmunity(bezoar, net.minecraft.Potion.poison.id);
        black_dragon_scale = register("black_dragon_scale", Material.bone, "necklace", "ring");
        BBEffects.addPotionImmunity(black_dragon_scale, net.minecraft.Potion.wither.id);
        mixed_dragon_scale = register("mixed_dragon_scale", Material.bone, "necklace", "ring");
        BBEffects.addPotionImmunity(mixed_dragon_scale, net.minecraft.Potion.poison.id, net.minecraft.Potion.wither.id);
        ankh_charm = register("ankh_charm", Material.gold, "necklace", "ring");
        BBEffects.addPotionImmunity(ankh_charm, net.minecraft.Potion.blindness.id, net.minecraft.Potion.hunger.id,
            net.minecraft.Potion.confusion.id, net.minecraft.Potion.weakness.id, net.minecraft.Potion.digSlowdown.id,
            net.minecraft.Potion.moveSlowdown.id, net.minecraft.Potion.poison.id, net.minecraft.Potion.wither.id, 25);
        obsidian_skull = register("obsidian_skull", Material.obsidian, "necklace", "ring");
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
            net.minecraft.Potion.moveSlowdown.id, net.minecraft.Potion.poison.id, net.minecraft.Potion.wither.id, 25);
        balloon = register("balloon", Material.cloth, "necklace", "ring");
        BBEffects.addFallResistance(balloon, 4.0F);
        BBEffects.addJumpBoost(balloon, 0.2F);
        lucky_horseshoe = register("lucky_horseshoe", Material.iron, "necklace", "ring");
        BBEffects.addFallImmunity(lucky_horseshoe);
        horseshoe_balloon = register("horseshoe_balloon", Material.iron, "necklace", "ring");
        BBEffects.addFallImmunity(horseshoe_balloon);
        BBEffects.addJumpBoost(horseshoe_balloon, 0.2F);
        amulet_sin_gluttony = register("amulet_sin_gluttony", Material.gold, "necklace");
        BBEffects.addFastFood(amulet_sin_gluttony);
        BBEffects.BountifulBaublesRegistryAccess.GLUTTONY = amulet_sin_gluttony;
        amulet_sin_pride = register("amulet_sin_pride", Material.gold, "necklace");
        BBEffects.addStepAssist(amulet_sin_pride);
        BBEffects.BountifulBaublesRegistryAccess.PRIDE = amulet_sin_pride;
        amulet_sin_wrath = register("amulet_sin_wrath", Material.gold, "necklace")
            .addModifier(net.minecraft.SharedMonsterAttributes.attackDamage, "wrath", 2, 0);
        BBEffects.BountifulBaublesRegistryAccess.WRATH = amulet_sin_wrath;
        broken_heart = register("broken_heart", Material.meat, "necklace", "ring");
        BBEffects.addBrokenHeart(broken_heart);
        PhylacteryCharmItem phylactery = new PhylacteryCharmItem();
        phylactery.setMaterial(Material.diamond);
        phylactery_charm = registerCurio("phylactery_charm", phylactery);
        BBEffects.addPhylactery(phylactery_charm);
        amulet_cross = register("amulet_cross", Material.gold, "necklace");
        BBEffects.addExtendedInvulnerability(amulet_cross);
        gloves_dexterity = register("gloves_dexterity", Material.leather, "hands");
        BBEffects.addFastToolSwitching(gloves_dexterity);
        gloves_digging_iron = registerCurio("gloves_digging_iron", new DiggingGlovesItem(false));
        gloves_digging_diamond = registerCurio("gloves_digging_diamond", new DiggingGlovesItem(true));
        ender_dragon_scale = registerItem("ender_dragon_scale", Material.bone);
        broken_black_dragon_scale = registerItem("broken_black_dragon_scale", Material.bone);
        magic_mirror = registerItem("magic_mirror", new BBTeleportItem(BBTeleportItem.Kind.MAGIC_MIRROR).setMaterial(Material.glass));
        potion_recall = registerItem("potion_recall", new BBTeleportItem(BBTeleportItem.Kind.RECALL_POTION).setMaterial(Material.glass));
        wormhole_mirror = registerItem("wormhole_mirror", new BBTeleportItem(BBTeleportItem.Kind.WORMHOLE_MIRROR).setMaterial(Material.glass));
        potion_wormhole = registerItem("potion_wormhole", new BBTeleportItem(BBTeleportItem.Kind.WORMHOLE_POTION).setMaterial(Material.glass));
        amulet_sin_empty = registerItem("amulet_sin_empty", Material.gold);
        disintegration_tablet = registerItem("disintegration_tablet", new BBItem(1).setMaterial(Material.quartz));
        spectral_silt = registerItem("spectral_silt", Material.sand);
        resplendent_token = registerItem("resplendent_token", Material.gold);
    }

    private static BBCurioItem register(String path, Material material, String... slots) {
        BBCurioItem item = new BBCurioItem(slots);
        item.setMaterial(material);
        return registerCurio(path, item);
    }

    private static BBCurioItem registerCurio(String path, BBCurioItem item) {
        BBCurioItem registered = (BBCurioItem) registerItem(path, item);
        for (String slot : registered.getSlotTypes()) CuriosApi.registerCurioTag(registered, slot);
        return registered;
    }

    private static Item registerItem(String path, Material material) {
        return registerItem(path, new BBItem(64).setMaterial(material));
    }

    private static Item registerItem(String path, Item item) {
        item = REGISTRY.registerItem(BountifulBaubles.MOD_ID + ":" + path,
            BountifulBaubles.MOD_ID + "." + path, item);
        item.setCreativeTab(CreativeTabs.tabMisc);
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
