package com.starkettle.mite_ctm.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.UnknownNullability;

public class ToolHarvestLevel implements IToolHarvestLevel{
    private int toolHarvestLevel;

    public ToolHarvestLevel(int toolHarvestLevel){
        this.toolHarvestLevel = toolHarvestLevel;
    }

    @Override
    public int getHarvestLevel() {
        return toolHarvestLevel;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("toolHarvestLevel", toolHarvestLevel);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        toolHarvestLevel = nbt.getInt("toolHarvestLevel");
    }
}
