package com.bountifulbaubles.mixin;

import com.bountifulbaubles.item.BBShieldItem;
import net.minecraft.EntityDamageResult;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.Damage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Damage.class)
public abstract class DamageShieldMixin {
    @Inject(
        method = "applyTargetDefenseModifiers(Lnet/minecraft/EntityLivingBase;Lnet/minecraft/EntityDamageResult;)F",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/EntityPlayer;getHeldItemStack()Lnet/minecraft/ItemStack;",
            ordinal = 0
        )
    )
    private void bountifulbaubles$damageShieldOnBlock(
        EntityLivingBase target, EntityDamageResult result, CallbackInfoReturnable<Float> cir) {
        if (target instanceof EntityPlayer) {
            BBShieldItem.damageOnBlock((EntityPlayer) target, result);
        }
    }
}
