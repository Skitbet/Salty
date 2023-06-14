package com.skitbet.salty.shared;

import com.skitbet.salty.bungee.SaltyBungee;
import com.skitbet.salty.spigot.SaltyPlugin;
import com.skitbet.salty.shared.handlers.MongoHandler;
import com.skitbet.salty.shared.rank.RankHandler;

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
