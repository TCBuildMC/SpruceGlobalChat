package xyz.tcbuildmc.minecraft.sgchat.config;

public interface ConfigData {
    boolean isMessageForwarding();
    void setMessageForwarding(boolean messageForwarding);

    String getMessageFormat();
    void setMessageFormat(String messageFormat);

    boolean isPlayerStatusForwarding();
    void setPlayerStatusForwarding(boolean playerStatusForwarding);

    String getJoinFormat();
    void setJoinFormat(String joinFormat);

    String getLeaveFormat();
    void setLeaveFormat(String leaveFormat);
}
