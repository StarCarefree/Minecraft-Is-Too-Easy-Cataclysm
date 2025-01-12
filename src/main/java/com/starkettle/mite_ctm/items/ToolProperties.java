package com.starkettle.mite_ctm.items;

//工具挖掘等级相关数值
public enum ToolProperties {
    air(1),//占位，代表空手
    iron_pickaxe(3),
    iron_shovel(3),
    iron_axe(3),
    iron_hoe(3),
    iron_sword(3)
    ;
    public final int harvestLevel;
    ToolProperties(int harvestLevel){
        this.harvestLevel=harvestLevel;
    }
}
