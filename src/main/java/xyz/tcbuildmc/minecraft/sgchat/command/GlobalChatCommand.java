package xyz.tcbuildmc.minecraft.sgchat.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import xyz.tcbuildmc.minecraft.sgchat.SpruceGlobalChatPlugin;

import java.util.List;
import java.util.Objects;

public class GlobalChatCommand {
    private static final List<String> suggestions = List.of("reload", "help");

    public static LiteralCommandNode<CommandSource> register(SpruceGlobalChatPlugin plugin) {
        return LiteralArgumentBuilder.<CommandSource>literal("spruceglobalchat")
                .executes(c -> {
                    CommandSource s = c.getSource();
                    s.sendMessage(Component.text("SpruceGlobalChat by TCBuildMC")
                            .appendNewline()
                            .append(Component.text("Version: " +
                                    plugin.getServer().getPluginManager().getPlugin("spruceglobalchat").get()
                                            .getDescription().getVersion().get()
                            )));

                    return Command.SINGLE_SUCCESS;
                })
                .then(RequiredArgumentBuilder.<CommandSource, String>argument("action", StringArgumentType.word())
                        .suggests((c, b) -> {
                            suggestions.forEach(b::suggest);
                            return b.buildFuture();
                        })
                        .executes(c -> {
                            String action = c.getArgument("action", String.class);
                            switch (action) {
                                case "reload" -> plugin.reload();
                                case "help" -> {
                                    CommandSource s = c.getSource();
                                    s.sendMessage(Component.text("Please see https://github.com/TCBuildMC/SpruceGlobalChat/wiki"));
                                }
                            }

                            return Command.SINGLE_SUCCESS;
                        }))
                .build();
    }
}
