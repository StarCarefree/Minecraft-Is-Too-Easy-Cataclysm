package com.starkettle.mite_ctm.mixins;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(ServerPlayer player, CallbackInfo ci) {
        ci.cancel();
    }
}
