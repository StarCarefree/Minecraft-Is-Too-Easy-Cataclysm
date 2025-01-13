package com.starkettle.mite_ctm.utils;

import net.minecraft.world.item.ItemStack;

public interface ICrafting {
    int getCraftTickCount();
    int getCraftingTicks();
    void setCraftTickCount(int count);
    void setCraftingTicks(int ticks);
    ItemStack getResultItemStack();
    void setResultItemStack(ItemStack itemStack);
    void setResultSlotAndSendS2CPacket(ItemStack itemStack);
}
