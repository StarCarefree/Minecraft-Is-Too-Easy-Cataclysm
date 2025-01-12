package com.starkettle.mite_ctm.items;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import net.minecraft.world.item.crafting.RecipeInput;

public enum CraftingDifficultyProperties {
    //请填recipe_components中的物品/方块合成难度（不是配方合成难度）
    //Minecraft原版物品/方块
    stone(240),
    cobblestone(200),
    oak_planks(80),
    spruce_planks(80),
    birch_planks(80),
    jungle_planks(80),
    acacia_planks(80),
    dark_oak_planks(80),
    cherry_planks(80),
    pale_oak_planks(80),
    sand(40),
    oak_log(120),
    spruce_log(120),
    birch_log(120),
    jungle_log(120),
    acacia_log(120),
    dark_oak_log(120),
    cherry_log(120),
    pale_oak_log(120),
    glass(200),
    //TODO:补充合成难度
    //MITE-CTM物品/方块
    //TODO:补充MITE-CTM合成难度
    ;
    public final int craftingDifficulty;
    CraftingDifficultyProperties(int craftingDifficulty){
        this.craftingDifficulty=craftingDifficulty;
    }
    public static int getCraftingTicks(RecipeInput recipeInput){
        int craftingDifficulty=0;
        for(int i=0;i<recipeInput.size();i++){
            try{
                CraftingDifficultyProperties properties=CraftingDifficultyProperties.valueOf(recipeInput.getItem(i).getItemHolder().getKey().location().getPath());
                craftingDifficulty+=properties.craftingDifficulty;
            } catch (IllegalArgumentException ignored){
            }
        }
        if(craftingDifficulty>100){
            return (int) Math.ceil(Math.pow(craftingDifficulty-100,0.8)+100);
        }
        return (int) Math.ceil(Math.pow(craftingDifficulty,0.8));
    }
}
