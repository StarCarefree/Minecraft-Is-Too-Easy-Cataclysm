package com.starkettle.mite_ctm.events;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.starkettle.mite_ctm.MinecraftIsTooEasyCataclysm;
import com.starkettle.mite_ctm.capabilities.IPlayerFoodValue;
import com.starkettle.mite_ctm.capabilities.ModCapabilities;
import com.starkettle.mite_ctm.capabilities.PlayerFoodValue;
import com.starkettle.mite_ctm.commands.StatsCommand;
import com.starkettle.mite_ctm.effects.ModEffects;
import com.starkettle.mite_ctm.utils.Tickable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@EventBusSubscriber(modid = MinecraftIsTooEasyCataclysm.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class GameEventBus {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event){
        Player player=event.getEntity();
        Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER)).ifPresent((cap)->{
            cap.decreaseAllBy1();
            cap.setMaxFoodLevel(Mth.clamp(6+player.experienceLevel/5*2,0,20));
            cap.setMaxSaturationLevel(Mth.clamp(6f+(float)player.experienceLevel/5*2,0f,20f));
            player.getFoodData().setFoodLevel(Mth.clamp(player.getFoodData().getFoodLevel(),0,cap.getMaxFoodLevel()));
            player.getFoodData().setSaturation(Mth.clamp(player.getFoodData().getSaturationLevel(),0f,cap.getMaxSaturationLevel()));
        });
        player.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(Math.min(20,6+player.experienceLevel/5*2));
        IPlayerFoodValue playerFoodValue=player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER);
        if(player.getFoodData().getFoodLevel()==0F){
            player.setSpeed(0.08F);
        } else {
            player.setSpeed(0.1F);
        }
        //食物相关的重写到FoodDataMixin里了
        if(!player.hasEffect(ModEffects.MALNOURISHED)&&playerFoodValue.getPhytonutrients() <= PlayerFoodValue.MAX_PHYTONUTRIENTS*0.05 || playerFoodValue.getProtein() <= PlayerFoodValue.MAX_PROTEIN*0.05){
            player.addEffect(new MobEffectInstance(ModEffects.MALNOURISHED));
        } else if(player.hasEffect(ModEffects.MALNOURISHED)) {
            player.removeEffect(ModEffects.MALNOURISHED);
        }

        if(playerFoodValue.getInsulinResponse()>=48000){
            int amplifier = 0;
            if(playerFoodValue.getInsulinResponse()>=96000&&playerFoodValue.getInsulinResponse()<144000){
                amplifier = 1;
            } else if(playerFoodValue.getInsulinResponse()>=144000){
                amplifier = 2;
            }
            if(!player.hasEffect(ModEffects.INSULIN_RESISTANCE)||player.getEffect(ModEffects.INSULIN_RESISTANCE).getDuration()!=playerFoodValue.getInsulinResponse()||player.getEffect(ModEffects.INSULIN_RESISTANCE).getAmplifier()!=amplifier){
                if(player.hasEffect(ModEffects.INSULIN_RESISTANCE)) player.removeEffect(ModEffects.INSULIN_RESISTANCE);
                player.addEffect(new MobEffectInstance(ModEffects.INSULIN_RESISTANCE,playerFoodValue.getInsulinResponse(),amplifier));
            }
        }
        else if(player.hasEffect(ModEffects.INSULIN_RESISTANCE)){
            player.removeEffect(ModEffects.INSULIN_RESISTANCE);
        }

        if(player.containerMenu instanceof Tickable menu){
            menu.tick();
        }
    }
    @SubscribeEvent
    public static void onCommandsRegistering(RegisterCommandsEvent event){
        CommandDispatcher<CommandSourceStack> dispatcher=event.getDispatcher();
        dispatcher.register(Commands.literal("stats").executes(StatsCommand.INSTANCE));
        dispatcher.register(Commands.literal("dev").then(Commands.literal("query_insulin_response").executes(context -> {
            context.getSource().sendSystemMessage(Component.literal(context.getSource().getPlayer().getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER).getInsulinResponse()+""));
            return Command.SINGLE_SUCCESS;
        })).then(Commands.literal("set_insulin_response").then(Commands.argument("awa",IntegerArgumentType.integer(0,PlayerFoodValue.MAX_INSULIN_RESPONSE)).executes(context -> {
            context.getSource().getPlayer().getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER).setInsulinResponse(context.getArgument("awa",Integer.class));
            return Command.SINGLE_SUCCESS;
        }))).then(Commands.literal("add_all_by_1145").executes(context -> {
            context.getSource().getPlayer().getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER).increaseAll(1145,1145,1145);
            return Command.SINGLE_SUCCESS;
        })));
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
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event){
        Player player = event.getEntity();
        if (player.getHealth() <= 1.0F||player.getFoodData().getFoodLevel() <= 0) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onEntityDamage(LivingIncomingDamageEvent event){
        if(event.getSource().getEntity() instanceof Player player){
            event.setAmount(event.getAmount()*(1.0F+player.experienceLevel*0.005F));
        }
        if(event.getEntity() instanceof Player player){
            Random random = new Random();
            int randomInt = random.nextInt(21-player.experienceLevel/10);
            if(player.getArmorValue()>event.getAmount()&&randomInt==0){
                event.setAmount(0.0F);
            }
        }
    }
}
