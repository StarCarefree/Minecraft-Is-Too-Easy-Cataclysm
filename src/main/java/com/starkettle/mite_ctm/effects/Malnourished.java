package com.starkettle.mite_ctm.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Malnourished extends MobEffect {
    protected Malnourished() {
        super(MobEffectCategory.HARMFUL, 0xff0000);
    }
}
