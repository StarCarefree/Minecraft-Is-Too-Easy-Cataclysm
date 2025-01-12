package com.starkettle.mite_ctm.capabilities;

import com.starkettle.mite_ctm.items.ToolProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToolHarvestLevelProvider implements ICapabilityProvider<ItemStack, Void, IToolHarvestLevel> {
    private IToolHarvestLevel harvestLevel;

    public IToolHarvestLevel getOrCreateCapability(ToolProperties properties){
        if(harvestLevel==null){
            harvestLevel = new ToolHarvestLevel(properties==null?1:properties.harvestLevel);
        }
        return harvestLevel;
    }

    @Override
    public @Nullable IToolHarvestLevel getCapability(@NotNull ItemStack object, Void context) {
        String toolId=object.getItemHolder().getKey().location().getPath();
        if(object.isEmpty()) toolId=Items.AIR.builtInRegistryHolder().getKey().location().getPath();
        try{
            ToolProperties properties=ToolProperties.valueOf(toolId);
            return getOrCreateCapability(properties);
        } catch (IllegalArgumentException e) {
            return getOrCreateCapability(null);
        }
    }
}
