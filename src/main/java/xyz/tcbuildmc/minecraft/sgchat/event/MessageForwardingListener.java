package xyz.tcbuildmc.minecraft.sgchat.event;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import xyz.tcbuildmc.minecraft.sgchat.config.Config;

import java.util.Locale;
import java.util.Optional;

public class MessageForwardingListener {
    private final ProxyServer server;
    private final Config config;

    public MessageForwardingListener(ProxyServer server, Config config) {
        this.server = server;
        this.config = config;
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerChat(PlayerChatEvent e) {
        if (this.config.isMessageForwarding()) {
            String message = e.getMessage();
            Player player = e.getPlayer();
            String playerServer = player.getCurrentServer().orElseThrow().getServer().getServerInfo().getName();

            this.server.getAllServers().forEach(s -> {
                if (!s.getServerInfo().getName().equals(playerServer)) {
                    s.getPlayersConnected().forEach(p -> p.sendMessage(getFormattedMessage(playerServer, player.getUsername(), message)));
                }
            });
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerLogin(LoginEvent e) {
        if (this.config.isPlayerStatusForwarding()) {
            String player = e.getPlayer().getUsername();

            this.server.getAllServers().forEach(s -> s.getPlayersConnected().forEach(p -> p.sendMessage(
                    getFormattedMessage("ProxyServer", "ProxyServer",
                            "Player %s logged in".formatted(player)))));
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerConnect(ServerPostConnectEvent e) {
        if (this.config.isPlayerStatusForwarding()) {
            Player player = e.getPlayer();
            Optional<RegisteredServer> from = Optional.ofNullable(e.getPreviousServer());
            String target = player.getCurrentServer().orElseThrow().getServerInfo().getName();

            this.server.getAllServers().forEach(s -> s.getPlayersConnected().forEach(p -> {
                String name = "ProxyServer";
                if (from.isPresent()) {
                    name = from.get().getServerInfo().getName();
                }

                p.sendMessage(getFormattedMessage(name,
                        "ProxyServer",
                        "Player %s transferred to server %s from server %s".formatted(player.getUsername(),
                                target, name)));
            }));
        }
    }

    @Subscribe
    public void onPlayerDisconnected(DisconnectEvent e) {
        if (this.config.isPlayerStatusForwarding()) {
            String player = e.getPlayer().getUsername();
            DisconnectEvent.LoginStatus status = e.getLoginStatus();

            this.server.getAllServers().forEach(s -> s.getPlayersConnected().forEach(p -> p.sendMessage(
                    getFormattedMessage("ProxyServer", "ProxyServer",
                            "Player %s disconnected because %s".formatted(player, status.name())))));
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerKicked(KickedFromServerEvent e) {
        if (this.config.isPlayerStatusForwarding()) {
            Player player = e.getPlayer();
            String server = e.getServer().getServerInfo().getName();
            Optional<Component> reason = e.getServerKickReason();

            this.server.getAllServers().forEach(s -> s.getPlayersConnected().forEach(p -> {
                Component component = Component.empty();
                if (reason.isPresent()) {
                    component = reason.get();
                }

                p.sendMessage(getFormattedMessage(server, "ProxyServer",
                        "Player %s was kicked/disconnected from server %s because".formatted(player.getUsername(), server),
                        component));
            }));
        }
    }

    private Component getFormattedMessage(String server, String player, String message) {
        String messageFormat = this.config.getMessageFormat();

        return Component.text(messageFormat.formatted(server, player, message));
    }

    private Component getFormattedMessage(String server, String player, String message, Component more) {
        return getFormattedMessage(server, player, message).appendSpace().append(more);
    }
}
