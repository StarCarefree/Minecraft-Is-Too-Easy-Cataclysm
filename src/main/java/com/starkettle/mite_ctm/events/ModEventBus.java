package com.starkettle.mite_ctm.events;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.capabilities.IPlayerFoodValue;
import com.starkettle.mite_ctm.capabilities.ModCapabilities;
import com.starkettle.mite_ctm.capabilities.PlayerFoodValue;
import com.starkettle.mite_ctm.capabilities.PlayerFoodValueProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Optional;

@EventBusSubscriber(modid = MinecraftIsTooEasyCataclysm.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBus {
    @SubscribeEvent
    public static void onCapabilityRegister(RegisterCapabilitiesEvent event) {
        event.registerEntity(
            Capabilities.ItemHandler.ENTITY,
                EntityType.PLAYER,
                new PlayerFoodValueProvider()
        );
    }
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event){
        Optional<PlayerFoodValue> optionalPlayerFoodValue=Optional.ofNullable(event.getEntity().getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER));
        optionalPlayerFoodValue.ifPresent(IPlayerFoodValue::decreaseAll);
    }
}
