package com.skitbet.salty.shared.rank;

import com.skitbet.salty.shared.Logger;
import com.skitbet.salty.shared.handlers.MongoHandler;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class RankHandler {

    public List<Rank> savedRanks;

    private MongoHandler mongoHandler = MongoHandler.instance;

    public RankHandler() {
        this.savedRanks = new ArrayList<>();
    }

    public void init() {
        // Load all ranks in database
        for (Document document : mongoHandler.getRanks().find()) {
            savedRanks.add(fromDocument(document));
        }

        // Create default rank if it does not exist yet.
        if (rankExistsInSaved("default")) {
            createRank("Default");
        }

        Logger.info("&aLoaded &7" + savedRanks.size() + "&a ranks from the database.");
    }

    public void createRank(String name) {
        // Check if rank already exists
        if (rankExistsInSaved(name.toLowerCase())) {
            return;
        }

        // Make new instance of rank and save it
        Rank rank = new Rank(name.toLowerCase(), name, "&7");
        savedRanks.add(rank);

        Logger.info("&aCreated the &7" + rank.getDisplayName() + " &arank!");
    }

    public void deleteRank(String id) {
        // Check if rank doesn't exists, if it doesn't we don't do anything
        if (!rankExistsInSaved(id)) {
            return;
        }

        // Get the rank and remove it from the saved list
        Rank rank = getRankInSaved(id);
        savedRanks.remove(rank);

        // Remove rank from database
        for (Document document : mongoHandler.getRanks().find()) {
            if (document.getString("_id").equalsIgnoreCase(id)) {
                mongoHandler.getRanks().deleteOne(document);
            }
        }
        Logger.info("&cDeleted the &7" + rank.getDisplayName() + " &crank!");
    }

    public Rank getRankInSaved(String id) {
        for (Rank savedRank : savedRanks) {
            if (savedRank.getId().equalsIgnoreCase(id)) {
                return savedRank;
            }
        }
        return null;
    }

    public boolean rankExistsInSaved(String id) {
        for (Rank savedRank : savedRanks) {
            if (savedRank.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public Rank fromDocument(Document document) {
        return new Rank(
                document.getString("_id"),
                document.getString("displayName"),
                document.getString("prefix"),
                document.getString("color"),
                document.getInteger("priority"),
                document.getList("parents", String.class),
                document.getList("permissions", String.class)
                );
    }

}
