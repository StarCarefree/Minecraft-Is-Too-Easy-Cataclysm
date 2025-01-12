package com.starkettle.mite_ctm.items;

public enum ToolProperties {
    iron_pickaxe(3),
    air(1);
    public final int harvestLevel;
    ToolProperties(int harvestLevel){
        this.harvestLevel=harvestLevel;
    }
}
