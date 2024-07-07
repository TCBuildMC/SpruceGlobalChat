package xyz.tcbuildmc.minecraft.sgchat.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import lombok.Getter;

import java.io.File;

@Getter
public class Config implements ConfigData {
    private final CommentedFileConfig config;

    public Config(File configPath) {
        this.config = CommentedFileConfig.builder(new File(configPath, "config.toml"))
                .defaultData(Config.class.getClassLoader().getResource("config.toml"))
                .autosave()
                .build();
    }

    public void load() {
        this.config.load();
    }

    public void close() {
        this.config.close();
    }

    public void reload() {
        this.config.load();
    }

    @Override
    public boolean getGlobalChat() {
        return this.config.getOrElse("globalChat", true);
    }

    @Override
    public void setGlobalChat(boolean globalChat) {
        this.config.set("globalChat", globalChat);
    }
}
