package me.night0721.nv.database

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

class DatabaseManager {
    fun connect() {
        database = MongoClients.create(System.getenv("MONGODB_URI")).getDatabase("NullValkyrie")
    }

    val minersDB: MongoCollection<Document?>
        get() = database!!.getCollection("miners")
    val shopsDB: MongoCollection<Document?>
        get() = database!!.getCollection("shops")
    val ranksDB: MongoCollection<Document?>
        get() = database!!.getCollection("ranks")
    val nPCsDB: MongoCollection<Document?>
        get() = database!!.getCollection("npcs")
    val usersDB: MongoCollection<Document?>
        get() = database!!.getCollection("users")
    val customWeaponsDB: MongoCollection<Document?>
        get() = database!!.getCollection("custom_weapons")

    companion object {
        var database: MongoDatabase? = null
    }
}