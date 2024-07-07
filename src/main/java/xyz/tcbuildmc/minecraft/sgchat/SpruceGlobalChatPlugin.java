package xyz.tcbuildmc.minecraft.sgchat;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyReloadEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;
import xyz.tcbuildmc.minecraft.sgchat.command.Commands;
import xyz.tcbuildmc.minecraft.sgchat.config.Config;

import java.io.File;
import java.nio.file.Path;

@Plugin(
        id = "spruceglobalchat",
        name = "SpruceGlobalChat",
        version = "1.0.0-alpha.1",
        description = "A Velocity plugin.",
        url = "https://github.com/TCBuildMC/SpruceGlobalChat",
        authors = {"TCBuildMC", "Block_Mrlimr267"}
)
public class SpruceGlobalChatPlugin {
    public static SpruceGlobalChatPlugin instance;

    @Getter
    private final Logger logger;

    @Getter
    private final ProxyServer server;

    @Getter
    private final File pluginData;

    @Getter
    private final Config config;

    @Inject
    public SpruceGlobalChatPlugin(Logger logger, ProxyServer server, @DataDirectory Path pluginData) {
        this.logger = logger;
        this.server = server;
        this.pluginData = pluginData.toFile();
        this.config = new Config(this.pluginData);
        SpruceGlobalChatPlugin.instance = this;
    }

    @Subscribe
    public void onServerStart(ProxyInitializeEvent e) {
        this.config.load();
        Commands.register(this.server);
    }

    @Subscribe
    public void onServerShutdown(ProxyShutdownEvent e) {
        this.config.close();
    }

    @Subscribe
    public void onServerReload(ProxyReloadEvent e) {
        reload();
    }

    public void reload() {
        this.config.reload();
    }
}
