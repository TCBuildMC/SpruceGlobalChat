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
    public boolean isMessageForwarding() {
        return this.config.getOrElse("messageForwarding", true);
    }

    @Override
    public void setMessageForwarding(boolean messageForwarding) {
        this.config.set("messageForwarding", messageForwarding);
    }

    @Override
    public boolean isPlayerStatusForwarding() {
        return this.config.getOrElse("playerStatusForwarding", true);
    }

    @Override
    public void setPlayerStatusForwarding(boolean playerStatusForwarding) {
        this.config.set("playerStatusForwarding", playerStatusForwarding);
    }

    @Override
    public String getMessageFormat() {
        return this.config.getOrElse("messageFormat", "[%s]<%s> %s");
    }

    @Override
    public void setMessageFormat(String messageFormat) {
        this.config.set("messageFormat", messageFormat);
    }
}
