package com.starkettle.mite_ctm.events;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.keymappings.ModKeyMappings;
import com.starkettle.mite_ctm.utils.CraftTickable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = MinecraftIsTooEasyCataclysm.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientGameEventBus {
    @SubscribeEvent
    public static void onFovChanging(ViewportEvent.ComputeFov event) {
        event.setFOV(ModKeyMappings.ZOOM_KEY_MAPPING.isDown()?30:event.getFOV());
    }
    @SubscribeEvent
    public static void onContainerMenuRender(ContainerScreenEvent.Render.Foreground event) {
        if(event.getContainerScreen().getMenu() instanceof AbstractCraftingMenu menu) {
            CraftTickable crafting = (CraftTickable) menu;
            if(!crafting.getResultItemStack().isEmpty()&&!menu.getResultSlot().hasItem()&&crafting.getCraftTickCount()<=crafting.getCraftingTicks()){
                List<Slot> slots = menu.getInputGridSlots();
                int minY = Integer.MAX_VALUE, maxX = 0, maxY = 0, textureWidth=24, textureHeight=16, resultOffset=6, arrowOffset=-1;
                ResourceLocation resourceLocation = ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");
                if(menu instanceof InventoryMenu){
                    resourceLocation=ResourceLocation.fromNamespaceAndPath(MinecraftIsTooEasyCataclysm.MOD_ID,"container/inventory/craft_progress");
                    textureWidth=18;
                    textureHeight=14;
                    resultOffset=1;
                    arrowOffset=0;
                }
                for (Slot slot : slots) {
                    maxX = Math.max(maxX, slot.x);
                    minY = Math.min(minY, slot.y);
                    maxY = Math.max(maxY, slot.y);
                }
                int pixels = Mth.ceil(((float) crafting.getCraftTickCount() / (float) crafting.getCraftingTicks()) * textureWidth);
                GuiGraphics guiGraphics = event.getGuiGraphics();

                guiGraphics.blitSprite(RenderType::guiTextured, resourceLocation, textureWidth, textureHeight, 0, 0, maxX+17+resultOffset, Mth.ceil((maxY+17+minY)/2f-textureHeight/2f)+arrowOffset, pixels, textureHeight);
            }
        }
    }
}
