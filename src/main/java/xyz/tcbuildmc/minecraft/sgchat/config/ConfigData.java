package xyz.tcbuildmc.minecraft.sgchat.config;

import java.util.List;

public interface ConfigData {
    boolean isChatForwarding();
    void setChatForwarding(boolean chatForwarding);

    String getChatFormat();
    void setChatFormat(String chatFormat);

    boolean isPlayerStatusForwarding();
    void setPlayerStatusForwarding(boolean playerStatusForwarding);

    String getJoinFormat();
    void setJoinFormat(String joinFormat);

    String getLeaveFormat();
    void setLeaveFormat(String leaveFormat);

    List<String> getExceptServers();
    void setExceptServers(List<String> exceptServers);

    List<String> getExceptPlayers();
    void setExceptPlayers(List<String> exceptPlayers);
}
