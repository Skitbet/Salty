package xyz.beanymc.shared.rank;

import org.bson.Document;
import xyz.beanymc.shared.Logger;
import xyz.beanymc.shared.handlers.MongoHandler;

import java.util.ArrayList;
import java.util.List;

public class RankHandler {

    public List<Rank> savedRanks;

    private MongoHandler mongoHandler = MongoHandler.instance;

    public RankHandler() {
        this.savedRanks = new ArrayList<>();
    }

    public void init() {
        for (Document document : mongoHandler.getRanks().find()) {
            Rank rank = fromDocument(document);
            if (rankExistsInSaved(rank.getId())) {
                // TODO: Figure out why there may be 2, not a issue yet, will fix it if or when it becomes a issue
                continue;
            }
            savedRanks.add(rank);
        }
        if (savedRanks.size() == 0) {
            createRank("Default");
        }
        Logger.info("&aLoaded &7" + savedRanks.size() + "&a ranks from the database.");
    }

    public void createRank(String name) {
        if (rankExistsInSaved(name.toLowerCase())) {
            return;
        }
        Rank rank = new Rank(name.toLowerCase(), name, "&7");
        savedRanks.add(rank);
        Logger.info("&aCreated the &7" + rank.getDisplayName() + " &arank!");
    }

    public void deleteRank(String id) {
        if (!rankExistsInSaved(id)) {
            return;
        }

        Rank rank = getRankInSaved(id);
        savedRanks.remove(rank);

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
