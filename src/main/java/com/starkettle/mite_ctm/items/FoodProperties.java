package com.starkettle.mite_ctm.items;

public enum FoodProperties {
    apple(0,8000,4800);
    //TODO: add more foods
    public final int protein, phytonutrients, insulinResponse;
    FoodProperties(int protein, int phytonutrients, int insulinResponse){
        this.protein=protein;
        this.phytonutrients=phytonutrients;
        this.insulinResponse=insulinResponse;
    }
}
