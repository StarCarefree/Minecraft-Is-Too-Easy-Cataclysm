package com.starkettle.mite_ctm.capabilities;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class ModCapabilities {
    public static final EntityCapability<PlayerFoodValue,Void> PLAYER_FOOD_VALUE_HANDLER=EntityCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MinecraftIsTooEasyCataclysm.MOD_ID, "player_food_value"), PlayerFoodValue.class);
}
