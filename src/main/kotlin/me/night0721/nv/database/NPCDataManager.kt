package me.night0721.nv.database

import me.night0721.nv.entities.npcs.NPCManager
import me.night0721.nv.util.Util.warn
import org.bson.Document

object NPCDataManager {
    fun setNPC(
        name: String?,
        x: Double,
        y: Double,
        z: Double,
        pitch: Int,
        yaw: Int,
        world: String?,
        texture: String?,
        signature: String?
    ) {
        val document = DatabaseManager().npcs.find(Document("Name", name)).first()
        if (document != null) {
            warn("A NPC with this name already exist")
        } else {
            val newDocument = Document()
            newDocument["Name"] = name
            newDocument["x"] = x
            newDocument["y"] = y
            newDocument["z"] = z
            newDocument["pitch"] = pitch
            newDocument["yaw"] = yaw
            newDocument["world"] = world
            newDocument["texture"] = texture
            newDocument["signature"] = signature
            DatabaseManager().npcs.insertOne(newDocument)
        }
    }

    fun reloadNPC() {
        val npcList: MutableList<HashMap<String, Any>> = ArrayList()
        DatabaseManager().npcs.find().cursor().use { cursor ->
            while (cursor.hasNext()) {
                val document = cursor.next()
                val npc = HashMap<String, Any>()
                if (!document.isNullOrEmpty()) {
                    for (key in document.keys) {
                        npc[key] = document[key]!!
                    }
                }
                npcList.add(npc)
            }
        }
        NPCManager.reloadNPC(npcList)
    }
}