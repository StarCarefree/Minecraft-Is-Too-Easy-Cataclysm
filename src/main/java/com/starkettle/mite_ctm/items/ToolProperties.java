package com.starkettle.mite_ctm.items;

//工具挖掘等级相关数值
public enum ToolProperties {
    //请填harvest_level里面【工具】的挖掘等级
    //Minecraft工具
    air(1),//占位，代表空手
    iron_pickaxe(3),
    iron_shovel(3),
    iron_axe(3),
    iron_hoe(3),
    iron_sword(3),
    //TODO:剩下的工具
    //MITE-CTM工具
    //TODO:MITE工具
    ;
    public final int harvestLevel;
    ToolProperties(int harvestLevel){
        this.harvestLevel=harvestLevel;
    }
}
