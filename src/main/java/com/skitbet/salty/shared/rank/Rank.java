package com.skitbet.salty.shared.rank;

import org.bson.Document;
import com.skitbet.salty.shared.Salty;

import java.util.ArrayList;
import java.util.List;

public class Rank {

    private String id;
    private String displayName;
    private String prefix;
    private String color;

    private int priority;
    private List<String> parents;
    private List<String> permissions;

    public Rank(String id, String displayName, String color) {
        this.id = id;
        this.displayName = displayName;
        this.color = color;
        this.prefix = color;
        this.priority = 0;
        this.parents = new ArrayList<>();
        this.permissions = new ArrayList<>();
        save();
    }

    public Rank(String id, String displayName, String prefix, String color, int priority, List<String> parents, List<String> permissions) {
        this.id = id;
        this.displayName = displayName;
        this.prefix = color;
        this.color = color;
        this.priority = priority;
        this.parents = parents;
        this.permissions = permissions;
        save();
    }

    public void save() {
        Document filtering = new Document("_id", this.id);
        Document oldDoc = Salty.INSTANCE.getMongoHandler().getRanks().find(filtering).first();

        if (oldDoc != null) {
            Salty.INSTANCE.getMongoHandler().getRanks().findOneAndReplace(oldDoc, toDocument());
            return;
        }
        Salty.INSTANCE.getMongoHandler().getRanks().insertOne(toDocument());
    }

    public Document toDocument() {
        return new Document("_id", this.id)
                .append("displayName", this.displayName)
                .append("prefix", this.prefix)
                .append("color", this.color)
                .append("priority", this.priority)
                .append("parents", this.parents)
                .append("permissions", this.permissions);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getParents() {
        return parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
