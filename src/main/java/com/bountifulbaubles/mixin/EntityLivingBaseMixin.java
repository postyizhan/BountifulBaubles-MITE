package com.bountifulbaubles.mixin;

import com.bountifulbaubles.equipment.BBEffects;
import net.minecraft.EntityLivingBase;
import net.minecraft.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {
    @Inject(method = "addPotionEffect(Lnet/minecraft/PotionEffect;)V", at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$blockImmunePotion(PotionEffect effect, CallbackInfo info) {
        if (effect != null && !BBEffects.canApplyPotion((EntityLivingBase) (Object) this, effect.getPotionID())) {
            info.cancel();
        }
    }

    @Inject(method = "isHarmedByFire()Z", at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$fireImmunity(CallbackInfoReturnable<Boolean> cir) {
        if (BBEffects.isFireImmune((EntityLivingBase) (Object) this)) cir.setReturnValue(false);
    }

    @Inject(method = "fall(F)V", at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$fallImmunity(float distance, CallbackInfo info) {
        if (BBEffects.isFallImmune((EntityLivingBase) (Object) this)) info.cancel();
    }

    @org.spongepowered.asm.mixin.injection.ModifyVariable(method = "fall(F)V", at = @At("HEAD"), argsOnly = true)
    private float bountifulbaubles$fallResistance(float distance) {
        return BBEffects.reduceFallDistance((EntityLivingBase) (Object) this, distance);
    }

    @Inject(method = "jump()V", at = @At("TAIL"))
    private void bountifulbaubles$jumpBoost(CallbackInfo info) {
        ((EntityLivingBase) (Object) this).motionY +=
            BBEffects.getJumpBoost((EntityLivingBase) (Object) this);
    }

    @Inject(method = "onLivingUpdate()V", at = @At("TAIL"))
    private void bountifulbaubles$extendedInvulnerability(CallbackInfo info) {
        BBEffects.updateInvulnerabilityFrames((EntityLivingBase) (Object) this);
    }

    @ModifyArg(
        method = "attackEntityFromHelper(Lnet/minecraft/Damage;Lnet/minecraft/EntityDamageResult;)Lnet/minecraft/EntityDamageResult;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityLivingBase;setHealth(F)V"),
        index = 0)
    private float bountifulbaubles$preventPlayerDeath(float healthAfter) {
        EntityLivingBase entity = (EntityLivingBase) (Object) this;
        if (healthAfter <= 0.0F && entity instanceof net.minecraft.EntityPlayer) {
            net.minecraft.EntityPlayer player = (net.minecraft.EntityPlayer) entity;
            if (BBEffects.preventDeath(player, healthAfter)) return player.getHealth();
        }
        return healthAfter;
    }
}
