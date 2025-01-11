package com.starkettle.mite_ctm.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class PlayerFoodValue implements IPlayerFoodValue{
    private final int MAX_PROTEIN = 160000, MAX_PHYTONUTRIENTS = 160000, MAX_INSULIN_RESISTANCE = 192000;

    private int protein=MAX_PROTEIN, phytonutrients=MAX_PHYTONUTRIENTS, insulinResistance=0, maxFoodLevel=6;

    @Override
    public int getMaxFoodLevel() {
        return maxFoodLevel;
    }

    @Override
    public void setMaxFoodLevel(int maxFoodLevel) {
        this.maxFoodLevel = maxFoodLevel;
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
    public int getInsulinResistance() {
        return insulinResistance;
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
    public void setInsulinResistance(int insulinResistance) {
        this.insulinResistance = Mth.clamp(insulinResistance, 0, MAX_INSULIN_RESISTANCE);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("insulinResistance", insulinResistance);
        compoundTag.putInt("phytonutrients", phytonutrients);
        compoundTag.putInt("protein", protein);
        compoundTag.putInt("maxFoodLevel", maxFoodLevel);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        insulinResistance = nbt.getInt("insulinResistance");
        phytonutrients = nbt.getInt("phytonutrients");
        protein = nbt.getInt("protein");
        maxFoodLevel = nbt.getInt("maxFoodLevel");
    }
}
