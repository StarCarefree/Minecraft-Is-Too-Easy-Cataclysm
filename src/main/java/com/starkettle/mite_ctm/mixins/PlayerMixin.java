package com.starkettle.mite_ctm.mixins;

import com.starkettle.mite_ctm.capabilities.ModCapabilities;
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

    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "getXpNeededForNextLevel", at = @At("HEAD"), cancellable = true)
    public void getXpNeededForNextLevel(CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(10*(this.experienceLevel+1));
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    public void aiStepMixin(CallbackInfo ci){//每64秒回一滴血（直接用tick算的,mc到底有没有秒计数啊啊啊啊啊啊啊啊啊）
        if(this.tickCount%(64*20)==0&&this.foodData.getFoodLevel()>=this.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER).getMaxFoodLevel()*0.5){
            this.heal(1.0F);
        }
        else if(this.tickCount%(256*20)==0&&this.foodData.getFoodLevel()>=this.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER).getMaxFoodLevel()*0.5){
            this.heal(1.0F);
        }
    }

    @Inject(method = "attack" ,at = @At("HEAD"),cancellable = true)
    public void attackMixin(Entity entity, CallbackInfo ci){//取消攻击
        if (this.getHealth() <= 1.0F) {
            ci.cancel();
        }
        if (this.foodData.getFoodLevel() <= 0) {
            ci.cancel();
        }
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"),index = 1)
    public float attackMixin2(float f){//攻击力每级增加0.5%
        return (float)(f*0.005*this.experienceLevel);
    }
}
