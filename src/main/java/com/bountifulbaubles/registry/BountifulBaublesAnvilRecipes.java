package com.bountifulbaubles.registry;

import net.minecraft.Item;
import net.minecraft.ItemStack;

/** Custom two-item upgrades handled by the vanilla anvil container. */
public final class BountifulBaublesAnvilRecipes {
    private static Recipe[] recipes;

    private BountifulBaublesAnvilRecipes() {}

    public static synchronized void register() {
        if (recipes != null) return;
        recipes = new Recipe[] {
            recipe(BountifulBaublesRegistry.ring_overclocking,
                BountifulBaublesRegistry.shulker_heart, BountifulBaublesRegistry.ring_free_action),
            recipe(BountifulBaublesRegistry.bezoar,
                BountifulBaublesRegistry.black_dragon_scale, BountifulBaublesRegistry.mixed_dragon_scale),
            recipe(BountifulBaublesRegistry.shield_cobalt,
                BountifulBaublesRegistry.obsidian_skull, BountifulBaublesRegistry.shield_obsidian),
            recipe(BountifulBaublesRegistry.shield_obsidian,
                BountifulBaublesRegistry.ankh_charm, BountifulBaublesRegistry.shield_ankh),
            recipe(BountifulBaublesRegistry.balloon,
                BountifulBaublesRegistry.lucky_horseshoe, BountifulBaublesRegistry.horseshoe_balloon)
        };
    }

    public static ItemStack find(ItemStack left, ItemStack right) {
        if (recipes == null || left == null || right == null) return null;
        for (Recipe recipe : recipes) {
            if (recipe.matches(left.getItem(), right.getItem())) return new ItemStack(recipe.result);
        }
        return null;
    }

    private static Recipe recipe(Item left, Item right, Item result) {
        return new Recipe(left, right, result);
    }

    private static final class Recipe {
        private final Item left;
        private final Item right;
        private final Item result;

        private Recipe(Item left, Item right, Item result) {
            this.left = left;
            this.right = right;
            this.result = result;
        }

        private boolean matches(Item first, Item second) {
            return (first == left && second == right) || (first == right && second == left);
        }
    }
}
