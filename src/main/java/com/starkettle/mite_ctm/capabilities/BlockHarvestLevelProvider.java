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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockHarvestLevelProvider implements IBlockCapabilityProvider<IBlockHarvestLevel,Void>, INBTSerializable<CompoundTag> {
    private IBlockHarvestLevel harvestLevel;

    public IBlockHarvestLevel getOrCreateCapability(@Nullable BlockProperties properties){
        if(harvestLevel==null){
            harvestLevel = new BlockHarvestLevel(properties==null?0:properties.harvestLevel);
        }
        return harvestLevel;
    }

    @Override
    public @Nullable IBlockHarvestLevel getCapability(@NotNull Level level, @NotNull BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, Void context) {
        String blockId=state.getBlockHolder().getKey().location().getPath();
        try{
            BlockProperties properties= BlockProperties.valueOf(blockId);
            return getOrCreateCapability(properties);
        } catch (IllegalArgumentException e) {
            return getOrCreateCapability(null);
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return getOrCreateCapability(null).serializeNBT(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        getOrCreateCapability(null).deserializeNBT(provider, nbt);
    }
}
