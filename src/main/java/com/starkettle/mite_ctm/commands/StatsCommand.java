package com.starkettle.mite_ctm.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.starkettle.mite_ctm.capabilities.ModCapabilities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.world.entity.player.Player;

public class StatsCommand implements Command<CommandSourceStack> {
    public static final Command<CommandSourceStack> INSTANCE=new StatsCommand();
    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        context.getSource().sendSystemMessage(ComponentUtils.wrapInSquareBrackets(Component.translatable("mite_ctm.mod_name")));
        context.getSource().sendSystemMessage(Component.translatable("command.mite_ctm.stats",player.getName(),player.getStringUUID(),player.experienceLevel,player.totalExperience,player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER).getPhytonutrients(),player.getCapability(ModCapabilities.PLAYER_FOOD_VALUE_HANDLER).getProtein()));
        return 0;
    }
}
