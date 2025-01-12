package com.starkettle.mite_ctm.events;

import com.mojang.brigadier.CommandDispatcher;
import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.capabilities.ModCapabilities;
import com.starkettle.mite_ctm.commands.StatsCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Optional;

@EventBusSubscriber(modid = MinecraftIsTooEasyCataclysm.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class GameEventBus {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event){
        Player player=event.getEntity();
        Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER)).ifPresent((cap)->{
            cap.decrease1All();
            cap.setMaxFoodLevel(Mth.clamp(6+player.experienceLevel/5*2,0,20));
            player.getFoodData().setFoodLevel(Mth.clamp(player.getFoodData().getFoodLevel(),0,cap.getMaxFoodLevel()));
        });
        player.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(Math.min(20,6+player.experienceLevel/5*2));
    }
    @SubscribeEvent
    public static void onCommandsRegistering(RegisterCommandsEvent event){
        CommandDispatcher<CommandSourceStack> dispatcher=event.getDispatcher();
        dispatcher.register(Commands.literal("stats").executes(StatsCommand.INSTANCE));
    }
    @SubscribeEvent
    public static void canHarvest(PlayerEvent.HarvestCheck event){
        event.setCanHarvest(false);
        if(event.getTargetBlock().requiresCorrectToolForDrops()){
            Optional.ofNullable(event.getEntity().level().getCapability(ModCapabilities.BLOCK_HARVEST_LEVEL_HANDLER,event.getPos(),event.getTargetBlock(),null,null)).ifPresent(
                    (cap)-> Optional.ofNullable(event.getEntity().getMainHandItem().getCapability(ModCapabilities.TOOL_HARVEST_LEVEL_HANDLER)).ifPresent(
                            (cap2)-> event.setCanHarvest(cap.getHarvestLevel()<=cap2.getHarvestLevel())));
        }
    }
}
