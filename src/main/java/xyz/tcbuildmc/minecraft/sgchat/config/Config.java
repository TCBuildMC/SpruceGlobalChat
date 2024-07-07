package xyz.tcbuildmc.minecraft.sgchat.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import lombok.Getter;

import java.io.File;
import java.util.List;

@Getter
public class Config implements ConfigData {
    private final CommentedFileConfig config;

    public Config(File configPath) {
        this.config = CommentedFileConfig.builder(new File(configPath, "config.toml"))
                .defaultData(Config.class.getClassLoader().getResource("config.toml"))
                .autosave()
                .build();
    }

    public final void load() {
        this.config.load();
    }

    @Deprecated(forRemoval = true)
    public final void save() {
        this.config.save();
    }

    public final void close() {
        this.config.close();
    }

    public final void reload() {
        this.config.load();
    }

    @Override
    public boolean isChatForwarding() {
        return this.config.getOrElse("chatForwarding", true);
    }

    @Override
    public void setChatForwarding(boolean chatForwarding) {
        this.config.set("chatForwarding", chatForwarding);
    }

    @Override
    public String getChatFormat() {
        return this.config.getOrElse("chatFormat", "[%s]<%s> %s");
    }

    @Override
    public void setChatFormat(String chatFormat) {
        this.config.set("chatFormat", chatFormat);
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
    public String getJoinFormat() {
        return this.config.getOrElse("joinFormat", "[%s] %s joined the server");
    }

    @Override
    public void setJoinFormat(String joinFormat) {
        this.config.set("joinFormat", joinFormat);
    }

    @Override
    public String getLeaveFormat() {
        return this.config.getOrElse("leaveFormat", "[%s] %s left the server");
    }

    @Override
    public void setLeaveFormat(String leaveFormat) {
        this.config.set("leaveFormat", leaveFormat);
    }

    @Override
    public List<String> getExceptServers() {
        return this.config.getOrElse("exceptServers", List.of());
    }

    @Override
    public void setExceptServers(List<String> exceptServers) {
        this.config.set("exceptServers", exceptServers);
    }

    @Override
    public List<String> getExceptPlayers() {
        return this.config.getOrElse("exceptPlayers", List.of());
    }

    @Override
    public void setExceptPlayers(List<String> exceptPlayers) {
        this.config.set("exceptPlayers", exceptPlayers);
    }
}
