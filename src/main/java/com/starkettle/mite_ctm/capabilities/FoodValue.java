package com.starkettle.mite_ctm.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class FoodValue implements IFoodValue{
    private int protein, phytonutrients, insulinResponse;

    public FoodValue(int protein, int phytonutrients, int insulinResponse) {
        this.protein = protein;
        this.phytonutrients = phytonutrients;
        this.insulinResponse = insulinResponse;
    }

    @Override
    public int getProtein() {
        return protein;
    }

    @Override
    public int getPhytonutrients() {
        return phytonutrients;
    }

    @Override
    public int getInsulinResponse() {
        return insulinResponse;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag compoundTag=new CompoundTag();
        compoundTag.putInt("protein",protein);
        compoundTag.putInt("phytonutrients",phytonutrients);
        compoundTag.putInt("insulinResponse",insulinResponse);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        protein=nbt.getInt("protein");
        phytonutrients=nbt.getInt("phytonutrients");
        insulinResponse=nbt.getInt("insulinResponse");
    }
}
