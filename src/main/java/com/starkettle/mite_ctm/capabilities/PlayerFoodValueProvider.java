package com.starkettle.mite_ctm.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFoodValueProvider implements ICapabilityProvider<Player,Void,IPlayerFoodValue>, INBTSerializable<CompoundTag> {
    private IPlayerFoodValue foodValue = null;

    private IPlayerFoodValue getOrCreatePlayerFoodValue(){
        if(this.foodValue == null){
            foodValue = new PlayerFoodValue();
        }
        return foodValue;
    }

    @Override
    public @Nullable IPlayerFoodValue getCapability(@NotNull Player object, Void context) {
        return getOrCreatePlayerFoodValue();
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return getOrCreatePlayerFoodValue().serializeNBT(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        getOrCreatePlayerFoodValue().deserializeNBT(provider, nbt);
    }
}
