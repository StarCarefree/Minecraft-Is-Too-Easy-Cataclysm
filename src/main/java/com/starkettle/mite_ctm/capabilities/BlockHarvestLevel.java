package com.starkettle.mite_ctm.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class BlockHarvestLevel implements IBlockHarvestLevel{
    private int blockHarvestLevel;

    public BlockHarvestLevel(int blockHarvestLevel) {
        this.blockHarvestLevel = blockHarvestLevel;
    }

    @Override
    public int getHarvestLevel() {
        return blockHarvestLevel;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compoundTag=new CompoundTag();
        compoundTag.putInt("blockHarvestLevel",blockHarvestLevel);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        blockHarvestLevel=nbt.getInt("blockHarvestLevel");
    }
}
