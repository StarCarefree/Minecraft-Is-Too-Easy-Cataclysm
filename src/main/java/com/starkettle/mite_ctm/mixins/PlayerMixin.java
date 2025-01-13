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

    @Shadow protected FoodData foodData;

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

    @Inject(method = "attack" ,at = @At("HEAD"),cancellable = true)
    public void attackMixin(Entity entity, CallbackInfo ci){//取消攻击
        if (this.getHealth() <= 1.0F||this.foodData.getFoodLevel() <= 0) {
            ci.cancel();
        }
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"),index = 1)
    public float attackMixin2(float f){//攻击力每级增加0.5%
        return f+f*0.005f*this.experienceLevel;
    }
}
