package com.bountifulbaubles.mixin;

import com.bountifulbaubles.client.ShieldModelRenderer;
import com.bountifulbaubles.item.BBShieldItem;
import net.minecraft.EntityItem;
import net.minecraft.FontRenderer;
import net.minecraft.ItemStack;
import net.minecraft.RenderItem;
import net.minecraft.TextureManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class ShieldRenderItemMixin {
    @Shadow public float zLevel;
    @Inject(method = "renderItemIntoGUI(Lnet/minecraft/FontRenderer;Lnet/minecraft/TextureManager;Lnet/minecraft/ItemStack;II)V",
        at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$renderShieldIcon(FontRenderer font, TextureManager textures, ItemStack stack,
                                                  int x, int y, CallbackInfo ci) {
        if (stack == null || !(stack.getItem() instanceof BBShieldItem)) return;
        GL11.glPushMatrix();
        GL11.glTranslatef(x + 8, y + 8, 100 + zLevel);
        GL11.glScalef(0.6F, -0.6F, 0.6F);
        GL11.glRotatef(15, 1, 0, 0);
        GL11.glRotatef(-25, 0, 1, 0);
        GL11.glRotatef(-5, 0, 0, 1);
        ShieldModelRenderer.render(stack);
        GL11.glPopMatrix();
        ci.cancel();
    }

    @Inject(method = "doRenderItem(Lnet/minecraft/EntityItem;DDDFF)V", at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$renderDroppedShield(EntityItem entity, double x, double y, double z,
                                                      float yaw, float partialTick, CallbackInfo ci) {
        ItemStack stack = entity.getEntityItem();
        if (stack == null || !(stack.getItem() instanceof BBShieldItem)) return;
        GL11.glPushMatrix();
        float angle = (entity.age + partialTick) / 20.0F + entity.hoverStart;
        GL11.glTranslated(x, y + 0.25 + Math.sin(angle * 2) * 0.1, z);
        GL11.glRotatef(angle * 57.29578F, 0, 1, 0);
        GL11.glScalef(1.0F / 32, 1.0F / 32, 1.0F / 32);
        ShieldModelRenderer.render(stack);
        GL11.glPopMatrix();
        ci.cancel();
    }
}
