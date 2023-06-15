package com.skitbet.salty.shared.profile;

import com.skitbet.salty.shared.Logger;
import com.skitbet.salty.shared.Salty;
import com.skitbet.salty.shared.handlers.MongoHandler;
import com.skitbet.salty.shared.rank.Rank;
import com.skitbet.salty.shared.rank.RankHandler;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ProfileManager {

    public HashMap<UUID, Profile> savedProfiles = new HashMap<>();

    private final MongoHandler mongoHandler = MongoHandler.instance;

    public void init() {
        for (Document document : mongoHandler.getProfiles().find()) {
            Profile profile = fromDocument(document);
            savedProfiles.put(profile.uuid, profile);
        }

        Logger.info("&aLoaded &7" + savedProfiles.size() + "&a profiles from the database.");
    }

    public Profile createProfile(String name, UUID uuid) {
        if (profileExistsInSaved(uuid)) {
            return null;
        }
        Profile profile = new Profile(uuid, name);
        savedProfiles.put(uuid, profile);

        Logger.info("&aCreated a profile with the UUID of &7" + profile.getUuid() );
        return profile;
    }

    public Profile getOrCreateProfile(String name, UUID uuid) {
        if (profileExistsInSaved(uuid)) {
            return getProfileInSaved(uuid);
        }
        return createProfile(name, uuid);
    }

    public void deleteProfile(UUID id) {
        if (!profileExistsInSaved(id)) {
            return;
        }

        Profile profile = getProfileInSaved(id);
        savedProfiles.remove(profile.uuid);

        for (Document document : mongoHandler.getProfiles().find()) {
            if (document.getString("_id").equals(id.toString())) {
                mongoHandler.getProfiles().deleteOne(document);
            }
        }
        Logger.info("&cDeleted the &7" + profile.getUuid() + " &cProfile!");
    }

    public Profile getProfileInSaved(UUID id) {
        return savedProfiles.getOrDefault(id, null);
    }

    public boolean profileExistsInSaved(UUID id) {
        return savedProfiles.containsKey(id);
    }

    public Profile fromDocument(Document document) {
        RankHandler handler = Salty.INSTANCE.getRankHandler();
        Rank rank = handler.getRankInSaved(document.getString("name"));
        if (rank == null) rank = handler.getRankInSaved("default");

        return new Profile(
                UUID.fromString(document.getString("_id")),
                document.getString("name"),
                rank,
                document.getList("permissions", String.class)
        );
    }

}
