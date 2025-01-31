package com.starkettle.mite_ctm.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IPlayerFoodValue extends INBTSerializable<CompoundTag> {
    void toggleIncreasingBlocked();
    //是否被禁止增加
    boolean isIncreasingBlocked();
    //获取最大饥饿值
    int getMaxFoodLevel();
    //设置最大饥饿值
    void setMaxFoodLevel(int maxFoodLevel);
    //获取最大饱和度
    float getMaxSaturationLevel();
    //设置最大饱和度
    void setMaxSaturationLevel(float maxSaturationLevel);
    //获取蛋白质值
    int getProtein();
    //获取植物营养素值
    int getPhytonutrients();
    //获取胰岛素抵抗值
    int getInsulinResponse();
    //设置蛋白质值
    void setProtein(int protein);
    //设置植物营养素值
    void setPhytonutrients(int phytonutrients);
    //设置胰岛素抵抗值
    void setInsulinResponse(int insulinResponse);
    //设置所有值
    default void setAll(int protein, int phytonutrients, int insulinResponse){
        setProtein(protein);
        setPhytonutrients(phytonutrients);
        setInsulinResponse(insulinResponse);
    }
    //将所有值增加
    default void increaseAll(int protein, int phytonutrients, int insulinResponse){
        if(!isIncreasingBlocked()){
            setAll(getProtein()+protein, getPhytonutrients()+phytonutrients, getInsulinResponse()+insulinResponse);
        }
        toggleIncreasingBlocked();
    }
    //将所有值减1
    default void decreaseAllBy1(){
        setAll(getProtein()-1, getPhytonutrients()-1, getInsulinResponse()-1);
    }
}
