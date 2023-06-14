package com.skitbet.salty.shared.profile;

import com.skitbet.salty.shared.Salty;
import com.skitbet.salty.shared.rank.Rank;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Profile {

    public UUID uuid;
    public String name;

    public Rank rank;
    public List<String> permissions;

    public Profile(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.rank = Salty.INSTANCE.getRankHandler().getRankInSaved("default");
        this.permissions = new ArrayList<>();
        save();
    }

    public Profile(UUID uuid, String name, Rank rank, List<String> permissions) {
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.permissions = permissions;
        save();
    }

    public void save() {
        Document filtering = new Document("_id", this.uuid.toString());
        Document oldDoc = Salty.INSTANCE.getMongoHandler().getRanks().find(filtering).first();

        if (oldDoc != null) {
            Salty.INSTANCE.getMongoHandler().getRanks().findOneAndReplace(oldDoc, toDocument());
            return;
        }
        Salty.INSTANCE.getMongoHandler().getRanks().insertOne(toDocument());
    }

    public Document toDocument() {
        return new Document("_id", this.uuid.toString())
                .append("name", this.name)
                .append("rank", this.rank.getId())
                .append("permissions", this.permissions);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Rank getRank() {
        return rank;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
