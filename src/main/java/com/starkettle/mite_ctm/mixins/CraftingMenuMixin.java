package com.starkettle.mite_ctm.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.starkettle.mite_ctm.items.CraftingDifficultyProperties;
import com.starkettle.mite_ctm.utils.ICrafting;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingMenu.class)
public abstract class CraftingMenuMixin extends AbstractCraftingMenu {
    public CraftingMenuMixin(MenuType<?> menuType, int containerId, int width, int height) {
        super(menuType, containerId, width, height);
    }

    @Inject(method = "slotChangedCraftingGrid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.BEFORE), cancellable = true)
    private static void slotChangedCraftingGrid(AbstractContainerMenu menu, ServerLevel level, Player player, CraftingContainer craftSlots, ResultContainer resultSlots, RecipeHolder<CraftingRecipe> recipe, CallbackInfo ci, @Local ItemStack itemStack){
        if(menu instanceof ICrafting crafting){
            crafting.setResultItemStack(itemStack);
            if(!itemStack.isEmpty()){
                crafting.setResultSlotAndSendS2CPacket(ItemStack.EMPTY);
                crafting.setCraftingTicks(CraftingDifficultyProperties.getCraftingTicks(craftSlots.asCraftInput(), player.experienceLevel/*这里后面可以加addBuff*/));
                crafting.setCraftTickCount(0);
            }
            ci.cancel();
        }
    }
}
