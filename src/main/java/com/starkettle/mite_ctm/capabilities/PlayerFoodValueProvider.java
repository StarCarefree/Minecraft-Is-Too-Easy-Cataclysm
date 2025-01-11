package com.starkettle.mite_ctm.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

public class PlayerFoodValueProvider implements ICapabilityProvider<Player,Void,PlayerFoodValue>, INBTSerializable<CompoundTag> {
    private PlayerFoodValue foodValue = null;

    private PlayerFoodValue getOrCreatePlayerFoodValue(){
        if(this.foodValue == null){
            foodValue = new PlayerFoodValue();
        }
        return foodValue;
    }

    @Override
    public @Nullable PlayerFoodValue getCapability(@NotNull Player object, Void context) {
        return getOrCreatePlayerFoodValue();
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return getOrCreatePlayerFoodValue().serializeNBT(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        getOrCreatePlayerFoodValue().deserializeNBT(provider, nbt);
    }
}
