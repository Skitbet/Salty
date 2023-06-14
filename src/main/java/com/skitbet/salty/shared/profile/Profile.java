package com.skitbet.salty.shared.profile;

import com.skitbet.salty.shared.rank.Rank;

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
    }

    public Profile(UUID uuid, String name, Rank rank, List<String> permissions) {
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.permissions = permissions;
    }
}
