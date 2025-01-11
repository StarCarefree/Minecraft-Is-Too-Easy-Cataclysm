package com.starkettle.mite_ctm.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IPlayerFoodValue extends INBTSerializable<CompoundTag> {
    int getProtein();
    int getPhytonutrients();
    int getInsulinResistance();
    void setProtein(int protein);
    void setPhytonutrients(int phytonutrients);
    void setInsulinResistance(int insulinResistance);
    default void setAll(int protein, int phytonutrients, int insulinResistance){
        setProtein(protein);
        setPhytonutrients(phytonutrients);
        setInsulinResistance(insulinResistance);
    }
    default void decreaseAll(){
        setAll(getProtein()-1, getPhytonutrients()-1, getInsulinResistance()-1);
    }
}
