package com.bountifulbaubles.mixin;

import com.bountifulbaubles.equipment.BBEffects;
import net.minecraft.Damage;
import net.minecraft.EntityDamageResult;
import net.minecraft.EntityPlayer;
import net.minecraft.EnumItemInUseAction;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
    @Shadow protected ItemStack itemInUse;
    @Shadow protected int itemInUseCount;
    @org.spongepowered.asm.mixin.Unique private int bountifulbaubles$lastSelectedSlot = -1;

    @Inject(method = "onUpdate()V", at = @At("TAIL"))
    private void bountifulbaubles$updateStepAssist(CallbackInfo info) {
        EntityPlayer player = (EntityPlayer) (Object) this;
        BBEffects.updateStepHeight(player);
        BBEffects.updatePlayer(player);
        int selected = player.inventory == null ? -1 : player.inventory.currentItem;
        if (bountifulbaubles$lastSelectedSlot >= 0 && selected != bountifulbaubles$lastSelectedSlot
            && BBEffects.hasFastToolSwitching(player)) {
            player.attackTime = 0;
        }
        bountifulbaubles$lastSelectedSlot = selected;
    }

    @Inject(method = "onItemUseFinish()V", at = @At("HEAD"))
    private void bountifulbaubles$sinGluttony(CallbackInfo info) {
        BBEffects.onFoodEaten((EntityPlayer) (Object) this, itemInUse);
    }

    @Inject(method = "onCriticalHit(Lnet/minecraft/Entity;)V", at = @At("HEAD"))
    private void bountifulbaubles$sinWrath(net.minecraft.Entity target, CallbackInfo info) {
        BBEffects.onCriticalHit((EntityPlayer) (Object) this);
    }

    @Inject(method = "wakeUpPlayer(ZLnet/minecraft/Entity;)V", at = @At("TAIL"))
    private void bountifulbaubles$brokenHeartRecovery(boolean immediately, net.minecraft.Entity entity, CallbackInfo info) {
        BBEffects.onWakeUp((EntityPlayer) (Object) this);
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
    private void bountifulbaubles$reduceFireDamage(Damage damage, CallbackInfoReturnable<EntityDamageResult> cir) {
        BBEffects.reduceFireDamage((EntityPlayer) (Object) this, damage);
    }

    @Inject(method = "setInWeb()V", at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$freeActionWeb(CallbackInfo info) {
        if (BBEffects.isWebImmune((EntityPlayer) (Object) this)) info.cancel();
    }
}
