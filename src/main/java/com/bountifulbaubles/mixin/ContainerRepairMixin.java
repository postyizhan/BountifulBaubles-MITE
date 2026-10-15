package com.bountifulbaubles.mixin;

import com.bountifulbaubles.registry.BountifulBaublesAnvilRecipes;
import net.minecraft.ContainerRepair;
import net.minecraft.IInventory;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerRepair.class)
public abstract class ContainerRepairMixin {
    @Shadow private IInventory outputSlot;
    @Shadow private IInventory inputSlots;
    @Shadow private int stackSizeToBeUsedInRepair;

    @Inject(method = "updateRepairOutput()V", at = @At("HEAD"), cancellable = true)
    private void bountifulbaubles$customAnvilRecipe(CallbackInfo info) {
        ItemStack result = BountifulBaublesAnvilRecipes.find(
            inputSlots.getStackInSlot(0), inputSlots.getStackInSlot(1));
        if (result == null) return;
        outputSlot.setInventorySlotContents(0, result);
        stackSizeToBeUsedInRepair = 0;
        info.cancel();
    }
}
