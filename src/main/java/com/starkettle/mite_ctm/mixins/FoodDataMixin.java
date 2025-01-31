package com.starkettle.mite_ctm.mixins;

import com.starkettle.mite_ctm.capabilities.IPlayerFoodValue;
import com.starkettle.mite_ctm.capabilities.ModCapabilities;
import com.starkettle.mite_ctm.effects.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {
    @Shadow private int foodLevel;

    @Shadow private int tickTimer;

    @Shadow public abstract void addExhaustion(float exhaustion);

    @Shadow private float saturationLevel;

    @Shadow private float exhaustionLevel;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(ServerPlayer player, CallbackInfo ci) {
        ServerLevel serverlevel = player.serverLevel();
        Difficulty difficulty = serverlevel.getDifficulty();
        IPlayerFoodValue playerFoodValue=player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER);
        int decreaseFoodLevel = 1;
        float decreaseSaturationLevel = 1.0F;
        if(player.hasEffect(ModEffects.MALNOURISHED)) {
            decreaseFoodLevel *= 2;
            decreaseSaturationLevel *= 2.0F;
        }
        if (this.exhaustionLevel > 4.0F) {
            this.exhaustionLevel -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - decreaseSaturationLevel, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - decreaseFoodLevel, 0);
            }
        }

        boolean flag = serverlevel.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        if (flag && this.foodLevel >= playerFoodValue.getMaxFoodLevel()*0.5F && player.isHurt()) {
            this.tickTimer++;
            if (this.tickTimer >= 64 * 20 && !player.hasEffect(ModEffects.MALNOURISHED)) {
                player.heal(1.0F);
                this.addExhaustion(6.0F);
                this.tickTimer = 0;
            } else if (this.tickTimer >= 256 * 20 && player.hasEffect(ModEffects.MALNOURISHED)){
                player.heal(1.0F);
                this.addExhaustion(6.0F);
                this.tickTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            this.tickTimer++;
            if (this.tickTimer >= 80) {
                player.hurtServer(serverlevel, player.damageSources().starve(), 1.0F);
                this.tickTimer = 0;
            }
        } else {
            this.tickTimer = 0;
        }
        ci.cancel();
    }
}
