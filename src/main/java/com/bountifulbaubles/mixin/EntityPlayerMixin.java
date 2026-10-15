package com.bountifulbaubles.mixin;

import com.bountifulbaubles.equipment.BBEffects;
import net.minecraft.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
    @Inject(method = "setInWeb()V", at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$freeActionWeb(CallbackInfo info) {
        if (BBEffects.isWebImmune((EntityPlayer) (Object) this)) info.cancel();
    }
}
