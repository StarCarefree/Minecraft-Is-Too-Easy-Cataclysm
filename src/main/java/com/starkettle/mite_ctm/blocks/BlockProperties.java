package com.starkettle.mite_ctm.blocks;

public enum BlockProperties {
    stone(1.5f, 2),
    bedrock(-1,100);
    public final float strength;
    public final int harvestLevel;
    BlockProperties(float strength, int harvestLevel){
        this.strength=strength;
        this.harvestLevel=harvestLevel;
    }
}
