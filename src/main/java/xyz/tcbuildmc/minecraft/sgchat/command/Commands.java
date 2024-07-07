package xyz.tcbuildmc.minecraft.sgchat.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.tcbuildmc.minecraft.sgchat.SpruceGlobalChatPlugin;

public class Commands {
    public static void register(final ProxyServer server) {
        CommandManager manager = server.getCommandManager();

        LiteralCommandNode<CommandSource> say = GlobalSayCommand.register(server);
        manager.register(getMeta(manager, say)
                        .aliases("globaltell", "globalmsg")
                        .build(),
                getCommand(say));

        LiteralCommandNode<CommandSource> globalChat = GlobalChatCommand.register(SpruceGlobalChatPlugin.instance);
        manager.register(getMeta(manager, globalChat)
                        .aliases("sgc")
                        .build(),
                getCommand(globalChat));
    }

    public static CommandMeta.Builder getMeta(final CommandManager manager, final LiteralCommandNode<CommandSource> node) {
        return manager.metaBuilder(node.getLiteral())
                .plugin(SpruceGlobalChatPlugin.instance);
    }

    public static BrigadierCommand getCommand(final LiteralCommandNode<CommandSource> node) {
        return new BrigadierCommand(node);
    }
}
