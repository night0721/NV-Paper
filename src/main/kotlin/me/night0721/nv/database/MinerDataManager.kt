package me.night0721.nv.database

import me.night0721.nv.entities.miners.CryptoMiner
import me.night0721.nv.entities.miners.MinerType
import org.bson.Document
import org.bson.conversions.Bson
import org.bukkit.Bukkit
import org.bukkit.Location

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
        newDocument["ID"] = DatabaseManager().miners.countDocuments() + 1
        newDocument["Name"] = name
        newDocument["Material"] = type!!.name
        newDocument["Level"] = level
        newDocument["Rate"] = rate
        newDocument["Enabled"] = enabled
        newDocument["LastClaim"] = lastclaim
        newDocument["x"] = location.x
        newDocument["y"] = location.y
        newDocument["z"] = location.z
        DatabaseManager().miners.insertOne(newDocument)
    }

    fun setLastClaim(name: String?) {
        val document = DatabaseManager().miners.find(Document("Name", name)).first()
        if (document != null) {
            val updated: Bson = Document("LastClaim", System.currentTimeMillis())
            val update: Bson = Document("\$set", updated)
            DatabaseManager().miners.updateOne(document, update)
        }
    }

    fun getLastClaim(id: Long): Long {
        val doc = DatabaseManager().miners.find(Document("ID", id)).first()
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
            DatabaseManager().miners.find().cursor().use { cursor ->
                while (cursor.hasNext()) {
                    val doc = cursor.next()
                    if (!doc.isNullOrEmpty())
                        list[doc.getLong("ID")] = CryptoMiner(
                            doc.getString("Name"),
                            MinerType.getByName(doc.getString("Material")),
                            doc.getInteger("Level"),
                            doc.getDouble("Rate"),
                            doc.getLong("LastClaim"),
                            Location(
                                Bukkit.getWorld("world"),
                                doc.getDouble("x"),
                                doc.getDouble("y"),
                                doc.getDouble("z")
                            )
                        )
                }
                return list
            }
        }
}