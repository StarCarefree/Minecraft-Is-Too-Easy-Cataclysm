package com.starkettle.mite_ctm.capabilities;

import com.starkettle.mite_ctm.blocks.BlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

public class BlockHarvestLevelProvider implements IBlockCapabilityProvider<IBlockHarvestLevel,Void>, INBTSerializable<CompoundTag> {
    private IBlockHarvestLevel harvestLevel;

    public IBlockHarvestLevel getOrCreateCapability(BlockProperties properties){
        if(harvestLevel==null){
            harvestLevel = new BlockHarvestLevel(properties==null?0:properties.harvestLevel);
        }
        return harvestLevel;
    }

    @Override
    public @Nullable IBlockHarvestLevel getCapability(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, Void context) {
        String blockId=state.getBlockHolder().getKey().location().getPath();
        try{
            BlockProperties properties= BlockProperties.valueOf(blockId);
            return getOrCreateCapability(properties);
        } catch (IllegalArgumentException e) {
            return getOrCreateCapability(null);
        }
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return getOrCreateCapability(null).serializeNBT(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        getOrCreateCapability(null).deserializeNBT(provider, nbt);
    }
}
