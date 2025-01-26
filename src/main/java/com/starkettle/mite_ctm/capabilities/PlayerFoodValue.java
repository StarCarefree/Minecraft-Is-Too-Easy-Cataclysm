package com.starkettle.mite_ctm.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class PlayerFoodValue implements IPlayerFoodValue{
    public static final int MAX_PROTEIN = 160000, MAX_PHYTONUTRIENTS = 160000, MAX_INSULIN_RESPONSE = 192000;

    private int protein=MAX_PROTEIN, phytonutrients=MAX_PHYTONUTRIENTS, insulinResponse =0, maxFoodLevel=6;
    private float maxSaturationLevel=6f;

    @Override
    public int getMaxFoodLevel() {
        return maxFoodLevel;
    }

    @Override
    public void setMaxFoodLevel(int maxFoodLevel) {
        this.maxFoodLevel = maxFoodLevel;
    }

    @Override
    public float getMaxSaturationLevel() {
        return maxSaturationLevel;
    }

    @Override
    public void setMaxSaturationLevel(float maxSaturationLevel) {
        this.maxSaturationLevel=maxSaturationLevel;
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
    public void setProtein(int protein) {
        this.protein = Mth.clamp(protein, 0, MAX_PROTEIN);
    }

    @Override
    public void setPhytonutrients(int phytonutrients) {
        this.phytonutrients = Mth.clamp(phytonutrients, 0, MAX_PHYTONUTRIENTS);
    }

    @Override
    public void setInsulinResponse(int insulinResponse) {
        this.insulinResponse = Mth.clamp(insulinResponse, 0, MAX_INSULIN_RESPONSE);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("insulinResponse", insulinResponse);
        compoundTag.putInt("phytonutrients", phytonutrients);
        compoundTag.putInt("protein", protein);
        compoundTag.putInt("maxFoodLevel", maxFoodLevel);
        compoundTag.putFloat("maxSaturationLevel", maxSaturationLevel);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        insulinResponse = nbt.getInt("insulinResponse");
        phytonutrients = nbt.getInt("phytonutrients");
        protein = nbt.getInt("protein");
        maxFoodLevel = nbt.getInt("maxFoodLevel");
        maxSaturationLevel = nbt.getFloat("maxSaturationLevel");
    }
}
