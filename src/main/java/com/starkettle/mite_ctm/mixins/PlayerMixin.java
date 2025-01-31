package com.starkettle.mite_ctm.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Shadow public int experienceLevel;

    @Shadow public float experienceProgress;

    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "getXpNeededForNextLevel", at = @At("HEAD"), cancellable = true)
    public void getXpNeededForNextLevel(CallbackInfoReturnable<Integer> cir){
        if(this.experienceLevel>=200){
            this.experienceLevel=200;
            this.experienceProgress=0f;
            cir.setReturnValue(Integer.MAX_VALUE);
        } else {
            cir.setReturnValue(10*(this.experienceLevel+1));
        }

    }

    @Inject(method = "isReducedDebugInfo", at = @At("RETURN"), cancellable = true)
    public void isReducedDebugInfo(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
    }
}
