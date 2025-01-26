package com.starkettle.mite_ctm.events;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.keymappings.ModKeyMappings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = MinecraftIsTooEasyCataclysm.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventBus {
    @SubscribeEvent
    public static void onKeyMappingsRegistering(RegisterKeyMappingsEvent event) {
        event.register(ModKeyMappings.ZOOM_KEY_MAPPING);
    }
}
