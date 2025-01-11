package com.starkettle.mite_ctm.mixins;

import com.starkettle.mite_ctm.capabilities.ModCapabilities;
import com.starkettle.mite_ctm.capabilities.PlayerFoodValue;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow @Final private static ResourceLocation FOOD_EMPTY_SPRITE;

    @Shadow @Final private static ResourceLocation FOOD_EMPTY_HUNGER_SPRITE;

    @Shadow @Final private static ResourceLocation FOOD_HALF_HUNGER_SPRITE;

    @Shadow @Final private static ResourceLocation FOOD_FULL_HUNGER_SPRITE;

    @Shadow @Final private static ResourceLocation FOOD_HALF_SPRITE;

    @Shadow @Final private static ResourceLocation FOOD_FULL_SPRITE;

    @Shadow private int tickCount;

    @Shadow @Final private RandomSource random;

    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    public void renderFood(GuiGraphics guiGraphics, Player player, int y, int x, CallbackInfo ci){
        FoodData fooddata = player.getFoodData();
        int i = fooddata.getFoodLevel();

        for (int j = 0; j < 10; j++) {
            int k = y;
            ResourceLocation resourcelocation;
            ResourceLocation resourcelocation1;
            ResourceLocation resourcelocation2;
            if (player.hasEffect(MobEffects.HUNGER)) {
                resourcelocation = FOOD_EMPTY_HUNGER_SPRITE;
                resourcelocation1 = FOOD_HALF_HUNGER_SPRITE;
                resourcelocation2 = FOOD_FULL_HUNGER_SPRITE;
            } else {
                resourcelocation = FOOD_EMPTY_SPRITE;
                resourcelocation1 = FOOD_HALF_SPRITE;
                resourcelocation2 = FOOD_FULL_SPRITE;
            }

            if (player.getFoodData().getSaturationLevel() <= 0.0F && this.tickCount % (i * 3 + 1) == 0) {
                k = y + (this.random.nextInt(3) - 1);
            }

            int l = x - j * 8 - 9;

            Optional<PlayerFoodValue> optionalPlayerFoodValue = Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER));

            if(optionalPlayerFoodValue.isPresent()){
                if(Math.ceil(optionalPlayerFoodValue.get().getMaxFoodLevel()/2.0D)>j){
                    guiGraphics.blitSprite(RenderType::guiTextured, resourcelocation, l, k, 9, 9);
                }
            }
            else {
                guiGraphics.blitSprite(RenderType::guiTextured, resourcelocation, l, k, 9, 9);
            }

            if (j * 2 + 1 < i) {
                guiGraphics.blitSprite(RenderType::guiTextured, resourcelocation2, l, k, 9, 9);
            }

            if (j * 2 + 1 == i) {
                guiGraphics.blitSprite(RenderType::guiTextured, resourcelocation1, l, k, 9, 9);
            }
        }
        ci.cancel();
    }
}
