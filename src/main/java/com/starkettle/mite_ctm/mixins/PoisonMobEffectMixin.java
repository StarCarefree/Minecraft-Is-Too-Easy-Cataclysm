package com.starkettle.mite_ctm.mixins;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.PoisonMobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PoisonMobEffect.class)
public class PoisonMobEffectMixin {
    @Inject(method = "applyEffectTick", at = @At("HEAD"), cancellable = true)
    public void applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        var dTypeReg = entity.damageSources().damageTypes;
        var dType = dTypeReg.get(NeoForgeMod.POISON_DAMAGE).orElse(dTypeReg.getOrThrow(DamageTypes.MAGIC));
        entity.hurtServer(level, new DamageSource(dType), 1.0F);
        cir.setReturnValue(true);
        cir.cancel();
    }
}
