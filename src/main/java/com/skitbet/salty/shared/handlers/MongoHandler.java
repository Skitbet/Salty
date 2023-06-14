package com.skitbet.salty.shared.handlers;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoHandler {

    public static MongoHandler instance;

    private MongoClient client;
    private MongoDatabase mainDB;
    private MongoCollection<Document> ranks;
    private MongoCollection<Document> profiles;

    public void connect() {
        instance = this;
        client = new MongoClient("localhost", 27017);

        mainDB = client.getDatabase("Salty");
        ranks = mainDB.getCollection("ranks");
        profiles = mainDB.getCollection("profiles");
    }

    public MongoDatabase getMainDB() {
        return mainDB;
    }

    public MongoCollection<Document> getRanks() {
        return ranks;
    }

    public MongoCollection<Document> getProfiles() {
        return profiles;
    }
}
