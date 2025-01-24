package com.starkettle.mite_ctm.items;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MinecraftIsTooEasyCataclysm.MOD_ID);
    public static void register(IEventBus modEventBus){
        ITEMS.register(modEventBus);
    }
}
