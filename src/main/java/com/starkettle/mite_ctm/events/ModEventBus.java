package com.starkettle.mite_ctm.events;

import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.blocks.BlockProperties;
import com.starkettle.mite_ctm.capabilities.*;
import com.starkettle.mite_ctm.items.FoodProperties;
import com.starkettle.mite_ctm.items.ToolProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = MinecraftIsTooEasyCataclysm.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBus {
    @SubscribeEvent
    public static void onCapabilityRegistering(RegisterCapabilitiesEvent event) {
        event.registerEntity(
            ModCapabilities.PLAYER_FOOD_VALUE_HANDLER,
                EntityType.PLAYER,
                new PlayerFoodValueProvider()
        );

        try{
            for (Block block : BuiltInRegistries.BLOCK){
                BlockProperties.valueOf(BuiltInRegistries.BLOCK.wrapAsHolder(block).getKey().location().getPath());
                event.registerBlock(
                        ModCapabilities.BLOCK_HARVEST_LEVEL_HANDLER,
                        new BlockHarvestLevelProvider(),
                        block
                );
            }

            for(Item item:BuiltInRegistries.ITEM){
                ItemStack itemStack=new ItemStack(item);
                ToolProperties.valueOf(itemStack.getItemHolder().getKey().location().getPath());
                event.registerItem(
                        ModCapabilities.TOOL_HARVEST_LEVEL_HANDLER,
                        new ToolHarvestLevelProvider(),
                        item
                );
                FoodProperties.valueOf(itemStack.getItemHolder().getKey().location().getPath());
                event.registerItem(
                        ModCapabilities.FOOD_VALUE_HANDLER,
                        new FoodValueProvider(),
                        item
                );
            }
        } catch (IllegalArgumentException ignored){
        }
    }
}
