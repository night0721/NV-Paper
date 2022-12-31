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
        val document = DatabaseManager().npCsDB.find(Document("Name", name)).first()
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
            DatabaseManager().npCsDB.insertOne(newDocument)
        }
    }

    fun reloadNPC() {
        val npcList: MutableList<HashMap<String, Any>> = ArrayList()
        DatabaseManager().npCsDB.find().cursor().use { cursor ->
            while (cursor.hasNext()) {
                val document = cursor.next()
                val npc = HashMap<String, Any>()
                val name = document.getString("Name")
                val x = document.getDouble("x")
                val y = document.getDouble("y")
                val z = document.getDouble("z")
                val pitch = document.getInteger("pitch")
                val yaw = document.getInteger("yaw")
                val world = document.getString("world")
                val texture = document.getString("texture")
                val signature = document.getString("signature")
                npc["name"] = name
                npc["x"] = x
                npc["y"] = y
                npc["z"] = z
                npc["pitch"] = pitch
                npc["yaw"] = yaw
                npc["world"] = world
                npc["texture"] = texture
                npc["signature"] = signature
                npcList.add(npc)
            }
        }
        NPCManager.reloadNPC(npcList)
    }
}