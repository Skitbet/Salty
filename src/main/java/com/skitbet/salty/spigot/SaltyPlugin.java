package com.skitbet.salty.spigot;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.skitbet.salty.shared.Salty;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class SaltyPlugin extends JavaPlugin implements PluginMessageListener {

    public static SaltyPlugin INSTANCE;
    public Salty salty;


    @Override
    public void onLoad() {
        INSTANCE = this;

        salty = new Salty();
        salty.onLoad();
    }

    @Override
    public void onEnable() {
        salty.onEnable();

        checkIfBungee();
        if (!getServer().getPluginManager().isPluginEnabled(this)) return;

        getServer().getMessenger().registerIncomingPluginChannel( this, "salty:update", this );
        getServer().getMessenger().registerOutgoingPluginChannel( this, "salty:update" );
    }

    @Override
    public void onDisable() {
        salty.onDisabled();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equalsIgnoreCase("salty:update")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

        String subChannel = in.readUTF();
        switch (subChannel) {
            case "massrankupdate" -> {
                String rankID = in.readUTF();
                Salty.INSTANCE.getRankHandler().update(rankID);
            }
        }

    }

    private void checkIfBungee()
    {
        if ( !getServer().spigot().getConfig().getConfigurationSection("settings").getBoolean( "bungeecord" ) )
        {
            getLogger().severe( "This server is not BungeeCord." );
            getLogger().severe( "If the server is already hooked to BungeeCord, please enable it into your spigot.yml aswell." );
            getLogger().severe( "Plugin disabled!" );
            getServer().getPluginManager().disablePlugin( this );
        }
    }
}