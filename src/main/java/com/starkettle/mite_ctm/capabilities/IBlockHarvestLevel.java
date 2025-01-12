package com.starkettle.mite_ctm.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IBlockHarvestLevel extends INBTSerializable<CompoundTag> {
    int getHarvestLevel();
}
