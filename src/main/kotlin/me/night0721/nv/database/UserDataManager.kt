package me.night0721.nv.database

import me.night0721.nv.ui.player.ScoreboardListener
import org.bson.Document
import org.bson.conversions.Bson

class UserDataManager {
    fun createUserBank(uuid: String?) {
        val document = DatabaseManager().users.find(Document("UUID", uuid)).first()
        if (document.isNullOrEmpty()) {
            val d = Document()
            d["UUID"] = uuid
            d["Bank"] = 0
            DatabaseManager().users.insertOne(d)
        }
    }

    fun updateUserBank(uuid: String?, coins: Int, vararg listener: ScoreboardListener) {
        val document = DatabaseManager().users.find(Document("UUID", uuid)).first()
        if (document != null) {
            val coinsBefore = document.getInteger("Bank")
            val updated: Bson = Document("Bank", coins + coinsBefore)
            val update: Bson = Document("\$set", updated)
            DatabaseManager().users.updateOne(document, update)
            listener[0].sideBarManager.addBank(uuid, coins)
        } else {
            val doc = Document()
            doc["UUID"] = uuid
            doc["Bank"] = coins
            DatabaseManager().users.insertOne(doc)
            listener[0].sideBarManager.addBank(uuid, coins)
        }
    }

    fun getUser(uuid: String?): HashMap<String?, Any?>? {
        val document = DatabaseManager().users.find(Document("UUID", uuid)).first()
        if (document != null) {
            val map = HashMap<String?, Any?>()
            for (key in document.keys) map[key] = document[key]
            map.remove("_id")
            return map
        }
        return null
    }
}