package com.skitbet.salty.bungee;

import com.skitbet.salty.bungee.listeners.PlayerListeners;
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
        getProxy().getPluginManager().registerListener(this, new PlayerListeners());

        salty.onEnable();
    }

    @Override
    public void onDisable() {
        salty.onDisabled();
    }
}
