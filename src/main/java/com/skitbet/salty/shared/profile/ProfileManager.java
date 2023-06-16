package com.skitbet.salty.shared.profile;

import com.skitbet.salty.bungee.SaltyBungee;
import com.skitbet.salty.shared.Logger;
import com.skitbet.salty.shared.Salty;
import com.skitbet.salty.shared.handlers.MongoHandler;
import com.skitbet.salty.shared.rank.Rank;
<<<<<<< Updated upstream
import com.skitbet.salty.shared.rank.RankHandler;
=======
import com.skitbet.salty.spigot.SaltyPlugin;
>>>>>>> Stashed changes
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ProfileManager {

<<<<<<< Updated upstream
    public HashMap<UUID, Profile> savedProfiles = new HashMap<>();

    private final MongoHandler mongoHandler = MongoHandler.instance;

    public void init() {
        for (Document document : mongoHandler.getProfiles().find()) {
            Profile profile = fromDocument(document);
            savedProfiles.put(profile.uuid, profile);
        }

        Logger.info("&aLoaded &7" + savedProfiles.size() + "&a profiles from the database.");
=======
    public List<Profile> onlineProfiles;

    private MongoHandler mongoHandler = MongoHandler.instance;

    public ProfileManager() {
        this.onlineProfiles = new ArrayList<>();
    }

    public void update(String id) {
        UUID uuid = UUID.fromString(id);
        // Check if rank already exists
        if (!profile(uuid)) {
            return;
        }

        // Get profile and update it
        Profile profile = getProfileFromDB(uuid);
>>>>>>> Stashed changes
    }

    public Profile createProfile(String name, UUID uuid) {
        if (profileExistsInDB(uuid)) {
            return null;
        }
        Profile profile = new Profile(uuid, name);
<<<<<<< Updated upstream
        savedProfiles.put(uuid, profile);
=======
        onlineProfiles.add(profile);

        if (Salty.isBungee()) {
            SaltyBungee.INSTANCE.massPlayerUpdate(uuid.toString());
        }else {
            SaltyPlugin.INSTANCE.getServer().sendPluginMessage("");
        }
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
        Profile profile = getProfileInSaved(id);
        savedProfiles.remove(profile.uuid);

=======
>>>>>>> Stashed changes
        for (Document document : mongoHandler.getProfiles().find()) {
            if (document.getString("_id").equals(id.toString())) {
                // Deleting it from database
                mongoHandler.getProfiles().deleteOne(document);

                // Check if its online, if so remove
                Profile profile = fromDocument(document);
                if (profileExistsFromOnline(profile.getUuid())) {
                    onlineProfiles.remove(profile);
                }
                Logger.info("&cDeleted the &7" + profile.getUuid() + " &cProfile!");
            }
        }
    }

<<<<<<< Updated upstream
    public Profile getProfileInSaved(UUID id) {
        return savedProfiles.getOrDefault(id, null);
    }

    public boolean profileExistsInSaved(UUID id) {
        return savedProfiles.containsKey(id);
=======
    public Profile getProfileFromOnline(UUID id) {
        for (Profile onlineProfile : onlineProfiles) {
            if (onlineProfile.getUuid().toString().equalsIgnoreCase(id.toString())) {
                return onlineProfile;
            }
        }
        return null;
    }

    public boolean profileExistsFromOnline(UUID id) {
        for (Profile onlineProfile : onlineProfiles) {
            if (onlineProfile.getUuid().toString().equalsIgnoreCase(id.toString())) {
                return true;
            }
        }
        return false;
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
>>>>>>> Stashed changes
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
