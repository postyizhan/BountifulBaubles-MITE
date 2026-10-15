package com.bountifulbaubles.mixin;

import com.bountifulbaubles.equipment.BBEffects;
import net.minecraft.Damage;
import net.minecraft.EntityPlayer;
import net.minecraft.EnumItemInUseAction;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
    @Shadow protected ItemStack itemInUse;
    @Shadow protected int itemInUseCount;

    @Inject(method = "onUpdate()V", at = @At("TAIL"))
    private void bountifulbaubles$updateStepAssist(CallbackInfo info) {
        BBEffects.updateStepHeight((EntityPlayer) (Object) this);
    }

    @Inject(method = "updateItemUse(Lnet/minecraft/ItemStack;I)V", at = @At("HEAD"))
    private void bountifulbaubles$fastFood(ItemStack stack, int ticks, CallbackInfo info) {
        EntityPlayer player = (EntityPlayer) (Object) this;
        if (itemInUse != null && BBEffects.hasFastFood(player)) {
            EnumItemInUseAction action = itemInUse.getItemInUseAction(player);
            if ((action == EnumItemInUseAction.EAT || action == EnumItemInUseAction.DRINK)
                && itemInUseCount > 7) {
                itemInUseCount = 7;
            }
        }
    }

    @Inject(method = "attackEntityFrom(Lnet/minecraft/Damage;)Lnet/minecraft/EntityDamageResult;", at = @At("HEAD"))
    private void bountifulbaubles$reduceFireDamage(Damage damage, CallbackInfo info) {
        BBEffects.reduceFireDamage((EntityPlayer) (Object) this, damage);
    }

    @Inject(method = "setInWeb()V", at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$freeActionWeb(CallbackInfo info) {
        if (BBEffects.isWebImmune((EntityPlayer) (Object) this)) info.cancel();
    }
}
