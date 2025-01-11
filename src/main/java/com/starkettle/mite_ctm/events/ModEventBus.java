package com.starkettle.mite_ctm.events;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.capabilities.ModCapabilities;
import com.starkettle.mite_ctm.capabilities.PlayerFoodValueProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
@EventBusSubscriber(modid = MinecraftIsTooEasyCataclysm.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBus {
    @SubscribeEvent
    public static void onCapabilityRegistering(RegisterCapabilitiesEvent event) {
        event.registerEntity(
            ModCapabilities.PLAYER_FOOD_VALUE_HANDLER,
                EntityType.PLAYER,
                new PlayerFoodValueProvider()
        );
    }
}
