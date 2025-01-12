package com.starkettle.mite_ctm.mixins;

import com.starkettle.mite_ctm.blocks.BlockProperties;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
    @Shadow public abstract BlockBehaviour.Properties properties();

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    private void Constructor(BlockBehaviour.Properties properties, CallbackInfo ci){
        String blockId=((BlockBehaviourPropertiesAccessor)properties).getId().location().getPath();
        try{
            BlockProperties blockProperties = BlockProperties.valueOf(blockId);
            properties.strength(blockProperties.strength);
        } catch (IllegalArgumentException ignored){
        }
    }

}
