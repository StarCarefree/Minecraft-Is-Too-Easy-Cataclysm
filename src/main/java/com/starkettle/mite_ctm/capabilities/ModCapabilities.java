package com.starkettle.mite_ctm.capabilities;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class ModCapabilities {
    public static final EntityCapability<IPlayerFoodValue,Void> PLAYER_FOOD_VALUE_HANDLER = EntityCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MinecraftIsTooEasyCataclysm.MOD_ID, "player_food_value"), IPlayerFoodValue.class);
    public static final BlockCapability<IBlockHarvestLevel,Void> BLOCK_HARVEST_LEVEL_HANDLER = BlockCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MinecraftIsTooEasyCataclysm.MOD_ID, "block_harvest_level"), IBlockHarvestLevel.class);
    public static final ItemCapability<IToolHarvestLevel,Void> TOOL_HARVEST_LEVEL_HANDLER = ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MinecraftIsTooEasyCataclysm.MOD_ID, "tool_harvest_level"), IToolHarvestLevel.class);
    public static final ItemCapability<IFoodValue,Void> FOOD_VALUE_HANDLER = ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MinecraftIsTooEasyCataclysm.MOD_ID, "food_value"), IFoodValue.class);
}
