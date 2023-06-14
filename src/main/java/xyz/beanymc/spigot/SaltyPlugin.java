package xyz.beanymc.spigot;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.beanymc.shared.Salty;
import xyz.beanymc.shared.handlers.MongoHandler;
import xyz.beanymc.shared.rank.RankHandler;

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