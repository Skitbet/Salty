package com.skitbet.salty.bungee;

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
        salty.onEnable();
    }

    @Override
    public void onDisable() {
        salty.onDisabled();
    }
}
