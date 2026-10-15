package com.bountifulbaubles.registry;

import net.minecraft.Block;
import net.minecraft.CraftingManager;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import moddedmite.rustedironcore.api.event.Handlers;
import moddedmite.rustedironcore.api.event.listener.IInitializationListener;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.reload.transform.util.CraftingManagerInvoker;

/** Registers the portable workbench recipes from the original BB data pack. */
public final class BountifulBaublesRecipes {
    private static boolean registered;

    public static void register() {
        Handlers.Initialization.register(new IInitializationListener() {
            @Override
            public void onServerStarted(MinecraftServer server) {
                BountifulBaublesAnvilRecipes.register();
                registerRecipes();
            }
        });
    }

    private static synchronized void registerRecipes() {
        if (registered) return;
        CraftingManagerInvoker recipes = (CraftingManagerInvoker) CraftingManager.getInstance();
        recipes.addRecipe(stack(BountifulBaublesRegistry.amulet_sin_gluttony), true,
            "c", "a", "g", 'c', Item.cake, 'a', BountifulBaublesRegistry.amulet_sin_empty,
            'g', Item.appleGold);
        recipes.addRecipe(stack(BountifulBaublesRegistry.amulet_sin_pride), true,
            " d ", "dad", " g ", 'd', Item.diamond, 'a', BountifulBaublesRegistry.amulet_sin_empty,
            'g', Block.blockGold);
        recipes.addRecipe(stack(BountifulBaublesRegistry.amulet_sin_wrath), true,
            " b ", "bab", " h ", 'b', Item.bone, 'a', BountifulBaublesRegistry.amulet_sin_empty,
            'h', skulls());
        recipes.addRecipe(stack(BountifulBaublesRegistry.ankh_charm), true,
            "glg", "fsa", "gvg", 'g', Block.blockGold, 'l', BountifulBaublesRegistry.sunglasses,
            'f', BountifulBaublesRegistry.ring_free_action, 's', BountifulBaublesRegistry.mixed_dragon_scale,
            'a', BountifulBaublesRegistry.apple, 'v', BountifulBaublesRegistry.vitamins);
        recipes.addRecipe(stack(BountifulBaublesRegistry.black_dragon_scale), true,
            "n", "e", "b", 'n', Item.netherStar, 'e', BountifulBaublesRegistry.ender_dragon_scale,
            'b', BountifulBaublesRegistry.broken_black_dragon_scale);
        recipes.addRecipe(stack(BountifulBaublesRegistry.disintegration_tablet), true,
            "qbq", "brb", "qbq", 'q', Item.netherQuartz, 'b', Item.blazePowder, 'r', Item.redstone);
        recipes.addRecipe(stack(BountifulBaublesRegistry.gloves_digging_diamond), true,
            "ddd", "lIl", "lll", 'd', Item.diamond, 'I', Block.blockIron, 'l', Item.leather);
        recipes.addRecipe(stack(BountifulBaublesRegistry.gloves_digging_iron), true,
            "iii", "lil", "lll", 'i', Item.ingotIron, 'l', Item.leather);
        recipes.addRecipe(stack(BountifulBaublesRegistry.obsidian_skull), true,
            "odo", "psp", "odo", 'o', Block.obsidian, 'd', Item.blazePowder,
            'p', Item.magmaCream, 's', skulls());
        recipes.addShapelessRecipe(stack(BountifulBaublesRegistry.phylactery_charm), true,
            ItemStack.copyItemStack(new ItemStack(Item.skull, 1, 1)), Item.diamond,
            BountifulBaublesRegistry.magic_mirror, BountifulBaublesRegistry.broken_heart);
        recipes.addRecipe(stack(BountifulBaublesRegistry.resplendent_token), true,
            " s ", "sgs", " s ", 's', BountifulBaublesRegistry.spectral_silt, 'g', Item.ingotGold);
        recipes.addRecipe(stack(BountifulBaublesRegistry.wormhole_mirror, 1), true,
            "owo", "pMp", "opo", 'o', Block.obsidian, 'w', BountifulBaublesRegistry.ender_dragon_scale,
            'M', BountifulBaublesRegistry.magic_mirror, 'p', BountifulBaublesRegistry.potion_wormhole);
        registered = true;
    }

    private static ItemStack stack(Item item) {
        return stack(item, 1);
    }

    private static ItemStack stack(Item item, int count) {
        return new ItemStack(item, count);
    }

    private static ItemStack skulls() {
        return new ItemStack(Item.skull, 1, 32767);
    }
}
