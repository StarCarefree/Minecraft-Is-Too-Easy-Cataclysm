package com.starkettle.mite_ctm.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.items.CraftingDifficultyProperties;
import com.starkettle.mite_ctm.utils.ITick;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingMenu.class)
public abstract class CraftingMenuMixin extends AbstractCraftingMenu implements ITick {
    @Shadow protected abstract @NotNull Player owner();

    @Shadow private boolean placingRecipe;

    @Shadow @Final private Player player;

    public CraftingMenuMixin(MenuType<?> menuType, int containerId, int width, int height) {
        super(menuType, containerId, width, height);
    }
    @Unique
    private static int craftTickCount = 0;

    @Unique
    private static int craftingTicks = 0;

    @Unique
    private static ItemStack resultItemStack = ItemStack.EMPTY;

    @Unique
    @Override
    public void tick(){
        if(!this.placingRecipe && this.player instanceof ServerPlayer serverPlayer){
            if (resultItemStack.getCount() != 0) {
                craftTickCount++;
                if (craftTickCount >= craftingTicks) {
                    setResultItemStack(resultItemStack, serverPlayer, (CraftingMenu) (Object) this);
                    craftTickCount=0;
                    craftingTicks=0;
                    resultItemStack=ItemStack.EMPTY;
                }
            }
        }
    }

    @Unique
    private static void setResultItemStack(ItemStack itemStack, ServerPlayer player, AbstractCraftingMenu menu){
        menu.getResultSlot().set(itemStack);
        menu.setRemoteSlot(0, itemStack);
        player.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemStack));
    }


    @Inject(method = "slotChangedCraftingGrid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.BEFORE), cancellable = true)
    private static void slotChangedCraftingGrid(AbstractContainerMenu menu, ServerLevel level, Player player, CraftingContainer craftSlots, ResultContainer resultSlots, RecipeHolder<CraftingRecipe> recipe, CallbackInfo ci, @Local ItemStack itemStack){
        resultItemStack=itemStack;
        if(!itemStack.isEmpty()){
            craftingTicks=CraftingDifficultyProperties.getCraftingTicks(craftSlots.asCraftInput());
            craftTickCount=0;
        }
        ci.cancel();
    }
}
