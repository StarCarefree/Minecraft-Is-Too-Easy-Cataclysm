package com.starkettle.mite_ctm.effects;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MinecraftIsTooEasyCataclysm.MOD_ID);
    public static final DeferredHolder<MobEffect, ? extends MobEffect> INSULIN_RESISTANCE = EFFECTS.register("insulin_resistance",InsulinResistance::new);
    public static final DeferredHolder<MobEffect, ? extends MobEffect> MALNOURISHED = EFFECTS.register("malnourished",Malnourished::new);
    // public static final DeferredHolder<MobEffect, ? extends MobEffect> CURSE = EFFECTS.register("curse",);
    //TODO: more effects
    public static void register(IEventBus modEventBus){
        EFFECTS.register(modEventBus);
    }
}
