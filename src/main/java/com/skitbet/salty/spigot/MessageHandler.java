package com.skitbet.salty.spigot;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class MessageHandler {

    private SaltyPlugin plugin = SaltyPlugin.INSTANCE;

    public void updatePlayer(String id) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("playerupdate");
        out.writeUTF(id);

        plugin.getServer().sendPluginMessage(plugin, "salty:update", out.toByteArray());
    }

}
