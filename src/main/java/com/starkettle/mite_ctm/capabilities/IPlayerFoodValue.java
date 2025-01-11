package com.starkettle.mite_ctm.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IPlayerFoodValue extends INBTSerializable<CompoundTag> {
    //获取最大饥饿度
    int getMaxFoodLevel();
    //设置最大饥饿度
    void setMaxFoodLevel(int maxFoodLevel);
    //获取蛋白质值
    int getProtein();
    //获取植物营养素值
    int getPhytonutrients();
    //获取胰岛素抵抗值
    int getInsulinResistance();
    //设置蛋白质值
    void setProtein(int protein);
    //设置植物营养素值
    void setPhytonutrients(int phytonutrients);
    //设置胰岛素抵抗值
    void setInsulinResistance(int insulinResistance);
    //设置所有值
    default void setAll(int protein, int phytonutrients, int insulinResistance){
        setProtein(protein);
        setPhytonutrients(phytonutrients);
        setInsulinResistance(insulinResistance);
    }
    default void increaseAll(int protein, int phytonutrients, int insulinResistance){
        setAll(getProtein()+protein, getPhytonutrients()+phytonutrients, getInsulinResistance()+insulinResistance);
    }
    //将所有值减1
    default void decrease1All(){
        setAll(getProtein()-1, getPhytonutrients()-1, getInsulinResistance()-1);
    }
}
