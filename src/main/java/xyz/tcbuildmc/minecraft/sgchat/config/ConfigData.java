package xyz.tcbuildmc.minecraft.sgchat.config;

public interface ConfigData {
    boolean isMessageForwarding();
    void setMessageForwarding(boolean messageForwarding);

    boolean isPlayerStatusForwarding();
    void setPlayerStatusForwarding(boolean playerStatusForwarding);

    String getMessageFormat();
    void setMessageFormat(String messageFormat);
}
