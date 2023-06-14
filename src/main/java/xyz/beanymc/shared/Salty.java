package xyz.beanymc.shared;

import xyz.beanymc.bungee.SaltyBungee;
import xyz.beanymc.shared.handlers.MongoHandler;
import xyz.beanymc.shared.rank.RankHandler;
import xyz.beanymc.spigot.SaltyPlugin;

public class Salty {

    public static Salty INSTANCE;

    private MongoHandler mongoHandler;

    private RankHandler rankHandler;

    public void onLoad() {
        INSTANCE = this;

        this.mongoHandler = new MongoHandler();
        this.mongoHandler.connect();
    }

    public void onEnable() {
        this.rankHandler = new RankHandler();
        this.rankHandler.init();
    }

    public void onDisabled() {

    }

    public static boolean isSpigot() {
        return SaltyPlugin.INSTANCE != null;
    }
    public static boolean isBungee() {
        return SaltyBungee.INSTANCE != null;
    }

    public MongoHandler getMongoHandler() {
        return mongoHandler;
    }

    public RankHandler getRankHandler() {
        return rankHandler;
    }
}
