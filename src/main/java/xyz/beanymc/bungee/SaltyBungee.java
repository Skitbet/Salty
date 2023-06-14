package xyz.beanymc.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import xyz.beanymc.shared.Salty;
import xyz.beanymc.shared.handlers.MongoHandler;
import xyz.beanymc.shared.rank.RankHandler;

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
