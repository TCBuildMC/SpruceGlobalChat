package xyz.tcbuildmc.minecraft.sgchat.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import xyz.tcbuildmc.minecraft.sgchat.SpruceGlobalChatPlugin;

public class Commands {
    public static void register(SpruceGlobalChatPlugin plugin) {
        CommandManager manager = plugin.getServer().getCommandManager();

        LiteralCommandNode<CommandSource> say = GlobalSayCommand.register(plugin.getServer());
        manager.register(getMeta(plugin, manager, say)
                        .aliases("globaltell", "globalmsg")
                        .build(),
                getCommand(say));

        LiteralCommandNode<CommandSource> globalChat = GlobalChatCommand.register(plugin);
        manager.register(getMeta(plugin, manager, globalChat)
                        .aliases("sgc")
                        .build(),
                getCommand(globalChat));
    }

    public static CommandMeta.Builder getMeta(SpruceGlobalChatPlugin plugin, CommandManager manager, LiteralCommandNode<CommandSource> node) {
        return manager.metaBuilder(node.getLiteral())
                .plugin(plugin);
    }

    public static BrigadierCommand getCommand(LiteralCommandNode<CommandSource> node) {
        return new BrigadierCommand(node);
    }
}
