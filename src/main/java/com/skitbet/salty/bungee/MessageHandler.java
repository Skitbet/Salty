package com.skitbet.salty.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.skitbet.salty.bungee.SaltyBungee;
import com.skitbet.salty.shared.util.MessageStatus;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MessageHandler implements Listener {

    @EventHandler
    public void on(PluginMessageEvent event) {
        if (!event.getTag().equalsIgnoreCase("salty:update")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String subChannel = in.readUTF();
        String id = in.readUTF();

        if (subChannel.equals(MessageStatus.SEND_RANK_UPDATE.getSubChannel())) {
            SaltyBungee.INSTANCE.massRankUpdate(id);
        }
        else if (subChannel.equals(MessageStatus.SEND_PLAYER_UPDATE.getSubChannel())) {
            SaltyBungee.INSTANCE.massPlayerUpdate(id);
        }
    }

}
