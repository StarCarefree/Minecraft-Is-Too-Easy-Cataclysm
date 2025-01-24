package com.starkettle.mite_ctm.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {

    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "hasEnoughFoodToStartSprinting", at = @At("RETURN"), cancellable = true)
    public  void hasEnoughFoodToStartSprinting(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(this.isPassenger()||this.foodData.getFoodLevel()>0);
    }
}
