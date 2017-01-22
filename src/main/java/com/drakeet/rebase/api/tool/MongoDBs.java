package com.drakeet.rebase.api.tool;

import com.drakeet.rebase.api.type.Category;
import com.drakeet.rebase.api.type.User;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

/**
 * @author drakeet
 */
public class MongoDBs {

    private static final String TAG = MongoDBs.class.getSimpleName();
    private static MongoDatabase db;
    private static MongoCollection<Document> users;
    private static MongoCollection<Document> categories;
    private static MongoCollection<Document> feeds;


    public static void setUp() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDatabase("rebase");
        Log.i(TAG, "[setUp] Connect to database successfully");
        MongoDBs.initCollections(db);
    }


    private static void initCollections(MongoDatabase db) {
        try {
            db.createCollection("users");
            db.createCollection("categories");
            db.createCollection("feeds");
        } catch (Exception e) {
            Log.w(TAG, "[attemptCreateCollections] " + e.getMessage());
        }
        users = db.getCollection("users");
        categories = db.getCollection("categories");
        feeds = db.getCollection("feeds");

        users.createIndex(
            Indexes.ascending(User.USERNAME),
            new IndexOptions().unique(true));

        categories.createIndex(
            Indexes.ascending(Category.OWNER, Category.KEY),
            new IndexOptions().unique(true));
    }


    public static MongoDatabase db() {
        return db;
    }


    public static MongoCollection<Document> users() {
        return users;
    }


    public static MongoCollection<Document> categories() {
        return categories;
    }


    public static MongoCollection<Document> feeds() {
        return feeds;
    }
}