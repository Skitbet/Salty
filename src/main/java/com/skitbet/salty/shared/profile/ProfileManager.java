package com.skitbet.salty.shared.profile;

import com.skitbet.salty.bungee.SaltyBungee;
import com.skitbet.salty.shared.Logger;
import com.skitbet.salty.shared.Salty;
import com.skitbet.salty.shared.handlers.MongoHandler;
import com.skitbet.salty.shared.rank.Rank;
import com.skitbet.salty.shared.rank.RankHandler;
import com.skitbet.salty.spigot.SaltyPlugin;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ProfileManager {

    public HashMap<UUID, Profile> savedProfiles = new HashMap<>();

    private final MongoHandler mongoHandler = MongoHandler.instance;

    public void update(String id) {
        UUID uuid = UUID.fromString(id);
        if (!profileExistsInSaved(uuid)) {
            return;
        }

        // Get profile and update it
        Profile profile = getProfileFromDB(uuid);
        profile.update(getProfileDocument(uuid));
    }

    public Profile createProfile(String name, UUID uuid) {
        if (profileExistsInDB(uuid)) {
            return null;
        }
        Profile profile = new Profile(uuid, name);
        savedProfiles.put(uuid, profile);

        if (Salty.isBungee()) {
            SaltyBungee.INSTANCE.massPlayerUpdate(uuid.toString());
        }else {
            SaltyPlugin.INSTANCE.getServer().sendPluginMessage();
        }

        Logger.info("&aCreated a profile with the UUID of &7" + profile.getUuid() );
        return profile;
    }

    public Profile getOrCreateProfile(String name, UUID uuid) {
        if (profileExistsInDB(uuid)) {
            return getProfileFromDB(uuid);
        }
        return createProfile(name, uuid);
    }

    public void deleteProfile(UUID id) {
        if (!profileExistsInDB(id)) {
            return;
        }

        savedProfiles.remove(id);

        for (Document document : mongoHandler.getProfiles().find()) {
            if (document.getString("_id").equals(id.toString())) {
                // Deleting it from database
                mongoHandler.getProfiles().deleteOne(document);

                // Check if its online, if so remove
                Profile profile = fromDocument(document);


                Logger.info("&cDeleted the &7" + profile.getUuid() + " &cProfile!");
            }
        }
    }

    public Profile getProfileInSaved(UUID id) {
        return savedProfiles.getOrDefault(id, null);
    }

    public boolean profileExistsInSaved(UUID id) {
        return savedProfiles.containsKey(id);
    }

    public Document getProfileDocument(UUID id) {
        for (Document document : mongoHandler.getProfiles().find()) {
            if (document.getString("_id").equalsIgnoreCase(id.toString())) {
                return document;
            }
        }
        return null;
    }

    public Profile getProfileFromDB(UUID id) {
        for (Document document : mongoHandler.getProfiles().find()) {
            if (document.getString("_id").equalsIgnoreCase(id.toString())) {
                return fromDocument(document);
            }
        }
        return null;
    }

    public boolean profileExistsInDB(UUID id) {
        for (Document document : mongoHandler.getProfiles().find()) {
            if (document.getString("_id").equalsIgnoreCase(id.toString())) {
                return true;
            }
        }
        return false;
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
