package me.night0721.nv.database

import me.night0721.nv.ui.player.ScoreboardListener
import me.night0721.nv.util.Rank
import org.bson.Document
import org.bson.conversions.Bson
import org.bukkit.Bukkit
import java.util.*

object RankDataManager {
    fun setRank(uuid: UUID, rank: Rank) {
        // TODO: fix not working in rank command
        val document = DatabaseManager().ranks.find(Document("UUID", uuid.toString())).first()
        if (document != null) {
            val updated: Bson = Document("Rank", rank.name)
            val update: Bson = Document("\$set", updated)
            DatabaseManager().ranks.updateOne(document, update)
        } else {
            val newDocument = Document()
            newDocument["UUID"] = uuid.toString()
            newDocument["Rank"] = rank.name
            DatabaseManager().ranks.insertOne(newDocument)
        }
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasPlayedBefore()) {
                ScoreboardListener.nameTagManager.removeTag(player)
                ScoreboardListener.nameTagManager.newTag(player)
            }
        }
    }

    fun getRank(uuid: UUID): Rank? {
        DatabaseManager().ranks.find(Document("UUID", uuid.toString())).cursor().use { cursor ->
            while (cursor.hasNext()) {
                val doc = cursor.next()
                if (doc.isNullOrEmpty()) return null
                for (key in doc.keys) {
                    if (key == "Rank") {
                        return Rank.valueOf((doc[key] as String?)!!)
                    }
                }
            }
        }
        return null
    }
}