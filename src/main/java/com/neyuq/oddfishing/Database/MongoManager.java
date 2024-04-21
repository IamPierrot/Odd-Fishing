package com.neyuq.oddfishing.Database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

class MongoManager {

    private static MongoManager instance = null;
    private static MongoClient mongoClient;


    private MongoManager(String connectionString) throws MongoException {
        if (connectionString == null || connectionString.isEmpty()) {
            throw new IllegalArgumentException("Connection string cannot be null or empty");
        }

        mongoClient = connectDatabase(connectionString);
        instance = this;
    }

    private MongoClient connectDatabase(String connectionURI) {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new UuidCodec(UuidRepresentation.STANDARD)),
                MongoClientSettings.getDefaultCodecRegistry());

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionURI))
                .codecRegistry(codecRegistry)
                .build();

        return MongoClients.create(settings);
    }

    public static synchronized MongoManager getInstance(String connectionString) throws MongoException {
        if (instance == null) new MongoManager(connectionString);
        return instance;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void shutdown() {
        mongoClient.close();
    }
}


