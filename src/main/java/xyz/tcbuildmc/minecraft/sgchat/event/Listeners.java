package xyz.tcbuildmc.minecraft.sgchat.event;

import xyz.tcbuildmc.minecraft.sgchat.SpruceGlobalChatPlugin;

public class Listeners {
    public static void register(SpruceGlobalChatPlugin plugin) {
        plugin.getServer().getEventManager().register(plugin, new ForwardingListener(plugin.getServer(), plugin.getConfig()));
    }
}
