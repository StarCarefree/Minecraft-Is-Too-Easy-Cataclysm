package com.starkettle.mite_ctm.blocks;

//方块硬度及挖掘等级有关的数据
public enum BlockProperties {
    //Minecraft原版方块
    stone(240f, 2),
    grass_block(50f),
    dirt(50f),
    cobblestone(200f, 2),
    oak_planks(80f),
    spruce_planks(80f),
    birch_planks(80f),
    jungle_planks(80f),
    acacia_planks(80f),
    cherry_planks(80f),
    pale_oak_planks(80f),
    dark_oak_planks(80f),
    oak_sapling(2f),
    spruce_sapling(2f),
    birch_sapling(2f),
    jungle_sapling(2f),
    acacia_sapling(2f),
    dark_oak_sapling(2f),
    cherry_sapling(2f),
    pale_oak_sapling(2f),
    bedrock(-10f/*-10f代表无限*/,100),
    water(10000f),
    lava(10000f),
    sand(40f),
    gravel(60f),
    //TODO:剩下的方块

    //MITE-CTM方块
    //TODO:MITE-CTM方块
    ;
    public final float strength;//方块硬度（会除以10的，直接照着MITE原版填就行）
    public final int harvestLevel;//挖掘等级
    BlockProperties(float strength/*方块硬度（会除以10的，直接照着MITE原版填就行）*/, int harvestLevel/*挖掘等级*/){
        this.strength=strength/10f;
        this.harvestLevel=harvestLevel;

    }
    //挖掘等级默认为0
    BlockProperties(float strength/*方块硬度（会除以10的，直接照着MITE原版填就行）*/){
        this(strength,0);
    }
}
