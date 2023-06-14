package com.skitbet.salty.spigot;

import org.bukkit.plugin.java.JavaPlugin;
import com.skitbet.salty.shared.Salty;

public class SaltyPlugin extends JavaPlugin {

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
    }

    @Override
    public void onDisable() {
        salty.onDisabled();
    }
}