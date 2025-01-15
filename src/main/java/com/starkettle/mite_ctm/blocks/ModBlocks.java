package com.starkettle.mite_ctm.blocks;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.items.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MinecraftIsTooEasyCataclysm.MOD_ID);

    public static DeferredBlock<? extends Block> registerCustomBlockAndBlockItem(String id, Class<? extends Block> clazz){
        DeferredBlock<? extends Block> result = BLOCKS.registerBlock(id, name -> {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        ModItems.ITEMS.registerSimpleBlockItem(result);
        return result;
    }

    public static DeferredBlock<? extends Block> registerSimpleBlockAndBlockItem(String id, BlockBehaviour.Properties properties){
        DeferredBlock<? extends Block> result = BLOCKS.registerSimpleBlock(id, properties);
        ModItems.ITEMS.registerSimpleBlockItem(result);
        return result;
    }
}
