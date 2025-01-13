package com.starkettle.mite_ctm.mixins;

import com.starkettle.mite_ctm.utils.CraftTickable;
import com.starkettle.mite_ctm.utils.Tickable;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(AbstractCraftingMenu.class)
public abstract class AbstractCraftingMenuMixin extends RecipeBookMenu implements Tickable, CraftTickable {
    @Shadow @Final protected CraftingContainer craftSlots;

    @Shadow @Final protected ResultContainer resultSlots;

    @Shadow protected abstract Player owner();

    @Unique
    private static int craftTickCount = 0;

    @Unique
    private static int craftingTicks = 0;

    @Unique
    private static ItemStack resultItemStack = ItemStack.EMPTY;

    public AbstractCraftingMenuMixin(MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Unique
    @Override
    public void tick(){
        ServerPlayer serverPlayer=null;
        if(this.owner() instanceof ServerPlayer serverplayer){
            serverPlayer=serverplayer;
        }
        if(serverPlayer!=null){
            Optional<RecipeHolder<CraftingRecipe>> optional = serverPlayer.getServer()
                    .getRecipeManager()
                    .getRecipeFor(RecipeType.CRAFTING, this.craftSlots.asCraftInput(), serverPlayer.level());
            if(optional.isEmpty()){
                setResultSlotAndSendS2CPacket(ItemStack.EMPTY);
                resultItemStack=ItemStack.EMPTY;
                return;
            }
            if (craftTickCount >= craftingTicks) {
                setResultSlotAndSendS2CPacket(resultItemStack.copy());
                craftTickCount=0;
            }
        }
        craftTickCount++;

    }

    @Unique
    @Override
    public int getCraftTickCount(){
        return craftTickCount;
    }

    @Unique
    @Override
    public int getCraftingTicks(){
        return craftingTicks;
    }

    @Unique
    @Override
    public void setCraftTickCount(int count){
        craftTickCount = count;
    }

    @Unique
    @Override
    public void setCraftingTicks(int ticks){
        craftingTicks = ticks;
    }

    @Unique
    @Override
    public ItemStack getResultItemStack(){
        return resultItemStack;
    }

    @Unique
    @Override
    public void setResultItemStack(ItemStack itemStack){
        resultItemStack = itemStack;
    }

    @Unique
    @Override
    public void setResultSlotAndSendS2CPacket(ItemStack itemStack){
        resultSlots.setItem(0,itemStack);
        this.setRemoteSlot(0, itemStack);
        ServerPlayer player=(ServerPlayer) this.owner();
        player.connection.send(new ClientboundContainerSetSlotPacket(this.containerId, this.incrementStateId(), 0, itemStack));
    }

}
