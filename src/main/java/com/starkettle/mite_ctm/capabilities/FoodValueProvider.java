package com.starkettle.mite_ctm.capabilities;

import com.starkettle.mite_ctm.items.FoodProperties;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FoodValueProvider implements ICapabilityProvider<ItemStack, Void, IFoodValue>, INBTSerializable<CompoundTag> {
    IFoodValue foodValue;

    private IFoodValue getOrCreateCapability(@Nullable FoodProperties properties){
        if(properties!=null){
            foodValue = new FoodValue(properties.protein, properties.phytonutrients, properties.insulinResponse);
        } else {
            foodValue = new FoodValue(0, 0, 0);
        }
        return foodValue;
    }

    @Override
    public @Nullable IFoodValue getCapability(@NotNull ItemStack object, Void context) {
        try {
            FoodProperties properties = FoodProperties.valueOf(object.getItemHolder().getKey().location().getPath());
            return getOrCreateCapability(properties);
        } catch (IllegalArgumentException e) {
            return getOrCreateCapability(null);
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return foodValue.serializeNBT(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        foodValue.deserializeNBT(provider, nbt);
    }
}
