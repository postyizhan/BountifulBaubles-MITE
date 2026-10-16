package com.bountifulbaubles.mixin;

import com.bountifulbaubles.client.ShieldModelRenderer;
import com.bountifulbaubles.item.BBShieldItem;
import net.minecraft.EntityLivingBase;
import net.minecraft.ItemRenderer;
import net.minecraft.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ShieldItemRendererMixin {
    @Inject(method = "renderItem(Lnet/minecraft/EntityLivingBase;Lnet/minecraft/ItemStack;I)V",
        at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$renderHeldShield(EntityLivingBase entity, ItemStack stack, int pass, CallbackInfo ci) {
        if (stack == null || !(stack.getItem() instanceof BBShieldItem)) return;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0.5F, 0.0F);
        GL11.glScalef(1.0F / 16, 1.0F / 16, 1.0F / 16);
        ShieldModelRenderer.render(stack);
        GL11.glPopMatrix();
        ci.cancel();
    }
}
