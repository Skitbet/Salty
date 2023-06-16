package com.skitbet.salty.shared.util;

public enum MessageStatus {
    ANNOUNCE_RANK_UPDATE("massrankupdate"),
    ANNOUNCE_PLAYER_UPDATE("massplayerupdate"),
    SEND_RANK_UPDATE("rankupdate"),
    SEND_PLAYER_UPDATE("playerupdate");

    private final String subChannel;

    MessageStatus(String subChannel) {
        this.subChannel = subChannel;
    }

    public String getSubChannel() {
        return subChannel;
    }
}
