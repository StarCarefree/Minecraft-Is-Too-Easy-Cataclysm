package com.starkettle.mite_ctm.mixins;

import com.starkettle.mite_ctm.blocks.BlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin implements FeatureElement {
    @Shadow public abstract BlockBehaviour.Properties properties();

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    public void Constructor(BlockBehaviour.Properties properties, CallbackInfo ci){
        ResourceKey<Block> resourceKey=((BlockBehaviourPropertiesAccessor)properties).getId();
        if(resourceKey==null) return;
        String blockId=resourceKey.location().getPath();
        try{
            BlockProperties blockProperties = BlockProperties.valueOf(blockId);
            properties.strength(blockProperties.strength);
        } catch (IllegalArgumentException ignored){
        }
    }
    @Inject(method = "updateShape", at = @At("HEAD"))
    public void updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random, CallbackInfoReturnable<BlockState> cir){
        if(state.getBlock() == Blocks.DIRT){
            scheduledTickAccess.scheduleTick(pos,state.getBlock(),2);
        }
    }
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci){
        if(state.getBlock() == Blocks.DIRT){
            if(FallingBlock.isFree(level.getBlockState(pos.below()))&&level.isInWorldBounds(pos)){
                FallingBlockEntity.fall(level,pos,state);
            }
        }
    }
    @Inject(method = "onPlace", at = @At("HEAD"))
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston, CallbackInfo ci){
        if (state.getBlock() == Blocks.DIRT) {
            level.scheduleTick(pos, state.getBlock(), 2);
        }
    }

}
