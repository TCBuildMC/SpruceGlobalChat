package xyz.tcbuildmc.minecraft.sgchat.event;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import xyz.tcbuildmc.minecraft.sgchat.config.ConfigData;

import java.util.Optional;

public class ForwardingListener {
    private final ProxyServer server;
    private final ConfigData config;

    public ForwardingListener(ProxyServer server, ConfigData config) {
        this.server = server;
        this.config = config;
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerChat(PlayerChatEvent e) {
        if (this.config.isChatForwarding()) {
            String message = e.getMessage();
            Player player = e.getPlayer();
            String playerServer = player.getCurrentServer().orElseThrow().getServer().getServerInfo().getName();

            this.server.getAllServers().forEach(s -> {
                if (!s.getServerInfo().getName().equals(playerServer) && !this.config.getExceptServers().contains(s.getServerInfo().getName())) {
                    s.getPlayersConnected().forEach(p -> {
                        if (!this.config.getExceptPlayers().contains(p.getUsername())) {
                            p.sendMessage(Component.text(this.config.getChatFormat().formatted(playerServer, player.getUsername(), message)));
                        }
                    });
                }
            });
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerConnect(ServerPostConnectEvent e) {
        if (this.config.isPlayerStatusForwarding()) {
            Player player = e.getPlayer();
            Optional<RegisteredServer> from = Optional.ofNullable(e.getPreviousServer());
            String target = player.getCurrentServer().orElseThrow().getServerInfo().getName();

            this.server.getAllServers().forEach(s -> {
                if (!this.config.getExceptServers().contains(s.getServerInfo().getName())) {
                    s.getPlayersConnected().forEach(p -> {
                        if (!this.config.getExceptPlayers().contains(p.getUsername())) {
                            String name = "ProxyServer";
                            if (from.isPresent()) {
                                name = from.get().getServerInfo().getName();
                            }

                            p.sendMessage(Component.text(this.config.getJoinFormat().formatted(target, player.getUsername(), name)));
                        }
                    });
                }
            });
        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerKicked(KickedFromServerEvent e) {
        if (this.config.isPlayerStatusForwarding()) {
            Player player = e.getPlayer();
            String server = e.getServer().getServerInfo().getName();
            Optional<Component> reason = e.getServerKickReason();

            this.server.getAllServers().forEach(s -> {
                if (!this.config.getExceptServers().contains(s.getServerInfo().getName())) {
                    s.getPlayersConnected().forEach(p -> {
                        if (!this.config.getExceptPlayers().contains(p.getUsername())) {
                            Component component = Component.empty();
                            if (reason.isPresent()) {
                                component = component.appendSpace()
                                        .append(Component.text("because"))
                                        .appendSpace()
                                        .append(reason.get());
                            }

                            p.sendMessage(Component.text(this.config.getLeaveFormat().formatted(server, player.getUsername())).append(component));
                        }
                    });
                }
            });
        }
    }
}
