package com.skitbet.salty.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.skitbet.salty.bungee.listeners.PlayerListeners;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import com.skitbet.salty.shared.Salty;

public class SaltyBungee extends Plugin {

    public static SaltyBungee INSTANCE;

    public Salty salty;


    @Override
    public void onLoad() {
        INSTANCE = this;

        salty = new Salty();
        salty.onLoad();
    }

    @Override
    public void onEnable() {
        getProxy().registerChannel("salty:update");
        getProxy().getPluginManager().registerListener(this, new PlayerListeners());

        salty.onEnable();

        massRankUpdate("default");
    }

    @Override
    public void onDisable() {
        salty.onDisabled();
    }

    /**
     * Will send a update packet to all server saying a rank needs to be refrehsed
     *
     * @param id
     */
    public void massRankUpdate(String id) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("massrankupdate");
        out.writeUTF(id);

        getProxy().getServers().forEach((s, serverInfo) -> {
            serverInfo.sendData("salty:update", out.toByteArray());
        });

    }
}
