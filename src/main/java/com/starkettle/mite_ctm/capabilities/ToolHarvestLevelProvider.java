package com.starkettle.mite_ctm.capabilities;

import com.starkettle.mite_ctm.items.ToolProperties;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToolHarvestLevelProvider implements ICapabilityProvider<ItemStack, Void, IToolHarvestLevel>, INBTSerializable<CompoundTag> {
    private IToolHarvestLevel harvestLevel;

    public IToolHarvestLevel getOrCreateCapability(@Nullable ToolProperties properties){
        if(harvestLevel==null){
            harvestLevel = new ToolHarvestLevel(properties==null?1:properties.harvestLevel);
        }
        return harvestLevel;
    }

    @Override
    public @Nullable IToolHarvestLevel getCapability(@NotNull ItemStack object, Void context) {
        String toolId="air";
        if(!object.isEmpty()) toolId=object.getItemHolder().getKey().location().getPath();
        try{
            ToolProperties properties=ToolProperties.valueOf(toolId);
            return getOrCreateCapability(properties);
        } catch (IllegalArgumentException e) {
            return getOrCreateCapability(null);
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return harvestLevel.serializeNBT(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        harvestLevel.deserializeNBT(provider, nbt);
    }
}
