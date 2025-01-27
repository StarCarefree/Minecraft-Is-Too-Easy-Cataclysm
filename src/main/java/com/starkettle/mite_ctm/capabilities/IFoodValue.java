package com.starkettle.mite_ctm.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

//物品的FoodVal
public interface IFoodValue extends INBTSerializable<CompoundTag> {
    //获取蛋白质值
    int getProtein();
    //获取植物营养素值
    int getPhytonutrients();
    //获取胰岛素抵抗值
    int getInsulinResponse();
}
