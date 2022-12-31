package me.night0721.nv.database

import me.night0721.nv.entities.miners.CryptoMiner
import me.night0721.nv.entities.miners.MinerType
import org.bson.Document
import org.bson.conversions.Bson
import org.bukkit.*

object MinerDataManager {
    fun setMiner(
        name: String?,
        type: MinerType?,
        level: Int,
        rate: Double,
        enabled: Boolean,
        lastclaim: Long,
        location: Location
    ) {
        val newDocument = Document()
        newDocument["ID"] = DatabaseManager().minersDB.countDocuments() + 1
        newDocument["Name"] = name
        newDocument["Material"] = type.getName()
        newDocument["Level"] = level
        newDocument["Rate"] = rate
        newDocument["Enabled"] = enabled
        newDocument["LastClaim"] = lastclaim
        newDocument["x"] = location.x
        newDocument["y"] = location.y
        newDocument["z"] = location.z
        DatabaseManager().minersDB.insertOne(newDocument)
    }

    fun setLastClaim(name: String?) {
        val document = DatabaseManager().minersDB.find(Document("Name", name)).first()
        if (document != null) {
            val updated: Bson = Document("LastClaim", System.currentTimeMillis())
            val update: Bson = Document("\$set", updated)
            DatabaseManager().minersDB.updateOne(document, update)
        }
    }

    fun getLastClaim(id: Long): Long {
        val doc = DatabaseManager().minersDB.find(Document("ID", id)).first()
        if (doc != null) {
            for (key in doc.keys) {
                if (key == "LastClaim") return doc[key] as Long
            }
        }
        return 0
    }

    val miners: HashMap<Long, CryptoMiner>
        get() {
            val list = HashMap<Long, CryptoMiner>()
            DatabaseManager().minersDB.find().cursor().use { cursor ->
                while (cursor.hasNext()) {
                    val doc = cursor.next()
                    list[doc.getLong("ID")] = CryptoMiner(
                        doc.getString("Name"),
                        MinerType.Companion.getByName(doc.getString("Material")),
                        doc.getInteger("Level"),
                        doc.getDouble("Rate"),
                        doc.getLong("LastClaim"),
                        Location(Bukkit.getWorld("world"), doc.getDouble("x"), doc.getDouble("y"), doc.getDouble("z"))
                    )
                }
                return list
            }
        }
}