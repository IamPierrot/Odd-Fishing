package com.neyuq.oddfishing.Database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class CollectionManager {
    private static final String connectionURI = "mongodb+srv://Pierrot:gKoOGnTKFPHkZwUl@neyuq.jyvp28j.mongodb.net/?retryWrites=true&w=majority&appName=NeyuQ";
    private static final MongoManager manager =  MongoManager.getInstance(connectionURI);
    private static MongoCollection<Document> PlayerCollection;
    private static CollectionManager instance = null;

    private CollectionManager() {

        MongoDatabase playerDatabase = manager.getMongoClient().getDatabase("Player");
        PlayerCollection = playerDatabase.getCollection("user");

        instance = this;
    }

    public static CollectionManager getInstance() {
        if (instance == null) new CollectionManager();
        return instance;
    }



}
