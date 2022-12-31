package me.night0721.nv.database

import com.mongodb.client.model.Filters
import org.bson.Document
import org.bukkit.Material

class CustomWeaponsDataManager {
    fun getWeapon(itemName: String?): HashMap<String, Any?> {
        val item = HashMap<String, Any?>()
        DatabaseManager().customWeaponsDB.find(Filters.eq("Name", itemName)).cursor().use { cursor ->
            while (cursor.hasNext()) {
                val doc = cursor.next()

                val name = doc.getString("Name")
                val lore = doc["Lore"] as Document?
                val ability = lore!!["Ability"] as Document?
                val properties = lore["Properties"] as Document?
                val lores = HashMap<String, HashMap<String, Any?>>()
                val abi = HashMap<String, Any?>()
                val prop = HashMap<String, Any?>()
                abi["Name"] = ability!!.getString("Name")
                val details: MutableList<String> = ArrayList()
                if (ability["Details"] != null) details.addAll((ability["Details"] as List<String>?)!!)
                abi["Details"] = details
                for (a in properties!!.keys) prop[a] = properties[a]
                lores["Ability"] = abi
                lores["Properties"] = prop
                val enchants = doc["Enchants"] as Document?
                val attributes = doc["Attributes"] as Document?
                val ench = HashMap<String, Any?>()
                val attr = HashMap<String, Any?>()
                for (a in enchants!!.keys) ench[a] = enchants[a]
                for (a in attributes!!.keys) attr[a] = attributes[a]
                val pdc = doc["PDC"] as Document?
                val pdcdata = HashMap<String, Any?>()
                if (pdc != null) for (a in pdc.keys) pdcdata[a] = pdc[a]
                val recipe = doc["Recipes"] as Document?
                val recipes = HashMap<String, Any>()
                if (recipe != null) {
                    val ing = recipe["Ingredients"] as Document?
                    val ingredients = HashMap<String, String>()
                    for (i in ing!!.keys) ingredients[i] = ing.getString(i)
                    val shapes: MutableList<String> = ArrayList()
                    if (recipe["Shapes"] != null) shapes.addAll((recipe["Shapes"] as List<String>?)!!)
                    recipes["Shape"] = shapes
                    recipes["Amount"] = recipe.getInteger("Amount")
                    recipes["Ingredients"] = ingredients
                }
                item["Name"] = name
                item["Material"] = Material.matchMaterial(doc.getString("Material"))
                item["Type"] = doc.getString("Type")
                item["Rarity"] = doc!!.getString("Rarity")
                item["Lore"] = lores
                item["Enchants"] = ench
                item["Attributes"] = attr
                item["PDC"] = pdcdata
                item["Recipes"] = recipes
            }
            return item
        }
    }

    companion object {
        val weapons: HashMap<String, Any>
            get() {
                val list = HashMap<String, Any>()
                DatabaseManager().customWeaponsDB.find().cursor().use { cursor ->
                    while (cursor.hasNext()) {
                        val doc = cursor.next()
                        val item = HashMap<String, Any?>()
                        val name = doc.getString("Name")
                        val lore = doc["Lore"] as Document?
                        val ability = lore!!["Ability"] as Document?
                        val properties = lore["Properties"] as Document?
                        val lores = HashMap<String, HashMap<String, Any?>>()
                        val abi = HashMap<String, Any?>()
                        val prop = HashMap<String, Any?>()
                        abi["Name"] = ability!!.getString("Name")
                        val details: MutableList<String> = ArrayList()
                        if (ability["Details"] != null) details.addAll((ability["Details"] as List<String>?)!!)
                        abi["Details"] = details
                        for (a in properties!!.keys) prop[a] = properties[a]
                        lores["Ability"] = abi
                        lores["Properties"] = prop
                        val enchants = doc["Enchants"] as Document?
                        val attributes = doc["Attributes"] as Document?
                        val ench = HashMap<String, Any?>()
                        val attr = HashMap<String, Any?>()
                        for (a in enchants!!.keys) ench[a] = enchants[a]
                        for (a in attributes!!.keys) attr[a] = attributes[a]
                        item["Name"] = name
                        item["Material"] = Material.matchMaterial(doc.getString("Material"))
                        item["Type"] = doc.getString("Type")
                        item["Rarity"] = doc.getString("Rarity")
                        item["Lore"] = lores
                        item["Enchants"] = ench
                        item["Attributes"] = attr
                        list[name] = item
                    }
                    return list
                }
            }
    }
}