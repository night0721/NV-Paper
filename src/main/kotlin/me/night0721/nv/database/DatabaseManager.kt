package me.night0721.nv.database

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

class DatabaseManager {
    fun connect() {
        database = MongoClients.create(System.getenv("MONGODB_URI")).getDatabase("NullValkyrie")
    }

    val miners: MongoCollection<Document?>
        get() = database!!.getCollection("miners")
    val shops: MongoCollection<Document?>
        get() = database!!.getCollection("shops")
    val ranks: MongoCollection<Document?>
        get() = database!!.getCollection("ranks")
    val npcs: MongoCollection<Document?>
        get() = database!!.getCollection("npcs")
    val users: MongoCollection<Document?>
        get() = database!!.getCollection("users")
    val customweapons: MongoCollection<Document?>
        get() = database!!.getCollection("custom_weapons")

    companion object {
        var database: MongoDatabase? = null
    }
}