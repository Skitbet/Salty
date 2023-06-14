package com.skitbet.salty.shared.profile;

import com.skitbet.salty.shared.Logger;
import com.skitbet.salty.shared.Salty;
import com.skitbet.salty.shared.handlers.MongoHandler;
import com.skitbet.salty.shared.rank.Rank;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileManager {

    public List<Profile> savedProfiles;

    private MongoHandler mongoHandler = MongoHandler.instance;

    public ProfileManager() {
        this.savedProfiles = new ArrayList<>();
    }

    public void init() {
        for (Document document : mongoHandler.getProfiles().find()) {
            Profile profile = fromDocument(document);
            if (profileExistsInSaved(profile.getUuid())) {
                // TODO: Figure out why there may be 2, not a issue yet, will fix it if or when it becomes a issue
                continue;
            }
            savedProfiles.add(profile);
        }
        Logger.info("&aLoaded &7" + savedProfiles.size() + "&a profiles from the database.");
    }

    public Profile createProfile(String name, UUID uuid) {
        if (profileExistsInSaved(uuid)) {
            return null;
        }
        Profile profile = new Profile(uuid, name);
        savedProfiles.add(profile);
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
        savedProfiles.remove(profile);

        for (Document document : mongoHandler.getProfiles().find()) {
            if (document.getString("_id").equals(id.toString())) {
                mongoHandler.getProfiles().deleteOne(document);
            }
        }
        Logger.info("&cDeleted the &7" + profile.getUuid() + " &cProfile!");
    }

    public Profile getProfileInSaved(UUID id) {
        for (Profile savedProfile : savedProfiles) {
            if (savedProfile.getUuid() == id) {
                return savedProfile;
            }
        }
        return null;
    }

    public boolean profileExistsInSaved(UUID id) {
        for (Profile savedProfile : savedProfiles) {
            if (savedProfile.getUuid() == id) {
                return true;
            }
        }
        return false;
    }

    public Profile fromDocument(Document document) {
        // Rank could be deleted when player joins so we give them default
        Rank rank = Salty.INSTANCE.getRankHandler().getRankInSaved(document.getString("name"));
        if (rank == null) rank = Salty.INSTANCE.getRankHandler().getRankInSaved("default");

        return new Profile(
                UUID.fromString(document.getString("_id")),
                document.getString("name"),
                rank,
                document.getList("permissions", String.class)
                );
    }

}
