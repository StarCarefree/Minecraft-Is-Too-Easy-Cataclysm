package com.starkettle.mite_ctm.events;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.keymappings.ModKeyMappings;
import com.starkettle.mite_ctm.utils.ITick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = MinecraftIsTooEasyCataclysm.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientGameEventBus {
    @SubscribeEvent
    public static void onFovChanging(ViewportEvent.ComputeFov event) {
        event.setFOV(ModKeyMappings.ZOOM_KEY_MAPPING.isDown()?30:event.getFOV());
    }
}
