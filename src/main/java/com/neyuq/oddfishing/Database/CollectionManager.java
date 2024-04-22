package com.neyuq.oddfishing.Database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.neyuq.oddfishing.Models.PlayerModel;
import org.bson.Document;
import org.bukkit.entity.Player;

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

    public synchronized boolean addPlayer(Player player) {
        if (player == null) return false;
        Document playerData = PlayerCollection.find(new Document("uniqueId", player.getUniqueId())).first();

        if (playerData != null) return false;

        PlayerCollection.insertOne(new PlayerModel(player).toDocument().append("_id", player.getUniqueId()));

        return true;
    }
    public synchronized void updatePlayer(Player player) {
        if (player == null) return;
        if (PlayerCollection.find(new Document("uniqueId", player.getUniqueId())).first() != null) return;

        PlayerModel playerModel = new PlayerModel(player);
        Document updateDocument = new Document("$set", playerModel.toDocument());

        PlayerCollection.updateOne(new Document("uniqueId", player.getUniqueId()), updateDocument);

    }
    public synchronized void updatePlayer(Player player, Document updateDocument) throws Exception {
        if (player == null) return;

        PlayerCollection.updateOne(new Document("uniqueId", player.getUniqueId()), updateDocument);
    }

    public static synchronized void disconnect() {
        manager.shutdown();
    }


}
