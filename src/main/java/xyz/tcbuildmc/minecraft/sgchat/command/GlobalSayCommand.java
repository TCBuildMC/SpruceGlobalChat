package xyz.tcbuildmc.minecraft.sgchat.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.VelocityBrigadierMessage;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

public class GlobalSayCommand {
    public static LiteralCommandNode<CommandSource> register(final ProxyServer server) {
        return LiteralArgumentBuilder.<CommandSource>literal("globalsay")
                .requires(s -> s.hasPermission("sgc.command.say"))
                .then(RequiredArgumentBuilder.<CommandSource, String>argument("content", StringArgumentType.greedyString())
                        .executes(c -> {
                            String message = c.getArgument("content", String.class);

                            server.getAllPlayers().forEach(p -> p.sendMessage(Component.text(message)));
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("player", StringArgumentType.word())
                                .suggests((c, b) -> {
                                    server.getAllPlayers().forEach(p -> b.suggest(
                                            p.getUsername(),
                                            VelocityBrigadierMessage.tooltip(Component.text(p.getUsername()))));
                                    return b.buildFuture();
                                })
                                .executes(c -> {
                                    String message = c.getArgument("content", String.class);
                                    String playerName = c.getArgument("player", String.class);

                                    server.getPlayer(playerName).ifPresent(p -> p.sendMessage(Component.text(message)));
                                    return Command.SINGLE_SUCCESS;
                                })))
                .build();
    }
}
