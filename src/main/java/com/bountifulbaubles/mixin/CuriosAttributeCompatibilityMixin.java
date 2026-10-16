package com.bountifulbaubles.mixin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Map;
import net.minecraft.Attribute;
import net.minecraft.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import top.theillusivec4.curios.common.CuriosInventory;

/** Adapt the Curios API's Attribute keys to the name keys used by MITE's attribute map. */
@Mixin(value = CuriosInventory.class, remap = false)
public abstract class CuriosAttributeCompatibilityMixin {
    @ModifyArg(method = "applyAttributeChanges", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/BaseAttributeMap;removeAttributeModifiers(Lcom/google/common/collect/Multimap;)V"),
        index = 0)
    private Multimap<String, AttributeModifier> bountifulbaubles$removeByName(
            Multimap<Attribute, AttributeModifier> modifiers) {
        return bountifulbaubles$attributeNames(modifiers);
    }

    @ModifyArg(method = "applyAttributeChanges", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/BaseAttributeMap;applyAttributeModifiers(Lcom/google/common/collect/Multimap;)V"),
        index = 0)
    private Multimap<String, AttributeModifier> bountifulbaubles$applyByName(
            Multimap<Attribute, AttributeModifier> modifiers) {
        return bountifulbaubles$attributeNames(modifiers);
    }

    private static Multimap<String, AttributeModifier> bountifulbaubles$attributeNames(
            Multimap<Attribute, AttributeModifier> modifiers) {
        Multimap<String, AttributeModifier> named = HashMultimap.create();
        for (Map.Entry<Attribute, AttributeModifier> entry : modifiers.entries()) {
            named.put(entry.getKey().getAttributeUnlocalizedName(), entry.getValue());
        }
        return named;
    }
}
