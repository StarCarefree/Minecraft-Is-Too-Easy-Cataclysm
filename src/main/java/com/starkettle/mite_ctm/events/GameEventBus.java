package com.starkettle.mite_ctm.events;

import com.mojang.brigadier.CommandDispatcher;
import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.capabilities.ModCapabilities;
import com.starkettle.mite_ctm.commands.StatsCommand;
import com.starkettle.mite_ctm.utils.ITick;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@EventBusSubscriber(modid = MinecraftIsTooEasyCataclysm.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class GameEventBus {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event){
        Player player=event.getEntity();
        Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER)).ifPresent((cap)->{
            cap.decrease1All();
            cap.setMaxFoodLevel(Mth.clamp(6+player.experienceLevel/5*2,0,20));
            cap.setMaxSaturationLevel(Mth.clamp(6f+(float)player.experienceLevel/5*2,0f,20f));
            player.getFoodData().setFoodLevel(Mth.clamp(player.getFoodData().getFoodLevel(),0,cap.getMaxFoodLevel()));
            player.getFoodData().setSaturation(Mth.clamp(player.getFoodData().getSaturationLevel(),0f,cap.getMaxSaturationLevel()));
        });
        player.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(Math.min(20,6+player.experienceLevel/5*2));
        if(player.getFoodData().getFoodLevel()==0F){
            player.setSpeed(0.08F);
        }
        else{
            player.setSpeed(0.1F);
        }
        if(player.tickCount%(64*20)==0&&player.getFoodData().getFoodLevel()>=player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER).getMaxFoodLevel()*0.5){
            player.heal(1.0F);
        }
        else if(player.tickCount%(256*20)==0&&player.getFoodData().getFoodLevel()>=player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER).getMaxFoodLevel()*0.5){
            player.heal(1.0F);
        }
        if(player.containerMenu instanceof CraftingMenu menu){
            ((ITick)menu).tick();
        }
    }
    @SubscribeEvent
    public static void onCommandsRegistering(RegisterCommandsEvent event){
        CommandDispatcher<CommandSourceStack> dispatcher=event.getDispatcher();
        dispatcher.register(Commands.literal("stats").executes(StatsCommand.INSTANCE));
    }
    @SubscribeEvent
    public static void canHarvest(PlayerEvent.HarvestCheck event){
        boolean vanilla = event.canHarvest();
        event.setCanHarvest(false);
        if(event.getTargetBlock().requiresCorrectToolForDrops()){
            AtomicInteger hav1 = new AtomicInteger(),hav2 = new AtomicInteger();
            Optional.ofNullable(event.getEntity().level().getCapability(ModCapabilities.BLOCK_HARVEST_LEVEL_HANDLER,event.getPos(),event.getTargetBlock(),null,null)).ifPresent(
                    (cap)-> hav1.set(cap.getHarvestLevel()));
            Optional.ofNullable(event.getEntity().getMainHandItem().getCapability(ModCapabilities.TOOL_HARVEST_LEVEL_HANDLER)).ifPresent(
                    (cap2)-> hav2.set(cap2.getHarvestLevel()));
            event.setCanHarvest(hav1.get()<=hav2.get()&&vanilla);
        }
    }
}
